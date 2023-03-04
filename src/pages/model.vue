<template>
  <div class="model">
    <el-row>
      <el-col :span="20" :offset="2">
        <!--全局搜索表单-->
        <el-row style="display: flex; justify-content: left">
          <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="标题">
              <el-input
                v-model="formInline.title"
                placeholder="请输入问卷标题"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="searchModelByTitle()"
                >搜索</el-button
              >
              <el-button type="info" @click="searchReset()">重置</el-button>
            </el-form-item>
          </el-form>
        </el-row>

        <!--模板列表-->
        <el-row :gutter="20">
          <el-col
            v-for="(model, index) in models"
            :key="index"
            :span="8"
            class="model-card"
          >
            <el-card
              shadow="hover"
              class="box-card"
              :body-style="{ padding: '10px' }"
            >
              <div class="box-card-top">
                <div>
                  <span
                    ><a @click="preModelPaper(model.id)">{{
                      model.title
                    }}</a></span
                  >
                </div>
                <el-button
                  @click="createPaperFromModel(model.id)"
                  style="float: right; padding: 3px 0"
                  type="text"
                  >从此模板创建问卷</el-button
                >
              </div>
              <el-divider></el-divider>
              <div class="box-card-bottom">
                {{ model.desc }}
              </div>
              <el-divider></el-divider>
              <div class="box-card-footer">
                <div style="font-size: small">共{{ model.qs_count }}题</div>
                <div>
                  <span style="font-size: small"
                    >来自用户：{{ model.user }}</span
                  >
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import modelSource from "../util/modelSource";
export default {
  name: "model",
  data() {
    return {
      formInline: {
        title: "",
      },
      models: [],
    };
  },

  created() {
    // 发送请求查询所有问卷模板
    const data = modelSource.getAllModels();
    this.models = data || [];
  },

  methods: {
    // 搜索问卷模板
    searchModelByTitle() {
      
      this.models = modelSource.getModelsByKeyword(this.formInline.title);
    },
    // 重置搜索框
    searchReset() {
      this.formInline.title = "";
      this.models = modelSource.getAllModels();
    },

    // 从模板创建问卷
    createPaperFromModel(modelId) {
      this.$router.push({
        name: "paper-edit",
        params: {
          modelId: modelId,
          model: "true",
        },
      });
    },

    // 预览问卷模板
    preModelPaper(modelId) {
      const data = modelSource.getModelById(modelId);

      const { content } = data;
      let qsList = [];
      for (let i in content) {
        if (content[i].qs_type === this.QS_TYPE.SELECT_RADIO.value)
          qsList.push({
            key: content[i].qs_order,
            name: "SelectRadio",
          });
        if (content[i].qs_type === this.QS_TYPE.SELECT_MULTI.value)
          qsList.push({
            key: content[i].qs_order,
            name: "SelectMulti",
          });
        if (content[i].qs_type === this.QS_TYPE.SELECT_DROP.value)
          qsList.push({
            key: content[i].qs_order,
            name: "SelectDrop",
          });
        if (content[i].qs_type === this.QS_TYPE.FILL_SINGLE.value)
          qsList.push({
            key: content[i].qs_order,
            name: "FillSingle",
          });
        if (content[i].qs_type === this.QS_TYPE.FILL_MULTI.value)
          qsList.push({
            key: content[i].qs_order,
            name: "FillMulti",
          });
        if (content[i].qs_type === this.QS_TYPE.FILL_DATE.value)
          qsList.push({
            key: content[i].qs_order,
            name: "FillDate",
          });
        if (content[i].qs_type === this.QS_TYPE.SHORTCUT_NAME.value)
          qsList.push({
            key: content[i].qs_order,
            name: "NameStr",
          });
        if (content[i].qs_type === this.QS_TYPE.SHORTCUT_GENDER.value)
          qsList.push({
            key: content[i].qs_order,
            name: "Gender",
          });
        if (content[i].qs_type === this.QS_TYPE.SHORTCUT_PHONE.value)
          qsList.push({
            key: content[i].qs_order,
            name: "Phone",
          });
      }
      this.$router.push({
        name: "paper-view",
        params: { paperInfo: data, preQsList: qsList, pre: "true" },
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.el-divider--horizontal {
  margin: 12px 0;
}

.model {
  .model-card {
    margin-bottom: 20px;

    .box-card {
      .box-card-top {
        display: flex;
        justify-content: space-between;

        span {
          cursor: pointer;

          a,
          a:active,
          a:visited {
            text-decoration: none;
            color: #333333;
          }

          a:hover {
            color: #1e649f;
          }
        }
      }

      .box-card-bottom {
        text-align: left;
        font-weight: lighter;
        font-size: 15px;
        height: 80px;
      }

      .box-card-footer {
        display: flex;
        justify-content: space-between;
        .tag {
          margin-right: 5px;
        }
      }
    }
  }
}
</style>
