import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8080/api', // API的base_url
  timeout: 5000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么
    return config
  },
  error => {
    // 对请求错误做些什么
    console.log(error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    // 如果返回的状态码不是200，说明接口有问题，把错误信息显示出来
    if (res.code !== 200) {
      // 处理业务错误码
      switch (res.code) {
        case 400:
          ElMessage({
            message: '请求参数错误',
            type: 'error',
            duration: 5 * 1000
          })
          break
        case 404:
          ElMessage({
            message: '请求的资源不存在',
            type: 'error',
            duration: 5 * 1000
          })
          break
        case 500:
          ElMessage({
            message: '服务器内部错误',
            type: 'error',
            duration: 5 * 1000
          })
          break
        case 1001:
          ElMessage({
            message: '学号已存在',
            type: 'warning',
            duration: 5 * 1000
          })
          break
        case 1002:
          ElMessage({
            message: '课程号已存在',
            type: 'warning',
            duration: 5 * 1000
          })
          break
        case 1003:
          ElMessage({
            message: '选课记录已存在',
            type: 'warning',
            duration: 5 * 1000
          })
          break
        case 1004:
          ElMessage({
            message: '选课记录不存在',
            type: 'warning',
            duration: 5 * 1000
          })
          break
        default:
          ElMessage({
            message: res.message || '未知错误',
            type: 'error',
            duration: 5 * 1000
          })
      }
      return Promise.reject(new Error(res.message || '错误'))
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error)
    ElMessage({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service 