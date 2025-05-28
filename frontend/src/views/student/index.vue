<template>
  <div class="student-container">
    <student-list></student-list>
  </div>
</template>

<script setup>
import StudentList from '@/components/StudentList.vue'

// 定义组件名称
defineOptions({
  name: 'StudentView'
})
</script>

<style scoped>
.student-container {
  padding: 20px;
}
</style> 