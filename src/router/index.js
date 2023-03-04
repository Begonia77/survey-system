import Vue from "vue";
import Router from "vue-router";

const Index = () => import("../pages/index");
const Home = () => import("../pages/home");
const Model = () => import("../pages/model");
const Paper = () => import("../pages/paper");
const PaperEdit = () => import("../pages/paper-edit");
const PaperAnalyse = () => import("../pages/paper-analyse");
const Register = () => import("../pages/register");
const Login = () => import("../pages/login");
const PaperView = () => import("../pages/paper-view");
const PaperFill = () => import("../pages/paper-fill");

Vue.use(Router);

const routes = [
  {
    path: "/",
    name: "index",
    component: Index,
    children: [
      {
        path: "",
        name: "home",
        component: Home,
      },
      {
        path: "model",
        name: "model",
        component: Model,
      },
      {
        path: "paper",
        name: "paper",
        component: Paper,
        meta: { authRequired: true },
      },
    ],
  },
  {
    path: "/paper/edit",
    name: "paper-edit",
    component: PaperEdit,
    meta: { authRequired: true },
  },
  {
    path: "/paper/analyse",
    name: "paper-analyse",
    component: PaperAnalyse,
    meta: { authRequired: true },
  },
  {
    path: "/paper/view",
    name: "paper-view",
    component: PaperView,
  },
  {
    path: "/register",
    name: "register",
    component: Register,
  },
  {
    path: "/login",
    name: "login",
    component: Login,
  },
  {
    path: "/paper/fill/:id",
    name: "paper-fill",
    component: PaperFill,
    props: true,
  },
];

const router = new Router({
  mode: "history",
  routes,
});

/*
  配置导航守卫
  只能验证当前的访问是否有token，关于token的正确性需要后端校验
*/
// router.beforeEach((to, from, next) => {
//   const token = localStorage.getItem("token")
//   if (to.meta['authRequired'] && !token) next({name: 'login-register'})
//   else next()
// })

export default router;
