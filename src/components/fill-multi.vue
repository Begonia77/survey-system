<template>
  <div class="qs-layout">
    <el-form
      v-if="show"
      :model="fillMultiForm"
      ref="fillMultiForm"
      label-width="100px"
      class="demo-dynamic"
    >
      <el-form-item
        prop="title"
        label="标题[多行]"
        :rules="{
          required: true,
          message: '标题不能为空',
          trigger: 'blur',
        }"
      >
        <el-input style="width: 68%" v-model="fillMultiForm.title"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button
          size="small"
          type="primary"
          @click="finishForm('fillMultiForm')"
          >完成</el-button
        >
        <el-button
          size="small"
          type="danger"
          @click="deleteForm('fillMultiForm')"
          >删除</el-button
        >
      </el-form-item>
    </el-form>

    <div
      v-if="!show"
      class="sr-show"
      @mouseenter="changePop"
      @mouseleave="popup = false"
    >
      <div class="sr-left">
        <div class="sr-title">
          <span>{{ qsIndex }}.&nbsp;{{ fillMultiForm.title }}</span>
        </div>
        <el-input
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 100 }"
          v-model="ans"
        >
        </el-input>
      </div>

      <div class="sr-btn" v-show="popup">
        <div>
          <el-button type="primary" size="mini" @click="changeEdit"
            >编辑</el-button
          >
          <el-button size="mini" :disabled="qsIndex <= 1" @click="moveUp"
            >上移</el-button
          >
          <el-button
            size="mini"
            :disabled="qsIndex >= qsCount"
            @click="moveDown"
            >下移</el-button
          >
        </div>
      </div>
    </div>
    <el-divider></el-divider>
  </div>
</template>

<script>
import dataSource from "@/util/dataSource";
import modelSource from "@/util/modelSource";
export default {
  name: "fill-multi",
  props: [
    "qsIndex",
    "qsKey",
    "qsCount",
    "qsEdit",
    "paperId",
    "popStop",
    "qsPre",
    "qsData",
    "qsModel",
    "modelId",
  ],
  data() {
    return {
      popup: false,
      show: true,
      ans: "",
      fillMultiForm: {
        qs_id: "",
        options: [],
        title: "",
      },
    };
  },
  watch: {
    qsData: {
      handler: function (val, oldVal) {
        this.fillMultiForm.qs_id = val.qs_id;
        this.fillMultiForm.title = val.qs_title;
        this.fillMultiForm.options = val.qs_option;
      },
      deep: true,
    },
  },
  created() {
    if (this.qsModel === "true") {
      this.show = false;
      const question = modelSource.getQuestionById(this.modelId, this.qsIndex);
      this.fillMultiForm.qs_id = question.qs_id;
      this.fillMultiForm.title = question.qs_title;
      this.fillMultiForm.options = question.qs_option;
    }

    // 若为编辑状态，则需要获取题目初始信息
    if (this.qsEdit === "true") {
      this.show = false;
      if (this.qsData) {
        this.fillMultiForm.qs_id = this.qsData.qs_id;
        this.fillMultiForm.title = this.qsData.qs_title;
        this.fillMultiForm.options = this.qsData.qs_option;
      } else {
        this.show = true;
      }
    }

    // 若为预览状态
    if (this.qsPre === "true") {
      this.show = false;
      this.fillMultiForm.qs_id = this.qsData.qs_id;
      this.fillMultiForm.title = this.qsData.qs_title;
      this.fillMultiForm.options = this.qsData.qs_option;
    }
  },
  methods: {
    changePop() {
      if (this.popStop) this.popup = false;
      else this.popup = true;
    },
    changeEdit() {
      this.show = true;
    },
    moveUp() {
      this.$emit("qsMoveUP");
    },
    moveDown() {
      this.$emit("qsMoveDown");
    },
    finishForm(formName) {
      // 检查字段是否合法
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.popup = false;
          this.show = false;

          // 将题目封装并传递给父组件
          let fm = {
            qs_id: this.qs_id,
            qs_order: this.qsIndex, // 题目顺序
            qs_number: this.qsKey, // 题目编号
            qs_title: this.fillMultiForm.title,
            qs_type: this.QS_TYPE.FILL_MULTI.value,
            qs_option: this.fillMultiForm.options,
          };
          this.$emit("add2Paper", fm);
        } else {
          return false;
        }
      });
    },
    deleteForm(formName) {
      this.$emit("delForm");
    },
  },
};
</script>

<style scoped>
.qs-layout {
  padding: 0 30px;
}

.sr-show {
  display: flex;
  justify-content: space-between;
}

.sr-title {
  font-weight: bold;
  padding: 10px 0;
}

.sr-group {
  padding-left: 10px;
  margin-bottom: 5px;
}

.sr-option {
  padding-bottom: 5px;
}
.sr-left {
  width: 70%;
}

.sr-btn {
  width: 30%;
  flex-direction: column;
  display: flex;
  justify-content: center;
}
</style>
