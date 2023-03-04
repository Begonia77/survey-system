<template>
  <div class="lr login-background">
    <el-tabs class="lr-tab" type="border-card">
      <el-form :model="registerForm" :rules="rules" ref="registerForm">
        <h2 align="center" style="padding-bottom: 15px">问卷系统注册</h2>
        <el-form-item prop="user">
          <el-input
            type="text"
            v-model="registerForm.user"
            placeholder="账号"
            prefix-icon="el-icon-user-solid"
          />
        </el-form-item>
        <el-form-item prop="pwd">
          <el-input
            type="password"
            v-model="registerForm.pwd"
            placeholder="密码"
            prefix-icon="el-icon-key"
            show-password
          />
        </el-form-item>
        <el-form-item prop="pwd2">
          <el-input
            type="password"
            v-model="registerForm.pwd2"
            placeholder="确认密码"
            prefix-icon="el-icon-key"
            show-password
          />
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" @click="register('registerForm')"
            >注&nbsp;&nbsp;&nbsp;&nbsp;册</el-button
          >
        </el-form-item>
        <el-form-item style="float: left; margin-left: 20px">
          <el-link :underline="false" type="primary" @click="onLogin"
            >前去登录</el-link
          >
        </el-form-item>
      </el-form>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "register",
  data() {
    // 自定义校验规则

    let validateUser = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入账号"));
      } else if (!/^[a-zA-Z0-9_]{5,10}$/.test(value)) {
        callback(new Error("请使用字母、数字、下划线，且长度为5-10个字符"));
      } else if (accounts.find((item) => item.user === value)) {
        callback(new Error("该账号已被注册"));
      } else {
        callback();
      }
    };
    let validatePwd = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (!/^[a-zA-Z0-9.]{6,15}$/.test(value)) {
        callback(new Error("请使用字母、数字、小数点，且长度为6-15个字符"));
      } else {
        if (this.registerForm.pwd2 !== "") {
          this.$refs.registerForm.validateField("pwd2");
        }
        callback();
      }
    };
    let validatePwd2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.registerForm.pwd) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      userToken: "",
      registerForm: {
        user: "",
        pwd: "",
        pwd2: "",
      },
      rules: {
        user: [{ validator: validateUser, trigger: "blur" }],
        pwd: [{ validator: validatePwd, trigger: "blur" }],
        pwd2: [{ validator: validatePwd2, trigger: "blur" }],
      },
    };
  },
  methods: {
    onLogin() {
      this.$router.push({ name: "login" });
    },
    register(FormName) {
      
      this.$refs[FormName].validate((valid) => {
        if (valid) {
          var accounts = localStorage.getItem("accounts")
            ? JSON.parse(localStorage.getItem("accounts"))
            : [];
          localStorage.setItem(
            "accounts",
            JSON.stringify([
              ...accounts,
              {
                user: this.registerForm.user,
                pwd: this.registerForm.pwd,
              },
            ])
          );
          this.$message({
            type: "success",
            message: "注册成功，正在前往首页",
          });
          localStorage.setItem("currentUser", this.registerForm.user);
          this.$refs[FormName].resetFields();
          this.$router.push({ name: "home" });
        } else {
          this.$message.error({
            message: "注册失败，请正确填写信息",
          });
          return false;
        }
      });
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
