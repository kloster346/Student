<template>
  <div id="app">
    <el-container>
      <el-header>
        <h1>学生管理系统</h1>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu
            :default-active="activeMenu"
            class="el-menu-vertical"
            @select="handleSelect"
          >
            <el-menu-item index="students">
              <span>学生管理</span>
            </el-menu-item>
            <el-menu-item index="courses">
              <span>课程管理</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <student-list v-if="activeMenu === 'students'" />
          <course-list v-if="activeMenu === 'courses'" />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import StudentList from './components/StudentList.vue'
import CourseList from './components/CourseList.vue'

const activeMenu = ref('students')

const handleSelect = (index) => {
  activeMenu.value = index
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100vh;
}

.el-container {
  height: 100%;
}

.el-header {
  background-color: #409EFF;
  color: white;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #f8f9fa;
  border-right: solid 1px #e6e6e6;
}

.el-menu-vertical {
  border-right: none;
}

.el-main {
  padding: 20px;
  background-color: #fff;
}
</style>
