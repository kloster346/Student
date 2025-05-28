import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '@/layout/MainLayout.vue'

const routes = [
  {
    path: '/',
    component: MainLayout,
    redirect: '/student',
    children: [
      {
        path: 'student',
        name: 'StudentManagement',
        component: () => import('@/views/student/StudentView.vue'),
        meta: { title: '学生管理' }
      },
      {
        path: 'course',
        name: 'CourseManagement',
        component: () => import('@/views/course/CourseView.vue'),
        meta: { title: '课程管理' }
      },
      {
        path: 'selection',
        name: 'SelectionManagement',
        component: () => import('@/views/selection/SelectionView.vue'),
        meta: { title: '选课管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 