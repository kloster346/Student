<template>
  <div class="selection-container">
    <selection-list></selection-list>
  </div>
</template>

<script>
export default {
  name: 'SelectionView'
}
</script>

<script setup>
import SelectionList from '@/components/SelectionList.vue'
</script>

<style scoped>
.selection-container {
  padding: 20px;
}
</style> 