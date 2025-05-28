import axios from 'axios'

// 创建axios实例
const api = axios.create({
  baseURL: 'http://localhost:8081/api',
  timeout: 5000
})

// 学生相关接口
export const getStudents = (params) => api.get('/students', { params })
export const addStudent = (student) => api.post('/students', student)
export const updateStudent = (sno, student) => api.put(`/students/${sno}`, student)
export const deleteStudent = (sno) => api.delete(`/students/${sno}`)

// 课程相关接口
export const getCourses = (params) => api.get('/courses', { params })
export const addCourse = (course) => api.post('/courses', course)
export const updateCourse = (cno, course) => api.put(`/courses/${cno}`, course)
export const deleteCourse = (cno) => api.delete(`/courses/${cno}`)

// 选课相关接口
export const getSelections = (params) => api.get('/sc', { params })
export const addSelection = (selection) => api.post('/sc', selection)
export const updateGrade = (sno, cno, grade) => api.put(`/sc/${sno}/${cno}`, { grade })
export const deleteSelection = (sno, cno) => api.delete(`/sc/${sno}/${cno}`) 