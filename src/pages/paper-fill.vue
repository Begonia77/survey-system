<template>
  <div>
    <!--页面头部-->
    <el-row class="edit-top">
      <el-image
        class=""
        style="height: 50px; width: 100px"
        fit="fill"
        :src="logo"
      >
      </el-image>
      <span>填写问卷</span>
      <el-button class="btn" type="success" :disabled="flag" @click="postPaper"
        >提交问卷</el-button
      >
    </el-row>

    <el-row>
      <!--问卷主编辑区-->
      <el-col :span="14" :offset="5">
        <div v-if="!submit" class="edit-main">
          <!--问卷标题-->
          <div class="edit-main-title">
            <span>{{ paper.title }}</span>
          </div>
          <el-divider></el-divider>

          <!--问卷内容-->
          <div class="edit-main-content">
            <!--遍历组件并添加到问卷-->
            <div v-for="(qs, index) in qsList" :key="qs.key">
              <!--
              qsIndex属性：向子组件传递题目顺序
              qsKey属性：向子组件传递题目编号（唯一）
              delForm方法：接收子组件传递的删除事件
              add2Paper方法：接收子组件传递的添加题目事件
              -->
              <component
                :is="qs.name"
                :popStop="true"
                :qsIndex="index + 1"
                :qsKey="qs.key"
                :qsData="paper.content[index]"
                :qsPre="'true'"
                :qsCount="qsList.length"
                ref="qs"
              >
              </component>
            </div>
          </div>
          <el-divider></el-divider>

          <!--问卷结束语-->
          <div class="edit-main-footer">
            <span>{{ paper.footer }}</span>
          </div>
        </div>

        <div v-if="submit" class="edit-submit">
          <div class="edit-txt"><span>您的问卷已提交成功！</span></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Gender from "../components/shortcut/gender";
import Phone from "../components/shortcut/phone";
import NameStr from "../components/shortcut/name";
import FillDate from "../components/fill-date";
import FillMulti from "../components/fill-multi";
import FillSingle from "../components/fill-single";
import SelectDrop from "../components/select-drop";
import SelectRadio from "../components/select-radio";
import SelectMulti from "../components/select-multi";
import dataSource from "../util/dataSource";

export default {
  name: "paper-fill",
  props: ["id"],
  components: {
    Gender,
    Phone,
    NameStr,
    FillDate,
    FillMulti,
    FillSingle,
    SelectDrop,
    SelectRadio,
    SelectMulti,
  },
  data() {
    return {
      submit: false,
      logo: require("../assets/logo.png"),
      flag: false,
      qsList: [],
      paper: {
        paper_id: "",
        qs_count: 0, // 总题目数
        title: "问卷标题",
        content: [],
        footer: "感谢您参与问卷调查，祝您生活愉快!",
      },
      answer: [],
    };
  },
  created() {
    this.paper = "";
    this.qsList = [];
    const data = dataSource.getPaperById(this.id);
    // 构造出题目组件列表
    const { content } = data;
    for (let i in content) {
      if (content[i].qs_type === this.QS_TYPE.SELECT_RADIO.value)
        this.qsList.push({
          key: content[i].qs_order,
          name: "SelectRadio",
        });
      if (content[i].qs_type === this.QS_TYPE.SELECT_MULTI.value)
        this.qsList.push({
          key: content[i].qs_order,
          name: "SelectMulti",
        });
      if (content[i].qs_type === this.QS_TYPE.SELECT_DROP.value)
        this.qsList.push({
          key: content[i].qs_order,
          name: "SelectDrop",
        });
      if (content[i].qs_type === this.QS_TYPE.FILL_SINGLE.value)
        this.qsList.push({
          key: content[i].qs_order,
          name: "FillSingle",
        });
      if (content[i].qs_type === this.QS_TYPE.FILL_MULTI.value)
        this.qsList.push({
          key: content[i].qs_order,
          name: "FillMulti",
        });
      if (content[i].qs_type === this.QS_TYPE.FILL_DATE.value)
        this.qsList.push({
          key: content[i].qs_order,
          name: "FillDate",
        });
      if (content[i].qs_type === this.QS_TYPE.SHORTCUT_NAME.value)
        this.qsList.push({
          key: content[i].qs_order,
          name: "NameStr",
        });
      if (content[i].qs_type === this.QS_TYPE.SHORTCUT_GENDER.value)
        this.qsList.push({
          key: content[i].qs_order,
          name: "Gender",
        });
      if (content[i].qs_type === this.QS_TYPE.SHORTCUT_PHONE.value)
        this.qsList.push({
          key: content[i].qs_order,
          name: "Phone",
        });
    }
    this.paper = data;
  },
  methods: {
    // 验证是否填写完
    validFill() {
      for (let i in this.qsList)
        if (
          this.$refs["qs"][i].ans === "" ||
          this.$refs["qs"][i].ans === undefined
        )
          return false;
      return true;
    },
    // 提交问卷答案
    postPaper() {
      if (!this.validFill()) {
        this.$message.warning("您的问卷未填完，无法提交");
        return false;
      }
      // 构造答案数组
      for (let i in this.qsList) {
        

        let ans = {
          qs_id: this.paper.content[i].qs_id,
          qs_type: this.paper.content[i].qs_type,
          qs_option: this.$refs["qs"][i].ans,
          qs_number: this.paper.content[i].qs_number,
        };
        this.answer.push(ans);
      }
      
      dataSource.saveAnswerToPaper(this.id, this.answer);
      this.submit = true;
      this.flag = true;
    },
  },
};
</script>

<style lang="scss" scoped>
.el-row {
  margin: 0;
  padding: 0;
  width: 100%;
}

ul,
li,
a,
h1,
h2,
h3 {
  margin: 0;
  padding: 0;
  list-style: none;
  text-decoration: none;
}

.el-divider--horizontal {
  margin: 10px 0;
}

/*页面头部样式*/
.edit-top {
  height: 60px;
  line-height: 60px;
  margin-bottom: 20px;
  background: #1e649f;
  color: #d3dce6;
  text-align: center;

  .logo {
    float: left;
    margin-top: 5px;
    margin-left: 30px;
  }

  span {
    font-size: larger;
    font-weight: bolder;
  }

  .btn {
    float: right;
    margin-top: 10px;
    margin-right: 50px;
  }
}

/*编辑问卷区域样式*/
.edit-main {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  height: calc(100vh - 80px);
  overflow: auto;
  overflow-x: hidden;

  /*问卷标题区*/
  .edit-main-title {
    text-align: center;
    padding: 20px 0;

    span {
      font-weight: bolder;
      font-size: larger;
    }
  }

  /*问卷主体区*/
  .edit-main-content {
    .emc-no-qs {
      margin: auto;
      width: 400px;
      height: 40px;
      line-height: 40px;
      border: 1px dashed;
      text-align: center;

      span i {
        font-size: larger;
        font-weight: bolder;
      }
    }
  }

  /*问卷结束语*/
  .edit-main-footer {
    text-align: center;
    padding: 20px 0;
    margin-bottom: 30px;
  }
}

.edit-submit {
  .edit-txt {
    text-align: center;
    margin: 200px 0;

    span {
      font-size: 40px;
      font-weight: 500;
    }
  }
}
</style>
