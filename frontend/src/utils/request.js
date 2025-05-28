import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
const request = axios.create({
  baseURL: 'http://localhost:8081/api',
  timeout: 5000
})

// 响应拦截器
request.interceptors.response.use(
  response => {
    const { code, message, data } = response.data

    // 如果code不是200，说明请求出错
    if (code !== 200) {
      ElMessage.error(message || '请求失败')
      return Promise.reject(new Error(message || '请求失败'))
    }

    return data
  },
  error => {
    ElMessage.error(error.response?.data?.message || error.message || '请求失败')
    return Promise.reject(error)
  }
)

export default request 