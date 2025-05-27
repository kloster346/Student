<template>
  <div class="sc-container">
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
      <el-button type="primary" @click="handleAdd">新增选课</el-button>
      <el-button 
        type="danger" 
        :disabled="!selectedRecords.length"
        @click="handleBatchDelete"
      >
        批量退课
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
      <!-- 成绩列 -->
      <template #grade="{ row }">
        <span v-if="!row.isEditing">{{ row.grade || '-' }}</span>
        <el-input
          v-else
          v-model="row.tempGrade"
          type="number"
          :min="0"
          :max="100"
          @blur="handleGradeBlur(row)"
          @keyup.enter="handleGradeBlur(row)"
        />
      </template>
      <!-- 操作列 -->
      <template #operate="{ row }">
        <el-button 
          type="text" 
          @click="handleEditGrade(row)"
        >
          {{ row.isEditing ? '保存' : '录入成绩' }}
        </el-button>
        <el-button type="text" class="delete-btn" @click="handleDelete(row)">退课</el-button>
      </template>
    </BaseTable>

    <!-- 新增选课弹窗 -->
    <el-dialog
      :title="'新增选课'"
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
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getScList, createSc, updateGrade, deleteSc, batchDeleteSc } from '@/api/sc'
import BaseTable from '@/components/BaseTable/index.vue'
import BaseForm from '@/components/BaseForm/index.vue'

export default {
  name: 'ScManagement',
  components: {
    BaseTable,
    BaseForm
  },
  setup() {
    // 表格加载状态
    const loading = ref(false)
    // 表格数据
    const tableData = ref([])
    // 选中的记录
    const selectedRecords = ref([])
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
        placeholder: '请输入学号/课程号'
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
        prop: 'cno',
        label: '课程号',
        width: 120
      },
      {
        prop: 'grade',
        label: '成绩',
        width: 120,
        slot: true
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
      cno: ''
    })

    // 表单校验规则
    const rules = {
      sno: [{ required: true, message: '请输入学号', trigger: 'blur' }],
      cno: [{ required: true, message: '请输入课程号', trigger: 'blur' }]
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
        prop: 'cno',
        label: '课程号',
        type: 'input',
        placeholder: '请输入课程号'
      }
    ]

    // 弹窗相关
    const dialogVisible = ref(false)

    // 获取表格数据
    const getList = async () => {
      loading.value = true
      try {
        const params = {
          page: pagination.currentPage,
          size: pagination.pageSize,
          keyword: searchForm.keyword
        }
        const { data } = await getScList(params)
        // 为每条记录添加编辑状态和临时成绩
        tableData.value = data.list.map(item => ({
          ...item,
          isEditing: false,
          tempGrade: item.grade
        }))
        pagination.total = data.total
      } catch (error) {
        console.error('获取选课列表失败:', error)
        ElMessage.error('获取选课列表失败')
      } finally {
        loading.value = false
      }
    }

    // 表格多选
    const handleSelectionChange = (selection) => {
      selectedRecords.value = selection.map(item => ({
        sno: item.sno,
        cno: item.cno
      }))
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
      Object.keys(form).forEach(key => {
        form[key] = ''
      })
      dialogVisible.value = true
    }

    // 编辑成绩
    const handleEditGrade = (row) => {
      if (row.isEditing) {
        // 保存成绩
        const grade = parseFloat(row.tempGrade)
        if (isNaN(grade) || grade < 0 || grade > 100) {
          ElMessage.warning('请输入0-100之间的成绩')
          return
        }
        updateGrade(row.sno, row.cno, { grade })
          .then(() => {
            row.grade = grade
            row.isEditing = false
            ElMessage.success('成绩更新成功')
          })
          .catch(() => {
            row.tempGrade = row.grade
          })
      } else {
        // 进入编辑状态
        row.isEditing = true
        row.tempGrade = row.grade
      }
    }

    // 成绩输入框失焦
    const handleGradeBlur = (row) => {
      if (!row.isEditing) return
      
      const grade = parseFloat(row.tempGrade)
      if (isNaN(grade) || grade < 0 || grade > 100) {
        row.tempGrade = row.grade
        ElMessage.warning('请输入0-100之间的成绩')
      }
    }

    // 退课
    const handleDelete = (row) => {
      ElMessageBox.confirm('确认退选该课程吗？', '提示', {
        type: 'warning'
      }).then(async () => {
        try {
          await deleteSc(row.sno, row.cno)
          ElMessage.success('退课成功')
          getList()
        } catch (error) {
          console.error('退课失败:', error)
          ElMessage.error('退课失败')
        }
      }).catch(() => {})
    }

    // 批量退课
    const handleBatchDelete = () => {
      if (!selectedRecords.value.length) {
        ElMessage.warning('请选择要退选的课程')
        return
      }
      ElMessageBox.confirm(`确认退选选中的 ${selectedRecords.value.length} 门课程吗？`, '提示', {
        type: 'warning'
      }).then(async () => {
        try {
          await batchDeleteSc(selectedRecords.value)
          ElMessage.success('批量退课成功')
          getList()
        } catch (error) {
          console.error('批量退课失败:', error)
          ElMessage.error('批量退课失败')
        }
      }).catch(() => {})
    }

    // 提交表单
    const handleSubmit = async () => {
      try {
        const valid = await form.value.validate()
        if (!valid) return

        await createSc(form)
        ElMessage.success('选课成功')
        dialogVisible.value = false
        getList()
      } catch (error) {
        console.error('选课失败:', error)
        ElMessage.error('选课失败')
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
      selectedRecords,
      pagination,
      searchForm,
      searchFormItems,
      columns,
      form,
      rules,
      formItems,
      dialogVisible,
      handleSelectionChange,
      handleSearch,
      handleReset,
      handleSizeChange,
      handleCurrentChange,
      handleAdd,
      handleEditGrade,
      handleGradeBlur,
      handleDelete,
      handleBatchDelete,
      handleSubmit,
      handleDialogClose
    }
  }
}
</script>

<style lang="scss" scoped>
.sc-container {
  padding: 20px;

  .table-operations {
    margin: 16px 0;
  }

  .delete-btn {
    color: #f56c6c;
  }
}
</style> 