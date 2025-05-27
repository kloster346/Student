<template>
  <div class="student-container">
    <!-- 搜索表单 -->
    <BaseForm
      ref="searchForm"
      :model="searchForm"
      :form-items="searchFormItems"
      label-width="80px"
      @search="handleSearch"
      @reset="handleReset"
    />
    
    <!-- 操作按钮 -->
    <div class="table-operations">
      <el-button type="primary" @click="handleAdd">新增学生</el-button>
      <el-button 
        type="danger" 
        :disabled="!selectedIds.length"
        @click="handleBatchDelete"
      >
        批量删除
      </el-button>
    </div>

    <!-- 数据表格 -->
    <BaseTable
      ref="baseTable"
      v-loading="loading"
      :data="tableData"
      :columns="columns"
      :pagination="pagination"
      @selection-change="handleSelectionChange"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
      <!-- 操作列 -->
      <template #operate="{ row }">
        <el-button type="text" @click="handleEdit(row)">编辑</el-button>
        <el-button type="text" class="delete-btn" @click="handleDelete(row)">删除</el-button>
      </template>
    </BaseTable>

    <!-- 新增/编辑弹窗 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
      @close="handleDialogClose"
    >
      <BaseForm
        ref="form"
        :model="form"
        :rules="rules"
        :form-items="formItems"
        label-width="80px"
      />
      <template #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getStudentList, createStudent, updateStudent, deleteStudent, batchDeleteStudents } from '@/api/student'
import BaseTable from '@/components/BaseTable/index.vue'
import BaseForm from '@/components/BaseForm/index.vue'

export default {
  name: 'StudentManagement',
  components: {
    BaseTable,
    BaseForm
  },
  setup() {
    // 表格加载状态
    const loading = ref(false)
    // 表格数据
    const tableData = ref([])
    // 选中的行ID
    const selectedIds = ref([])
    // 分页信息
    const pagination = reactive({
      total: 0,
      pageSize: 10,
      currentPage: 1
    })

    // 搜索表单
    const searchForm = reactive({
      keyword: ''
    })

    // 搜索表单配置
    const searchFormItems = [
      {
        prop: 'keyword',
        label: '搜索',
        type: 'input',
        placeholder: '请输入学号/姓名/专业'
      }
    ]

    // 表格列配置
    const columns = [
      {
        type: 'selection',
        width: 55,
        align: 'center'
      },
      {
        prop: 'sno',
        label: '学号',
        width: 120
      },
      {
        prop: 'sname',
        label: '姓名',
        width: 120
      },
      {
        prop: 'ssex',
        label: '性别',
        width: 80
      },
      {
        prop: 'sage',
        label: '年龄',
        width: 80
      },
      {
        prop: 'sdept',
        label: '专业',
        width: 120
      },
      {
        prop: 'operate',
        label: '操作',
        width: 150,
        fixed: 'right',
        slot: true
      }
    ]

    // 表单数据
    const form = reactive({
      sno: '',
      sname: '',
      ssex: '',
      sage: '',
      sdept: ''
    })

    // 表单校验规则
    const rules = {
      sno: [{ required: true, message: '请输入学号', trigger: 'blur' }],
      sname: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
      ssex: [{ required: true, message: '请选择性别', trigger: 'change' }],
      sage: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
      sdept: [{ required: true, message: '请输入专业', trigger: 'blur' }]
    }

    // 表单项配置
    const formItems = [
      {
        prop: 'sno',
        label: '学号',
        type: 'input',
        placeholder: '请输入学号'
      },
      {
        prop: 'sname',
        label: '姓名',
        type: 'input',
        placeholder: '请输入姓名'
      },
      {
        prop: 'ssex',
        label: '性别',
        type: 'select',
        placeholder: '请选择性别',
        options: [
          { label: '男', value: '男' },
          { label: '女', value: '女' }
        ]
      },
      {
        prop: 'sage',
        label: '年龄',
        type: 'input',
        placeholder: '请输入年龄'
      },
      {
        prop: 'sdept',
        label: '专业',
        type: 'input',
        placeholder: '请输入专业'
      }
    ]

    // 弹窗相关
    const dialogVisible = ref(false)
    const isEdit = ref(false)
    const dialogTitle = computed(() => isEdit.value ? '编辑学生' : '新增学生')

    // 获取表格数据
    const getList = async () => {
      loading.value = true
      try {
        const params = {
          page: pagination.currentPage,
          size: pagination.pageSize,
          keyword: searchForm.keyword
        }
        const { data } = await getStudentList(params)
        tableData.value = data.list
        pagination.total = data.total
      } catch (error) {
        console.error('获取学生列表失败:', error)
        ElMessage.error('获取学生列表失败')
      } finally {
        loading.value = false
      }
    }

    // 表格多选
    const handleSelectionChange = (selection) => {
      selectedIds.value = selection.map(item => item.sno)
    }

    // 搜索
    const handleSearch = () => {
      pagination.currentPage = 1
      getList()
    }

    // 重置搜索
    const handleReset = () => {
      Object.keys(searchForm).forEach(key => {
        searchForm[key] = ''
      })
      handleSearch()
    }

    // 分页
    const handleSizeChange = (val) => {
      pagination.pageSize = val
      getList()
    }

    const handleCurrentChange = (val) => {
      pagination.currentPage = val
      getList()
    }

    // 新增
    const handleAdd = () => {
      isEdit.value = false
      Object.keys(form).forEach(key => {
        form[key] = ''
      })
      dialogVisible.value = true
    }

    // 编辑
    const handleEdit = (row) => {
      isEdit.value = true
      Object.keys(form).forEach(key => {
        form[key] = row[key]
      })
      dialogVisible.value = true
    }

    // 删除
    const handleDelete = (row) => {
      ElMessageBox.confirm('确认删除该学生信息吗？', '提示', {
        type: 'warning'
      }).then(async () => {
        try {
          await deleteStudent(row.sno)
          ElMessage.success('删除成功')
          getList()
        } catch (error) {
          console.error('删除失败:', error)
          ElMessage.error('删除失败')
        }
      }).catch(() => {})
    }

    // 批量删除
    const handleBatchDelete = () => {
      if (!selectedIds.value.length) {
        ElMessage.warning('请选择要删除的学生')
        return
      }
      ElMessageBox.confirm(`确认删除选中的 ${selectedIds.value.length} 条记录吗？`, '提示', {
        type: 'warning'
      }).then(async () => {
        try {
          await batchDeleteStudents(selectedIds.value)
          ElMessage.success('批量删除成功')
          getList()
        } catch (error) {
          console.error('批量删除失败:', error)
          ElMessage.error('批量删除失败')
        }
      }).catch(() => {})
    }

    // 提交表单
    const handleSubmit = async () => {
      try {
        const valid = await form.value.validate()
        if (!valid) return

        if (isEdit.value) {
          await updateStudent(form.sno, form)
          ElMessage.success('更新成功')
        } else {
          await createStudent(form)
          ElMessage.success('创建成功')
        }
        
        dialogVisible.value = false
        getList()
      } catch (error) {
        console.error('提交失败:', error)
        ElMessage.error('提交失败')
      }
    }

    // 关闭弹窗
    const handleDialogClose = () => {
      Object.keys(form).forEach(key => {
        form[key] = ''
      })
    }

    // 初始化
    getList()

    return {
      loading,
      tableData,
      selectedIds,
      pagination,
      searchForm,
      searchFormItems,
      columns,
      form,
      rules,
      formItems,
      dialogVisible,
      dialogTitle,
      handleSelectionChange,
      handleSearch,
      handleReset,
      handleSizeChange,
      handleCurrentChange,
      handleAdd,
      handleEdit,
      handleDelete,
      handleBatchDelete,
      handleSubmit,
      handleDialogClose
    }
  }
}
</script>

<style lang="scss" scoped>
.student-container {
  padding: 20px;

  .table-operations {
    margin: 16px 0;
  }

  .delete-btn {
    color: #f56c6c;
  }
}
</style> 