<template>
  <div class="student-list">
    <!-- 工具栏 -->
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">添加学生</el-button>
      <el-button type="danger" :disabled="!selectedRows.length" @click="handleBatchDelete">
        批量删除
      </el-button>
    </div>

    <!-- 数据表格 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="sno" label="学号" width="120" />
      <el-table-column prop="sname" label="姓名" width="120" />
      <el-table-column prop="ssex" label="性别" width="80" />
      <el-table-column prop="sage" label="年龄" width="80" />
      <el-table-column prop="sdept" label="系别" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
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

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '添加学生' : '编辑学生'"
      width="500px"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="80px"
        style="padding: 0 20px"
      >
        <el-form-item label="学号" prop="sno">
          <el-input v-model="form.sno" :disabled="dialogType === 'edit'" />
        </el-form-item>
        <el-form-item label="姓名" prop="sname">
          <el-input v-model="form.sname" />
        </el-form-item>
        <el-form-item label="性别" prop="ssex">
          <el-select v-model="form.ssex" style="width: 100%">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="sage">
          <el-input-number v-model="form.sage" :min="0" :max="150" style="width: 100%" />
        </el-form-item>
        <el-form-item label="系别" prop="sdept">
          <el-input v-model="form.sdept" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getStudents, addStudent, updateStudent, deleteStudent, deleteStudentBatch } from '@/api'

// 表格数据
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const page = ref(1)
const size = ref(10)
const selectedRows = ref([])

// 对话框数据
const dialogVisible = ref(false)
const dialogType = ref('add')
const formRef = ref(null)
const form = reactive({
  sno: '',
  sname: '',
  ssex: '男',
  sage: 18,
  sdept: ''
})

// 表单验证规则
const rules = {
  sno: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { pattern: /^\d{7}$/, message: '学号必须是7位数字', trigger: 'blur' }
  ],
  sname: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
  ],
  ssex: [{ required: true, message: '请选择性别', trigger: 'change' }],
  sage: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  sdept: [{ required: true, message: '请输入系别', trigger: 'blur' }]
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: page.value,
      size: size.value
    }
    const response = await getStudents(params)
    tableData.value = response.list
    total.value = response.total
  } finally {
    loading.value = false
  }
}

// 处理选择变化
const handleSelectionChange = (rows) => {
  selectedRows.value = rows
}

// 处理添加
const handleAdd = () => {
  dialogType.value = 'add'
  form.sno = ''
  form.sname = ''
  form.ssex = '男'
  form.sage = 18
  form.sdept = ''
  dialogVisible.value = true
}

// 处理编辑
const handleEdit = (row) => {
  dialogType.value = 'edit'
  Object.assign(form, row)
  dialogVisible.value = true
}

// 处理删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这条记录吗？', '提示', {
      type: 'warning'
    })
    await deleteStudent(row.sno)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
    }
  }
}

// 处理批量删除
const handleBatchDelete = async () => {
  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedRows.value.length} 条记录吗？`,
      '提示',
      {
        type: 'warning'
      }
    )
    const ids = selectedRows.value.map(row => row.sno)
    await deleteStudentBatch(ids)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
    }
  }
}

// 处理表单提交
const handleSubmit = async () => {
  if (!formRef.value) return
  try {
    await formRef.value.validate()
    if (dialogType.value === 'add') {
      await addStudent(form)
      ElMessage.success('添加成功')
    } else {
      await updateStudent(form.sno, form)
      ElMessage.success('更新成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('提交失败:', error)
    }
  }
}

// 处理每页数量变化
const handleSizeChange = (val) => {
  size.value = val
  loadData()
}

// 处理页码变化
const handleCurrentChange = (val) => {
  page.value = val
  loadData()
}

// 初始加载
loadData()
</script>

<style scoped>
.student-list {
  padding: 20px;
}

.toolbar {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style> 