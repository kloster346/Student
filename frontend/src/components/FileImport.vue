/* eslint-disable no-undef */
<template>
  <div class="file-import">
    <!-- 文件上传区域 -->
    <div 
      class="upload-area" 
      :class="{ 'is-dragover': isDragover }"
      @drop.prevent="handleDrop" 
      @dragover.prevent="handleDragover"
      @dragleave.prevent="handleDragleave"
      @dragenter.prevent="handleDragenter"
    >
      <el-upload
        ref="uploadRef"
        :action="props.uploadUrl"
        :accept="'.csv'"
        :show-file-list="false"
        :before-upload="beforeUpload"
        :on-success="handleSuccess"
        :on-error="handleError"
        :on-progress="handleProgress"
        :headers="headers"
        :auto-upload="true"
        :http-request="customUpload"
      >
        <div class="upload-content">
          <el-icon class="upload-icon"><upload-filled /></el-icon>
          <div class="upload-text">
            <span>将文件拖到此处，或</span>
            <el-button type="primary" link>点击上传</el-button>
          </div>
          <div class="upload-tip">支持 .csv 格式文件</div>
          <div class="upload-tip">文件大小不超过5MB</div>
        </div>
      </el-upload>
    </div>

    <!-- 上传进度 -->
    <div v-if="uploading" class="progress-area">
      <el-progress :percentage="uploadProgress" :format="progressFormat" />
    </div>

    <!-- 上传结果 -->
    <div v-if="uploadResult" class="result-area">
      <el-alert
        :title="resultTitle"
        :type="resultType"
        :description="resultDescription"
        :closable="false"
        show-icon
      />
      
      <!-- 失败记录列表 -->
      <div v-if="failures.length > 0" class="failures-list">
        <el-collapse>
          <el-collapse-item title="失败记录详情">
            <el-table :data="failures" border style="width: 100%">
              <el-table-column prop="line" label="行号" width="80" />
              <el-table-column prop="content" label="内容" />
              <el-table-column prop="reason" label="失败原因" />
            </el-table>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>

    <!-- CSV格式说明 -->
    <div class="format-guide">
      <el-collapse>
        <el-collapse-item title="文件格式说明">
          <div class="format-content">
            <h4>CSV文件要求：</h4>
            <p>1. 文件必须是UTF-8编码的CSV格式</p>
            <p>2. 第一行必须是标题行</p>
            <p>3. 数据格式要求：</p>
            <pre v-if="props.formatExample" class="format-example">{{ props.formatExample }}</pre>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, defineProps } from 'vue'
import { UploadFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const props = defineProps({
  uploadUrl: {
    type: String,
    required: true
  },
  formatExample: {
    type: String,
    default: ''
  }
})

// 请求头
const headers = {
  'Accept': 'application/json',
  'X-Requested-With': 'XMLHttpRequest'
}

// 上传相关的响应式变量
const uploadRef = ref(null)
const uploading = ref(false)
const uploadProgress = ref(0)
const uploadResult = ref(null)
const failures = ref([])
const isDragover = ref(false)

// 计算上传结果的显示信息
const resultTitle = computed(() => {
  if (!uploadResult.value) return ''
  const { totalCount, successCount, failureCount } = uploadResult.value
  return `导入完成：成功 ${successCount} 条，失败 ${failureCount} 条，共 ${totalCount} 条`
})

const resultType = computed(() => {
  if (!uploadResult.value) return 'info'
  return uploadResult.value.failureCount > 0 ? 'warning' : 'success'
})

const resultDescription = computed(() => {
  if (!uploadResult.value) return ''
  const { failureCount } = uploadResult.value
  return failureCount > 0 ? `存在 ${failureCount} 条数据导入失败，请查看详情` : '所有数据导入成功'
})

// 上传前的验证
const beforeUpload = (file) => {
  const isCSV = file.type === 'text/csv' || file.name.endsWith('.csv')
  if (!isCSV) {
    ElMessage.error('只能上传CSV文件！')
    return false
  }
  
  const maxSize = 5 * 1024 * 1024 // 5MB
  if (file.size > maxSize) {
    ElMessage.error('文件大小不能超过5MB！')
    return false
  }

  uploading.value = true
  uploadProgress.value = 0
  uploadResult.value = null
  failures.value = []
  return true
}

// 处理上传进度
const handleProgress = (event) => {
  uploadProgress.value = Math.round(event.percent)
}

// 格式化进度显示
const progressFormat = (percentage) => {
  return uploading.value ? `${percentage}%` : ''
}

// 处理上传成功
const handleSuccess = (response) => {
  uploading.value = false
  if (response.code === 200) {
    uploadResult.value = response.data
    failures.value = response.data.failures || []
    const { successCount, totalCount } = response.data
    if (successCount === totalCount) {
      ElMessage.success(`成功导入 ${successCount} 条数据`)
    } else {
      ElMessage.warning(`成功导入 ${successCount} 条数据，${totalCount - successCount} 条数据导入失败`)
    }
  } else {
    ElMessage.error(response.message || '上传失败')
  }
}

// 处理上传错误
const handleError = (error) => {
  uploading.value = false
  console.error('上传错误:', error)
  if (error.response) {
    const { status, data } = error.response
    if (status === 413) {
      ElMessage.error('文件太大，无法上传')
    } else if (data && data.message) {
      ElMessage.error(data.message)
    } else {
      ElMessage.error('文件上传失败，请检查文件格式是否正确')
    }
  } else if (error.message) {
    ElMessage.error(error.message)
  } else {
    ElMessage.error('上传失败，请重试')
  }
}

// 自定义上传方法
const customUpload = async ({ file }) => {
  try {
    const formData = new FormData()
    formData.append('file', file)
    
    uploading.value = true
    uploadProgress.value = 0
    
    const response = await axios.post(props.uploadUrl, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        ...headers
      },
      onUploadProgress: (progressEvent) => {
        const percent = Math.round((progressEvent.loaded * 100) / progressEvent.total)
        uploadProgress.value = percent
      }
    })
    
    handleSuccess(response.data)
  } catch (error) {
    handleError(error)
  }
}

// 处理拖拽相关事件
const handleDragenter = (e) => {
  e.preventDefault()
  isDragover.value = true
}

const handleDragover = (e) => {
  e.preventDefault()
  isDragover.value = true
}

const handleDragleave = (e) => {
  e.preventDefault()
  isDragover.value = false
}

const handleDrop = async (e) => {
  e.preventDefault()
  isDragover.value = false
  const files = e.dataTransfer.files
  
  if (files.length > 0) {
    const file = files[0]
    
    // 验证文件
    if (!file.name.toLowerCase().endsWith('.csv')) {
      ElMessage.error('只能上传CSV文件！')
      return
    }
    
    if (file.size > 5 * 1024 * 1024) {
      ElMessage.error('文件大小不能超过5MB！')
      return
    }
    
    // 使用自定义上传方法
    await customUpload({ file })
  }
}
</script>

<style scoped>
.file-import {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.upload-area {
  border: 2px dashed #dcdfe6;
  border-radius: 6px;
  padding: 40px 20px;
  text-align: center;
  transition: all 0.3s;
  background-color: #fafafa;
  cursor: pointer;
}

.upload-area:hover {
  border-color: #409eff;
  background-color: #f5f7fa;
}

.upload-area.is-dragover {
  border-color: #409eff;
  background-color: #ecf5ff;
  box-shadow: 0 0 10px rgba(64, 158, 255, 0.2);
}

.upload-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.upload-icon {
  font-size: 48px;
  color: #409eff;
  transition: transform 0.3s;
}

.is-dragover .upload-icon {
  transform: scale(1.2);
}

.upload-text {
  color: #606266;
  font-size: 16px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.upload-tip {
  font-size: 12px;
  color: #909399;
  line-height: 1.5;
}

.progress-area {
  margin-top: 20px;
}

.result-area {
  margin-top: 20px;
}

.failures-list {
  margin-top: 20px;
}

.format-guide {
  margin-top: 20px;
}

.format-content {
  font-size: 14px;
  color: #606266;
}

.format-example {
  background: #f5f7fa;
  padding: 10px;
  border-radius: 4px;
  font-family: monospace;
  white-space: pre-wrap;
}
</style> 