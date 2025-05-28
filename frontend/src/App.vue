<template>
  <div id="app">
    <el-container>
      <el-header>
        <h1>学生管理系统</h1>
      </el-header>
      <el-main>
        <student-list />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import StudentList from './components/StudentList.vue'
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.el-header {
  background-color: #409EFF;
  color: white;
  text-align: center;
  line-height: 60px;
}

.el-main {
  padding: 20px;
}
</style>
