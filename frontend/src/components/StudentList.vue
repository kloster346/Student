<template>
  <div class="student-list">
    <h2>学生管理</h2>
    
    <!-- 添加学生表单 -->
    <el-form :inline="true" :model="newStudent" class="demo-form-inline">
      <el-form-item label="学号">
        <el-input v-model="newStudent.sno" placeholder="学号" />
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="newStudent.sname" placeholder="姓名" />
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="newStudent.ssex" placeholder="性别">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model.number="newStudent.sage" placeholder="年龄" type="number" />
      </el-form-item>
      <el-form-item label="系别">
        <el-input v-model="newStudent.sdept" placeholder="系别" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleAdd">添加学生</el-button>
      </el-form-item>
    </el-form>

    <!-- 学生列表 -->
    <el-table :data="students" style="width: 100%">
      <el-table-column prop="sno" label="学号" width="120" />
      <el-table-column prop="sname" label="姓名" width="120" />
      <el-table-column prop="ssex" label="性别" width="80" />
      <el-table-column prop="sage" label="年龄" width="80" />
      <el-table-column prop="sdept" label="系别" width="120" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 编辑对话框 -->
    <el-dialog v-model="dialogVisible" title="编辑学生信息">
      <el-form :model="editingStudent">
        <el-form-item label="姓名">
          <el-input v-model="editingStudent.sname" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="editingStudent.ssex">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model.number="editingStudent.sage" type="number" />
        </el-form-item>
        <el-form-item label="系别">
          <el-input v-model="editingStudent.sdept" />
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
import { getStudents, addStudent, updateStudent, deleteStudent } from '../api'

// 学生列表数据
const students = ref([])

// 新学生表单数据
const newStudent = ref({
  sno: '',
  sname: '',
  ssex: '男',
  sage: '',
  sdept: ''
})

// 编辑相关数据
const dialogVisible = ref(false)
const editingStudent = ref({})

// 获取学生列表
const fetchStudents = async () => {
  try {
    const response = await getStudents()
    students.value = response.data.data.list
  } catch (error) {
    ElMessage.error('获取学生列表失败')
  }
}

// 添加学生
const handleAdd = async () => {
  try {
    await addStudent(newStudent.value)
    ElMessage.success('添加成功')
    fetchStudents()
    // 清空表单
    newStudent.value = {
      sno: '',
      sname: '',
      ssex: '男',
      sage: '',
      sdept: ''
    }
  } catch (error) {
    ElMessage.error('添加失败')
  }
}

// 打开编辑对话框
const handleEdit = (row) => {
  editingStudent.value = { ...row }
  dialogVisible.value = true
}

// 更新学生信息
const handleUpdate = async () => {
  try {
    await updateStudent(editingStudent.value.sno, editingStudent.value)
    ElMessage.success('更新成功')
    dialogVisible.value = false
    fetchStudents()
  } catch (error) {
    ElMessage.error('更新失败')
  }
}

// 删除学生
const handleDelete = async (row) => {
  try {
    await deleteStudent(row.sno)
    ElMessage.success('删除成功')
    fetchStudents()
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

// 页面加载时获取学生列表
onMounted(() => {
  fetchStudents()
})
</script>

<style scoped>
.student-list {
  padding: 20px;
}
</style> 