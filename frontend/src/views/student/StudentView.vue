<template>
  <div class="student-container">
    <student-list></student-list>
  </div>
</template>

<script>
export default {
  name: 'StudentView'
}
</script>

<script setup>
import StudentList from '@/components/StudentList.vue'
</script>

<style scoped>
.student-container {
  padding: 20px;
}
</style> 