<template>
  <div class="course-container">
    <course-list></course-list>
  </div>
</template>

<script>
export default {
  name: 'CourseView'
}
</script>

<script setup>
import CourseList from '@/components/CourseList.vue'
</script>

<style scoped>
.course-container {
  padding: 20px;
}
</style> 