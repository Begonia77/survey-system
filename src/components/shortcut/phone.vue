<template>
  <div class="qs-layout">
    <div class="sr-show" @mouseenter="changePop" @mouseleave="popup = false">
      <div>
        <div class="sr-title">
          <span>{{ qsIndex }}.&nbsp;{{ PhoneForm.title }}</span>
        </div>
        <el-input prefix-icon="el-icon-phone-outline" v-model="ans"> </el-input>
      </div>

      <div class="sr-btn" v-show="popup">
        <div>
          <el-button size="mini" type="danger" @click="deleteForm"
            >删除</el-button
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
  name: "phone",
  props: [
    "qsIndex",
    "qsKey",
    "qsCount",
    "paperId",
    "qsEdit",
    "popStop",
    "qsModel",
    "modelId",
    "qsData",
  ],
  data() {
    return {
      popup: false,
      ans: "",
      show: true,
      PhoneForm: {
        qs_id: "",
        title: "您的手机号？",
        option: [],
      },
    };
  },
  created() {
    if (this.qsModel === "true") {
      const question = modelSource.getQuestionById(this.modelId, this.qsIndex);
      this.PhoneForm.qs_id = question.qs_id;
    }

    if (this.qsEdit === "true") {
      this.show = false;
      if (this.qsData) {
        this.PhoneForm.qs_id = this.qsData.qs_id;
      } else {
        this.show = true;
      }
    }
  },
  mounted() {
    let phone = {
      qs_id: this.qs_id,
      qs_order: this.qsIndex, // 题目顺序
      qs_number: this.qsKey, // 题目编号
      qs_title: this.PhoneForm.title,
      qs_type: this.QS_TYPE.SHORTCUT_PHONE.value,
      qs_option: this.PhoneForm.option,
    };
    this.$emit("add2Paper", phone);
  },
  methods: {
    changePop() {
      if (this.popStop) this.popup = false;
      else this.popup = true;
    },
    deleteForm(formName) {
      this.$emit("delForm");
    },
    moveUp() {
      this.$emit("qsMoveUP");
    },
    moveDown() {
      this.$emit("qsMoveDown");
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

.sr-btn {
  flex-direction: column;
  display: flex;
  justify-content: center;
}
</style>
