<template>
  <div class="course-list">
    <h2>课程管理</h2>
    
    <!-- 添加课程表单 -->
    <el-form :inline="true" :model="newCourse" class="demo-form-inline">
      <el-form-item label="课程号">
        <el-input v-model="newCourse.cno" placeholder="课程号" />
      </el-form-item>
      <el-form-item label="课程名">
        <el-input v-model="newCourse.cname" placeholder="课程名" />
      </el-form-item>
      <el-form-item label="学分">
        <el-input-number v-model="newCourse.credit" :min="1" :max="10" placeholder="学分" />
      </el-form-item>
      <el-form-item label="开课系别">
        <el-input v-model="newCourse.cdept" placeholder="开课系别" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleAdd">添加课程</el-button>
      </el-form-item>
    </el-form>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入课程号或课程名搜索"
        style="width: 200px"
        clearable
        @clear="handleSearch"
        @keyup.enter="handleSearch"
      >
        <template #append>
          <el-button @click="handleSearch">
            <el-icon><Search /></el-icon>
          </el-button>
        </template>
      </el-input>
    </div>

    <!-- 课程列表 -->
    <el-table :data="courses" style="width: 100%" v-loading="loading">
      <el-table-column prop="cno" label="课程号" width="120" />
      <el-table-column prop="cname" label="课程名" width="180" />
      <el-table-column prop="credit" label="学分" width="100" />
      <el-table-column prop="cdept" label="开课系别" width="180" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页器 -->
    <div class="pagination-container">
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :page-sizes="[10, 20, 30, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 编辑对话框 -->
    <el-dialog v-model="dialogVisible" title="编辑课程信息">
      <el-form :model="editingCourse">
        <el-form-item label="课程名">
          <el-input v-model="editingCourse.cname" />
        </el-form-item>
        <el-form-item label="学分">
          <el-input-number v-model="editingCourse.credit" :min="1" :max="10" />
        </el-form-item>
        <el-form-item label="开课系别">
          <el-input v-model="editingCourse.cdept" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleUpdate">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { getCourses, addCourse, updateCourse, deleteCourse } from '../api'

// 课程列表数据
const courses = ref([])
const loading = ref(false)

// 分页相关
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 搜索相关
const searchKeyword = ref('')

// 新课程表单数据
const newCourse = ref({
  cno: '',
  cname: '',
  credit: 3,
  cdept: ''
})

// 编辑相关数据
const dialogVisible = ref(false)
const editingCourse = ref({})

// 获取课程列表
const fetchCourses = async () => {
  loading.value = true
  try {
    const response = await getCourses({
      page: currentPage.value,
      size: pageSize.value,
      keyword: searchKeyword.value
    })
    courses.value = response.data.data.list
    total.value = response.data.data.total
  } catch (error) {
    ElMessage.error('获取课程列表失败')
  } finally {
    loading.value = false
  }
}

// 处理搜索
const handleSearch = () => {
  currentPage.value = 1
  fetchCourses()
}

// 处理页码改变
const handleCurrentChange = (page) => {
  currentPage.value = page
  fetchCourses()
}

// 处理每页数量改变
const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  fetchCourses()
}

// 添加课程
const handleAdd = async () => {
  try {
    await addCourse(newCourse.value)
    ElMessage.success('添加成功')
    fetchCourses()
    // 清空表单
    newCourse.value = {
      cno: '',
      cname: '',
      credit: 3,
      cdept: ''
    }
  } catch (error) {
    ElMessage.error('添加失败')
  }
}

// 打开编辑对话框
const handleEdit = (row) => {
  editingCourse.value = { ...row }
  dialogVisible.value = true
}

// 更新课程信息
const handleUpdate = async () => {
  try {
    await updateCourse(editingCourse.value.cno, editingCourse.value)
    ElMessage.success('更新成功')
    dialogVisible.value = false
    fetchCourses()
  } catch (error) {
    ElMessage.error('更新失败')
  }
}

// 删除课程
const handleDelete = async (row) => {
  try {
    await deleteCourse(row.cno)
    ElMessage.success('删除成功')
    // 如果当前页只有一条数据，删除后跳转到上一页
    if (courses.value.length === 1 && currentPage.value > 1) {
      currentPage.value--
    }
    fetchCourses()
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

// 页面加载时获取课程列表
onMounted(() => {
  fetchCourses()
})
</script>

<style scoped>
.course-list {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style> 