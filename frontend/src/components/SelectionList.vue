<template>
  <div class="selection-list">
    <!-- 工具栏 -->
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">添加选课</el-button>
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
      <el-table-column prop="cno" label="课程号" width="120" />
      <el-table-column prop="grade" label="成绩" width="100">
        <template #default="{ row }">
          <el-input-number
            v-model="row.grade"
            :min="0"
            :max="100"
            :precision="1"
            :controls="false"
            style="width: 80px"
            @change="(value) => handleGradeChange(row, value)"
          />
        </template>
      </el-table-column>
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
      :title="dialogType === 'add' ? '添加选课' : '编辑选课'"
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
        <el-form-item label="课程号" prop="cno">
          <el-input v-model="form.cno" :disabled="dialogType === 'edit'" />
        </el-form-item>
        <el-form-item label="成绩" prop="grade">
          <el-input-number
            v-model="form.grade"
            :min="0"
            :max="100"
            :precision="1"
            style="width: 100%"
          />
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
import { getSelections, addSelection, updateGrade, deleteSelection, deleteSelectionBatch } from '@/api'

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
  cno: '',
  grade: null
})

// 表单验证规则
const rules = {
  sno: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { pattern: /^\d{7}$/, message: '学号必须是7位数字', trigger: 'blur' }
  ],
  cno: [
    { required: true, message: '请输入课程号', trigger: 'blur' },
    { pattern: /^[A-Z]\d{3}$/, message: '课程号必须是1个大写字母后跟3位数字', trigger: 'blur' }
  ],
  grade: [
    { required: false, message: '请输入成绩', trigger: 'blur' },
    { type: 'number', min: 0, max: 100, message: '成绩必须在0-100之间', trigger: 'blur' }
  ]
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const params = {
      page: page.value,
      size: size.value
    }
    const response = await getSelections(params)
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
  form.cno = ''
  form.grade = null
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
    await deleteSelection(row.sno, row.cno)
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
    // 修改数据格式以符合API要求
    const data = {
      snos: selectedRows.value.map(row => row.sno),
      cnos: selectedRows.value.map(row => row.cno)
    }
    await deleteSelectionBatch(data)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('批量删除失败，请重试')
    }
  }
}

// 处理成绩变化
const handleGradeChange = async (row, value) => {
  try {
    await updateGrade(row.sno, row.cno, value)
    ElMessage.success('成绩更新成功')
  } catch (error) {
    console.error('成绩更新失败:', error)
    row.grade = value // 回滚到原值
  }
}

// 处理表单提交
const handleSubmit = async () => {
  if (!formRef.value) return
  try {
    await formRef.value.validate()
    if (dialogType.value === 'add') {
      await addSelection(form)
      ElMessage.success('添加成功')
    } else {
      await updateGrade(form.sno, form.cno, form.grade)
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
.selection-list {
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