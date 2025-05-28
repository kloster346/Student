import request from '@/utils/request'

// 学生相关接口
export const getStudents = (params) => request.get('/students', { params })
export const addStudent = (student) => request.post('/students', student)
export const updateStudent = (sno, student) => request.put(`/students/${sno}`, student)
export const deleteStudent = (sno) => request.delete(`/students/${sno}`)
export const deleteStudentBatch = (ids) => request.delete('/students/batch', { data: { ids } })
export const importStudents = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/students/import', formData)
}

// 课程相关接口
export const getCourses = (params) => request.get('/courses', { params })
export const addCourse = (course) => request.post('/courses', course)
export const updateCourse = (cno, course) => request.put(`/courses/${cno}`, course)
export const deleteCourse = (cno) => request.delete(`/courses/${cno}`)
export const deleteCourseBatch = (ids) => request.delete('/courses/batch', { data: { ids } })
export const importCourses = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/courses/import', formData)
}

// 选课相关接口
export const getSelections = (params) => request.get('/sc', { params })
export const addSelection = (selection) => request.post('/sc', selection)
export const updateGrade = (sno, cno, grade) => request.put(`/sc/${sno}/${cno}`, { grade })
export const deleteSelection = (sno, cno) => request.delete(`/sc/${sno}/${cno}`)
export const deleteSelectionBatch = ({ snos, cnos }) => request.delete('/sc/batch', { data: { snos, cnos } })
export const importSelections = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/sc/import', formData)
}

// 选课查询接口
export const queryStudentCourses = (params) => request.get('/students/courses', { params }) 