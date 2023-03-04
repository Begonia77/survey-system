<template>
  <div class="lr login-background">
    <el-tabs class="lr-tab" type="border-card">
      <el-form :model="loginForm" :rules="rules" ref="loginForm">
        <h2 align="center" style="padding-bottom: 15px">问卷系统登录</h2>
        <el-form-item prop="user">
          <el-input
            placeholder="账号"
            prefix-icon="el-icon-user-solid"
            type="text"
            v-model="loginForm.user"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            placeholder="密码"
            prefix-icon="el-icon-key"
            show-password
            type="password"
            v-model="loginForm.password"
          />
        </el-form-item>
        <el-form-item style="float: right">
          <el-button @click="login('loginForm')" type="success"
            >登&nbsp;&nbsp;&nbsp;&nbsp;录</el-button
          >
        </el-form-item>
        <el-form-item style="float: left; margin-left: 20px">
          <el-link :underline="false" @click="onRegister" type="primary"
            >前去注册</el-link
          >
        </el-form-item>
      </el-form>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      userToken: "",
      loginForm: {
        user: "",
        password: "",
      },
      rules: {
        user: [{ required: true, message: "请输入账号", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  methods: {
    onRegister() {
      this.$router.push({ name: "register" });
    },
    login(FormName) {
      const accounts = JSON.parse(localStorage.getItem("accounts")) || [];
      const matchedAccount = accounts.find((item) => {
        return (
          item.user === this.loginForm.user &&
          item.pwd === this.loginForm.password
        );
      });
      if (matchedAccount) {
        this.$message({
          message: "登录成功",
          type: "success",
        });
        localStorage.setItem("currentUser", this.loginForm.user);
        this.$router.push({ name: "home" });
      } else if (this.loginForm.user === "") {
        this.$message({
          message: "请输入账号",
          type: "error",
        });
      } else if (this.loginForm.password === "") {
        this.$message({
          message: "请输入密码",
          type: "error",
        });
      } else {
        this.$message({
          message: "账号或密码错误",
          type: "error",
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.login-background {
  background: linear-gradient(-180deg, #dcf2e6 0%, #ffffff 100%);
  width: 100%;
  height: 100%;
  z-index: -1;
  position: absolute;
}

.lr-tab {
  z-index: 1;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 30%;
}
</style>
