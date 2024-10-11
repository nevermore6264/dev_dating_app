import { createRouter, createWebHistory } from "vue-router";
import LoginPage from "../views/LoginPage.vue";
import RegisterPage from "@/views/RegisterPage.vue";
import ForgotPasswordPage from "@/views/ForgotPasswordPage.vue";
import HomePage from "../views/HomePage.vue";
import CafePage from "../views/CafePage.vue";
import CriteriaPage from "../views/CriteriaPage.vue";
import YouAreSafePage from "../views/YouAreSafePage.vue";
import MapPage from "../views/MapPage.vue";
import ChattingPage from "../views/ChattingPage.vue";
import ChangePassFirstLogin from "@/views/ChangePassFirstLogin.vue";
import EditProfilePage from "@/views/EditProfilePage.vue";
import UpdateProfileFirstLogin from "@/views/UpdateProfileFirstLogin.vue";
import ViewProfilePage from "@/views/ViewProfilePage.vue";
import NotificationPage from "@/views/NotificationPage.vue";
import SuccessToast from "@/views/Test.vue";

const router = createRouter({
  history: createWebHistory(process.env.VITE_PUBLIC_BASE_URL),
  routes: [
    {
      path: "/",
      name: "Login",
      component: LoginPage, // Trang Login sẽ là trang mặc định
    },
    {
      path: "/changePassFirstLogin",
      name: "ChangePassFirstLogin",
      component: ChangePassFirstLogin,
    },
    {
      path: "/forgotPasswordPage",
      name: "ForgotPasswordPage",
      component: ForgotPasswordPage,
    },
    {
      path: "/registerPage",
      name: "Register",
      component: RegisterPage,
    },
    {
      path: "/youAreSafePage",
      name: "youAreSafePage",
      component: YouAreSafePage,
    },
    {
      path: "/chattingPage",
      name: "chattingPage",
      component: ChattingPage,
    },
    {
      path: "/homePage",
      name: "homePage",
      component: HomePage,
    },
    {
      path: "/cafePage",
      name: "cafePage",
      component: CafePage,
    },
    {
      path: "/criteriaPage",
      name: "criteriaPage",
      component: CriteriaPage,
    },
    {
      path: "/editProfilePage",
      name: "editProfilePage",
      component: EditProfilePage,
    },
    {
      path: "/updateProfileFirstLogin",
      name: "updateProfileFirstLogin",
      component: UpdateProfileFirstLogin,
    },
    {
      path: "/mapPage",
      name: "mapPage",
      component: MapPage,
    },
    {
      path: "/profile",
      name: "profile",
      component: ViewProfilePage,
    },
    {
      path: "/notification",
      name: "notification",
      component: NotificationPage,
    },
    {
      path: "/a",
      name: "a",
      component: SuccessToast,
    },
  ],
});

export default router;
