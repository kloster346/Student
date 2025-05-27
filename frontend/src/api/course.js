import request from '@/utils/request'

// 获取课程列表
export function getCourseList(params) {
  return request({
    url: '/courses',
    method: 'get',
    params: {
      page: params.page || 1,
      size: params.size || 10,
      keyword: params.keyword
    }
  })
}

// 获取单个课程信息
export function getCourse(cno) {
  return request({
    url: `/courses/${cno}`,
    method: 'get'
  })
}

// 创建课程
export function createCourse(data) {
  return request({
    url: '/courses',
    method: 'post',
    data: {
      cno: data.cno,
      cname: data.cname,
      credit: data.credit,
      cdept: data.cdept
    }
  })
}

// 更新课程信息
export function updateCourse(cno, data) {
  return request({
    url: `/courses/${cno}`,
    method: 'put',
    data: {
      cname: data.cname,
      credit: data.credit,
      cdept: data.cdept
    }
  })
}

// 删除课程
export function deleteCourse(cno) {
  return request({
    url: `/courses/${cno}`,
    method: 'delete'
  })
}

// 批量删除课程
export function batchDeleteCourses(cnos) {
  return request({
    url: '/courses/batch',
    method: 'delete',
    data: { cnos }
  })
} 