<template>
  <div class="selection-container">
    <selection-list></selection-list>
  </div>
</template>

<script setup>
import SelectionList from '@/components/SelectionList.vue'

// 定义组件名称
defineOptions({
  name: 'SelectionView'
})
</script>

<style scoped>
.selection-container {
  padding: 20px;
}
</style> 