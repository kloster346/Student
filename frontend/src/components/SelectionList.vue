<template>
  <div class="selection-list">
    <h2>选课管理</h2>
    
    <!-- 添加选课表单 -->
    <el-form :inline="true" :model="newSelection" class="demo-form-inline">
      <el-form-item label="学号">
        <el-select v-model="newSelection.sno" placeholder="请选择学生" filterable>
          <el-option
            v-for="student in students"
            :key="student.sno"
            :label="student.sname + '(' + student.sno + ')'"
            :value="student.sno"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程">
        <el-select v-model="newSelection.cno" placeholder="请选择课程" filterable>
          <el-option
            v-for="course in courses"
            :key="course.cno"
            :label="course.cname + '(' + course.cno + ')'"
            :value="course.cno"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleAdd">选课</el-button>
      </el-form-item>
    </el-form>

    <!-- 选课记录列表 -->
    <el-table :data="selections" style="width: 100%">
      <el-table-column label="学号" width="120">
        <template #default="scope">
          {{ getStudentName(scope.row.sno) }}
        </template>
      </el-table-column>
      <el-table-column label="课程" width="180">
        <template #default="scope">
          {{ getCourseName(scope.row.cno) }}
        </template>
      </el-table-column>
      <el-table-column prop="grade" label="成绩" width="120">
        <template #default="scope">
          <span v-if="scope.row.grade">{{ scope.row.grade }}</span>
          <span v-else>未录入</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250">
        <template #default="scope">
          <el-button 
            size="small" 
            type="primary" 
            @click="handleGrade(scope.row)"
            :disabled="!!scope.row.grade"
          >录入成绩</el-button>
          <el-button 
            size="small" 
            type="danger" 
            @click="handleDelete(scope.row)"
          >退课</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 成绩录入对话框 -->
    <el-dialog v-model="dialogVisible" title="成绩录入">
      <el-form :model="gradeForm">
        <el-form-item label="成绩">
          <el-input-number 
            v-model="gradeForm.grade" 
            :min="0" 
            :max="100" 
            :precision="1"
          />
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
import { 
  getSelections, 
  addSelection, 
  updateGrade, 
  deleteSelection,
  getStudents,
  getCourses 
} from '../api'

// 数据列表
const selections = ref([])
const students = ref([])
const courses = ref([])

// 新选课数据
const newSelection = ref({
  sno: '',
  cno: ''
})

// 成绩录入相关数据
const dialogVisible = ref(false)
const gradeForm = ref({
  sno: '',
  cno: '',
  grade: 60
})

// 获取选课记录
const fetchSelections = async () => {
  try {
    const response = await getSelections()
    selections.value = response.data.data.list
  } catch (error) {
    ElMessage.error('获取选课记录失败')
  }
}

// 获取学生列表
const fetchStudents = async () => {
  try {
    const response = await getStudents()
    students.value = response.data.data.list
  } catch (error) {
    ElMessage.error('获取学生列表失败')
  }
}

// 获取课程列表
const fetchCourses = async () => {
  try {
    const response = await getCourses()
    courses.value = response.data.data.list
  } catch (error) {
    ElMessage.error('获取课程列表失败')
  }
}

// 获取学生姓名
const getStudentName = (sno) => {
  const student = students.value.find(s => s.sno === sno)
  return student ? `${student.sname}(${student.sno})` : sno
}

// 获取课程名称
const getCourseName = (cno) => {
  const course = courses.value.find(c => c.cno === cno)
  return course ? `${course.cname}(${course.cno})` : cno
}

// 添加选课记录
const handleAdd = async () => {
  try {
    await addSelection(newSelection.value)
    ElMessage.success('选课成功')
    fetchSelections()
    // 清空表单
    newSelection.value = {
      sno: '',
      cno: ''
    }
  } catch (error) {
    ElMessage.error('选课失败')
  }
}

// 打开成绩录入对话框
const handleGrade = (row) => {
  gradeForm.value = {
    sno: row.sno,
    cno: row.cno,
    grade: 60
  }
  dialogVisible.value = true
}

// 更新成绩
const handleUpdate = async () => {
  try {
    await updateGrade(
      gradeForm.value.sno, 
      gradeForm.value.cno, 
      gradeForm.value.grade
    )
    ElMessage.success('成绩录入成功')
    dialogVisible.value = false
    fetchSelections()
  } catch (error) {
    ElMessage.error('成绩录入失败')
  }
}

// 退课
const handleDelete = async (row) => {
  try {
    await deleteSelection(row.sno, row.cno)
    ElMessage.success('退课成功')
    fetchSelections()
  } catch (error) {
    ElMessage.error('退课失败')
  }
}

// 页面加载时获取数据
onMounted(() => {
  fetchSelections()
  fetchStudents()
  fetchCourses()
})
</script>

<style scoped>
.selection-list {
  padding: 20px;
}

.el-select {
  width: 220px;
}
</style> 