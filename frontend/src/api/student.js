import request from '@/utils/request'

// 获取学生列表
export function getStudentList(params) {
  return request({
    url: '/students',
    method: 'get',
    params: {
      page: params.page || 1,
      size: params.size || 10,
      keyword: params.keyword
    }
  })
}

// 获取单个学生信息
export function getStudent(sno) {
  return request({
    url: `/students/${sno}`,
    method: 'get'
  })
}

// 创建学生
export function createStudent(data) {
  return request({
    url: '/students',
    method: 'post',
    data: {
      sno: data.studentId,
      sname: data.name,
      ssex: data.gender,
      sage: data.age,
      sdept: data.className
    }
  })
}

// 更新学生信息
export function updateStudent(sno, data) {
  return request({
    url: `/students/${sno}`,
    method: 'put',
    data: {
      sname: data.name,
      ssex: data.gender,
      sage: data.age,
      sdept: data.className
    }
  })
}

// 删除学生
export function deleteStudent(sno) {
  return request({
    url: `/students/${sno}`,
    method: 'delete'
  })
}

// 批量删除学生
export function batchDeleteStudents(snos) {
  return request({
    url: '/students/batch',
    method: 'delete',
    data: { snos }
  })
} 