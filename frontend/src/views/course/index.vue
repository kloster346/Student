<template>
  <div class="course-container">
    <course-list></course-list>
  </div>
</template>

<script setup>
import CourseList from '@/components/CourseList.vue'

// 定义组件名称
defineOptions({
  name: 'CourseView'
})
</script>

<style scoped>
.course-container {
  padding: 20px;
}
</style> 