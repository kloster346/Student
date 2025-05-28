<template>
  <div class="course-list">
    <!-- 工具栏 -->
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">添加课程</el-button>
      <el-button type="danger" :disabled="!selectedRows.length" @click="handleBatchDelete">
        批量删除
      </el-button>
      <el-button type="success" @click="showImportDialog">导入数据</el-button>
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
      <el-table-column prop="cno" label="课程号" width="120" />
      <el-table-column prop="cname" label="课程名称" width="200" />
      <el-table-column prop="credit" label="学分" width="80" />
      <el-table-column prop="cdept" label="开课系别" />
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
      :title="dialogType === 'add' ? '添加课程' : '编辑课程'"
      width="500px"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="80px"
        style="padding: 0 20px"
      >
        <el-form-item label="课程号" prop="cno">
          <el-input v-model="form.cno" :disabled="dialogType === 'edit'" />
        </el-form-item>
        <el-form-item label="课程名称" prop="cname">
          <el-input v-model="form.cname" />
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input-number v-model="form.credit" :min="0" :max="10" :precision="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="开课系别" prop="cdept">
          <el-input v-model="form.cdept" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog v-model="importDialogVisible" title="导入课程数据" width="650px">
      <file-import
        :upload-url="'http://localhost:8081/api/courses/import'"
        :format-example="csvFormatExample"
        @success="handleImportSuccess"
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getCourses, addCourse, updateCourse, deleteCourse, deleteCourseBatch } from '@/api'
import FileImport from './FileImport.vue'

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
  cno: '',
  cname: '',
  credit: 2,
  cdept: ''
})

// 表单验证规则
const rules = {
  cno: [
    { required: true, message: '请输入课程号', trigger: 'blur' },
    { pattern: /^[A-Z]\d{3}$/, message: '课程号必须是1个大写字母后跟3位数字', trigger: 'blur' }
  ],
  cname: [
    { required: true, message: '请输入课程名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  credit: [{ required: true, message: '请输入学分', trigger: 'blur' }],
  cdept: [{ required: true, message: '请输入开课系别', trigger: 'blur' }]
}

// 导入相关
const importDialogVisible = ref(false)
const csvFormatExample = `课程号,课程名,学分,开课系别
C001,计算机网络,4,计算机系
C002,数据库原理,3,信息系`

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: page.value,
      size: size.value
    }
    const response = await getCourses(params)
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
  form.cno = ''
  form.cname = ''
  form.credit = 2
  form.cdept = ''
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
    await deleteCourse(row.cno)
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
    const ids = selectedRows.value.map(row => row.cno)
    await deleteCourseBatch(ids)
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
      await addCourse(form)
      ElMessage.success('添加成功')
    } else {
      await updateCourse(form.cno, form)
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

const showImportDialog = () => {
  importDialogVisible.value = true
}

const handleImportSuccess = () => {
  importDialogVisible.value = false
  loadData() // 重新加载数据
}

// 初始加载
loadData()
</script>

<style scoped>
.course-list {
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