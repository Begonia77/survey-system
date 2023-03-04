<template>
  <div class="h-layout">
    <el-image
      class="logo"
      style="height: 30px; width: 30px"
      fit="fill"
      :src="logo"
    >
    </el-image>

    <el-menu
      default-active="1"
      class="el-menu-demo"
      mode="horizontal"
      router
      background-color="#1e649f"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <el-menu-item index="/">首页</el-menu-item>
      <el-menu-item index="/model">模板中心</el-menu-item>
      <el-menu-item index="/paper">我的问卷</el-menu-item>
    </el-menu>

    <div v-if="!getUserName" class="admin">
      <span @click="$router.push('/login')">登录 </span>
      <span>|</span>
      <span @click="$router.push('/register')"> 注册</span>
    </div>

    <el-dropdown v-else @command="handleCommand" class="admin">
      <span class="el-dropdown-link">
        {{ getUserName }}<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="a">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "qsn-header",
  data() {
    return {
      logo: require("../../assets/logo.png"),
    };
  },
  computed: {
    getUserName() {
      const currentUser = localStorage.getItem("currentUser");
      
      return currentUser;
    },
  },
  methods: {
    handleCommand(com) {
      if (com === "a") {
        // 退出登录
        localStorage.removeItem("currentUser");
        const currentUser = JSON.parse(localStorage.getItem("currentUser"));
        location.reload();
      }
    },
  },
};
</script>

<style lang="scss">
.h-layout {
  display: flex;
  justify-content: space-between;
}
.el-header {
  padding: 0;
}
.logo {
  margin-top: 15px;
  margin-left: 40px;
}
.el-menu {
  width: 30%;
}
.admin {
  margin-right: 30px;
  cursor: pointer;
  span {
    font-weight: bolder;
    color: white;
  }
}
</style>
