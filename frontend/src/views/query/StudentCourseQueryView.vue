<template>
  <div class="query-container">
    <!-- 查询表单 -->
    <el-form :model="queryForm" :inline="true" class="query-form">
      <el-form-item label="学生姓名">
        <el-input
          v-model="queryForm.studentName"
          placeholder="请输入学生姓名"
          clearable
        />
      </el-form-item>
      <el-form-item label="课程名称">
        <el-input
          v-model="queryForm.courseName"
          placeholder="请输入课程名称"
          clearable
        />
      </el-form-item>
      <el-form-item label="成绩范围">
        <el-input-number
          v-model="queryForm.minGrade"
          :min="0"
          :max="100"
          placeholder="最低分"
          class="grade-input"
        />
        <span class="separator">-</span>
        <el-input-number
          v-model="queryForm.maxGrade"
          :min="0"
          :max="100"
          placeholder="最高分"
          class="grade-input"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据表格 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      border
      style="width: 100%"
      @sort-change="handleSortChange"
    >
      <el-table-column prop="studentNo" label="学号" sortable="custom" />
      <el-table-column prop="studentName" label="学生姓名" sortable="custom" />
      <el-table-column prop="courseNo" label="课程号" sortable="custom" />
      <el-table-column prop="courseName" label="课程名称" sortable="custom" />
      <el-table-column prop="grade" label="成绩" sortable="custom" />
      <el-table-column prop="credit" label="学分" sortable="custom" />
      <el-table-column prop="department" label="开课系部" />
    </el-table>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        :current-page="page"
        :page-size="size"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        @update:current-page="page = $event"
        @update:page-size="size = $event"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: 'StudentCourseQueryView'
}
</script>

<script setup>
import { ref, reactive } from 'vue'
import { queryStudentCourses } from '@/api'

// 查询表单数据
const queryForm = reactive({
  studentName: '',
  courseName: '',
  minGrade: null,
  maxGrade: null
})

// 表格数据
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const page = ref(1)
const size = ref(10)
const sortField = ref('')
const sortOrder = ref('')

// 查询数据
const fetchData = async () => {
  loading.value = true
  try {
    const params = {
      page: page.value,
      size: size.value,
      studentName: queryForm.studentName || undefined,
      courseName: queryForm.courseName || undefined,
      minGrade: queryForm.minGrade || undefined,
      maxGrade: queryForm.maxGrade || undefined,
      sortField: sortField.value || undefined,
      sortOrder: sortOrder.value || undefined
    }

    const response = await queryStudentCourses(params)
    tableData.value = response.list
    total.value = response.total
  } finally {
    loading.value = false
  }
}

// 处理查询
const handleQuery = () => {
  page.value = 1
  fetchData()
}

// 处理重置
const handleReset = () => {
  Object.keys(queryForm).forEach(key => {
    queryForm[key] = null
  })
  page.value = 1
  sortField.value = ''
  sortOrder.value = ''
  fetchData()
}

// 处理排序变化
const handleSortChange = ({ prop, order }) => {
  sortField.value = prop
  sortOrder.value = order === 'ascending' ? 'asc' : order === 'descending' ? 'desc' : ''
  fetchData()
}

// 处理每页数量变化
const handleSizeChange = (val) => {
  size.value = val
  fetchData()
}

// 处理页码变化
const handleCurrentChange = (val) => {
  page.value = val
  fetchData()
}

// 初始加载
fetchData()
</script>

<style scoped>
.query-container {
  padding: 20px;
}

.query-form {
  margin-bottom: 20px;
  background-color: #fff;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.grade-input {
  width: 110px;
}

.separator {
  margin: 0 10px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style> 