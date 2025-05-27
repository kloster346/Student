<template>
  <div class="app-wrapper">
    <!-- 侧边栏 -->
    <div class="sidebar-container">
      <el-menu
        :default-active="$route.path"
        class="el-menu-vertical"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/student">
          <el-icon><User /></el-icon>
          <span>学生管理</span>
        </el-menu-item>
        <el-menu-item index="/course">
          <el-icon><Reading /></el-icon>
          <span>课程管理</span>
        </el-menu-item>
        <el-menu-item index="/sc">
          <el-icon><List /></el-icon>
          <span>选课管理</span>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- 主要内容区 -->
    <div class="main-container">
      <router-view />
    </div>
  </div>
</template>

<script>
import { User, Reading, List } from '@element-plus/icons-vue'

export default {
  name: 'Layout',
  components: {
    User,
    Reading,
    List
  }
}
</script>

<style scoped>
.app-wrapper {
  display: flex;
  height: 100vh;
  width: 100%;
}

.sidebar-container {
  width: 210px;
  height: 100%;
  background: #304156;
  position: fixed;
  left: 0;
  top: 0;
  bottom: 0;
  overflow-y: auto;
}

.el-menu-vertical {
  border-right: none;
}

.main-container {
  margin-left: 210px;
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}
</style> 