import request from '@/utils/request'

// 获取学生列表
export function getStudentList(params) {
  return request({
    url: '/students',
    method: 'get',
    params
  })
}

// 添加学生
export function addStudent(data) {
  return request({
    url: '/students',
    method: 'post',
    data
  })
}

// 更新学生
export function updateStudent(sno, data) {
  return request({
    url: `/students/${sno}`,
    method: 'put',
    data
  })
}

// 删除学生
export function deleteStudent(sno) {
  return request({
    url: `/students/${sno}`,
    method: 'delete'
  })
} 