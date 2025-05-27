<template>
  <div class="base-table">
    <!-- 表格主体 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      :border="border"
      :stripe="stripe"
      @selection-change="handleSelectionChange"
      @sort-change="handleSortChange"
    >
      <!-- 多选列 -->
      <el-table-column
        v-if="showSelection"
        type="selection"
        width="55"
        align="center"
      />
      <!-- 序号列 -->
      <el-table-column
        v-if="showIndex"
        type="index"
        width="60"
        label="序号"
        align="center"
      />
      <!-- 动态列 -->
      <el-table-column
        v-for="(column, index) in columns"
        :key="index"
        :prop="column.prop"
        :label="column.label"
        :width="column.width"
        :align="column.align || 'left'"
        :sortable="column.sortable"
        :fixed="column.fixed"
      >
        <template #default="scope">
          <!-- 自定义列内容插槽 -->
          <slot
            :name="column.prop"
            :row="scope.row"
            :column="column"
            :index="scope.$index"
          >
            {{ scope.row[column.prop] }}
          </slot>
        </template>
      </el-table-column>
      <!-- 操作列 -->
      <el-table-column
        v-if="showOperation"
        :label="operationLabel"
        :width="operationWidth"
        :fixed="operationFixed"
        align="center"
      >
        <template #default="scope">
          <slot name="operation" :row="scope.row" :index="scope.$index">
            <!-- 默认操作按钮 -->
            <el-button
              v-if="showEdit"
              type="primary"
              link
              @click="handleEdit(scope.row)"
            >
              编辑
            </el-button>
            <el-button
              v-if="showDelete"
              type="danger"
              link
              @click="handleDelete(scope.row)"
            >
              删除
            </el-button>
          </slot>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页器 -->
    <div class="pagination-container" v-if="showPagination">
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :page-sizes="pageSizes"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        @update:current-page="page => currentPage = page"
        @update:page-size="size => pageSize = size"
      />
    </div>
  </div>
</template>

<script>
import { defineComponent, ref, watch } from 'vue'

export default defineComponent({
  name: 'BaseTable',
  props: {
    // 表格数据
    data: {
      type: Array,
      default: () => []
    },
    // 表格列配置
    columns: {
      type: Array,
      default: () => []
    },
    // 是否显示多选
    showSelection: {
      type: Boolean,
      default: false
    },
    // 是否显示序号
    showIndex: {
      type: Boolean,
      default: true
    },
    // 是否显示边框
    border: {
      type: Boolean,
      default: true
    },
    // 是否显示斑马纹
    stripe: {
      type: Boolean,
      default: true
    },
    // 是否显示操作列
    showOperation: {
      type: Boolean,
      default: true
    },
    // 操作列标题
    operationLabel: {
      type: String,
      default: '操作'
    },
    // 操作列宽度
    operationWidth: {
      type: String,
      default: '150'
    },
    // 操作列是否固定
    operationFixed: {
      type: String,
      default: 'right'
    },
    // 是否显示编辑按钮
    showEdit: {
      type: Boolean,
      default: true
    },
    // 是否显示删除按钮
    showDelete: {
      type: Boolean,
      default: true
    },
    // 是否显示分页
    showPagination: {
      type: Boolean,
      default: true
    },
    // 总条数
    total: {
      type: Number,
      default: 0
    },
    // 每页显示条数选项
    pageSizes: {
      type: Array,
      default: () => [10, 20, 30, 50]
    },
    // 加载状态
    loading: {
      type: Boolean,
      default: false
    }
  },
  emits: [
    'selection-change',
    'sort-change',
    'size-change',
    'current-change',
    'edit',
    'delete'
  ],
  setup(props, { emit }) {
    // 表格数据
    const tableData = ref([])
    // 当前页码
    const currentPage = ref(1)
    // 每页显示条数
    const pageSize = ref(10)

    // 监听数据变化
    watch(
      () => props.data,
      (newVal) => {
        tableData.value = newVal
      },
      { immediate: true }
    )

    // 多选变化
    const handleSelectionChange = (selection) => {
      emit('selection-change', selection)
    }

    // 排序变化
    const handleSortChange = (sort) => {
      emit('sort-change', sort)
    }

    // 每页条数变化
    const handleSizeChange = (size) => {
      pageSize.value = size
      emit('size-change', size)
    }

    // 页码变化
    const handleCurrentChange = (page) => {
      currentPage.value = page
      emit('current-change', page)
    }

    // 编辑操作
    const handleEdit = (row) => {
      emit('edit', row)
    }

    // 删除操作
    const handleDelete = (row) => {
      emit('delete', row)
    }

    return {
      tableData,
      currentPage,
      pageSize,
      handleSelectionChange,
      handleSortChange,
      handleSizeChange,
      handleCurrentChange,
      handleEdit,
      handleDelete
    }
  }
})
</script>

<style scoped>
.base-table {
  width: 100%;
}

.pagination-container {
  margin-top: 15px;
  display: flex;
  justify-content: flex-end;
}
</style> 