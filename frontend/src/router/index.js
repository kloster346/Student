import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "Layout",
    component: () => import("@/layout/index.vue"),
    redirect: "/student",
    children: [
      {
        path: "student",
        name: "Student",
        component: () => import("@/views/student/index.vue"),
        meta: { title: "学生管理" }
      },
      {
        path: "course",
        name: "Course",
        component: () => import("@/views/course/index.vue"),
        meta: { title: "课程管理" }
      },
      {
        path: "sc",
        name: "SC",
        component: () => import("@/views/sc/index.vue"),
        meta: { title: "选课管理" }
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
