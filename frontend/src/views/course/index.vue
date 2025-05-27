<template>
  <div class="course-container">
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
      <el-button type="primary" @click="handleAdd">新增课程</el-button>
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
import { getCourseList, createCourse, updateCourse, deleteCourse, batchDeleteCourses } from '@/api/course'
import BaseTable from '@/components/BaseTable/index.vue'
import BaseForm from '@/components/BaseForm/index.vue'

export default {
  name: 'CourseManagement',
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
        placeholder: '请输入课程号/课程名称/开课院系'
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
        prop: 'cno',
        label: '课程号',
        width: 120
      },
      {
        prop: 'cname',
        label: '课程名称',
        width: 180
      },
      {
        prop: 'credit',
        label: '学分',
        width: 100
      },
      {
        prop: 'cdept',
        label: '开课院系',
        width: 180
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
      cno: '',
      cname: '',
      credit: '',
      cdept: ''
    })

    // 表单校验规则
    const rules = {
      cno: [{ required: true, message: '请输入课程号', trigger: 'blur' }],
      cname: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
      credit: [
        { required: true, message: '请输入学分', trigger: 'blur' },
        { type: 'number', message: '学分必须为数字', trigger: 'blur' }
      ],
      cdept: [{ required: true, message: '请输入开课院系', trigger: 'blur' }]
    }

    // 表单项配置
    const formItems = [
      {
        prop: 'cno',
        label: '课程号',
        type: 'input',
        placeholder: '请输入课程号'
      },
      {
        prop: 'cname',
        label: '课程名称',
        type: 'input',
        placeholder: '请输入课程名称'
      },
      {
        prop: 'credit',
        label: '学分',
        type: 'input',
        inputType: 'number',
        placeholder: '请输入学分'
      },
      {
        prop: 'cdept',
        label: '开课院系',
        type: 'input',
        placeholder: '请输入开课院系'
      }
    ]

    // 弹窗相关
    const dialogVisible = ref(false)
    const isEdit = ref(false)
    const dialogTitle = computed(() => isEdit.value ? '编辑课程' : '新增课程')

    // 获取表格数据
    const getList = async () => {
      loading.value = true
      try {
        const params = {
          page: pagination.currentPage,
          size: pagination.pageSize,
          keyword: searchForm.keyword
        }
        const { data } = await getCourseList(params)
        tableData.value = data.list
        pagination.total = data.total
      } catch (error) {
        console.error('获取课程列表失败:', error)
        ElMessage.error('获取课程列表失败')
      } finally {
        loading.value = false
      }
    }

    // 表格多选
    const handleSelectionChange = (selection) => {
      selectedIds.value = selection.map(item => item.cno)
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
      ElMessageBox.confirm('确认删除该课程信息吗？', '提示', {
        type: 'warning'
      }).then(async () => {
        try {
          await deleteCourse(row.cno)
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
        ElMessage.warning('请选择要删除的课程')
        return
      }
      ElMessageBox.confirm(`确认删除选中的 ${selectedIds.value.length} 条记录吗？`, '提示', {
        type: 'warning'
      }).then(async () => {
        try {
          await batchDeleteCourses(selectedIds.value)
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
          await updateCourse(form.cno, form)
          ElMessage.success('更新成功')
        } else {
          await createCourse(form)
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
.course-container {
  padding: 20px;

  .table-operations {
    margin: 16px 0;
  }

  .delete-btn {
    color: #f56c6c;
  }
}
</style> 