import request from '@/utils/request'

// 获取课程列表
export function getCourseList(params) {
  return request({
    url: '/courses',
    method: 'get',
    params
  })
}

// 添加课程
export function addCourse(data) {
  return request({
    url: '/courses',
    method: 'post',
    data
  })
}

// 更新课程
export function updateCourse(cno, data) {
  return request({
    url: `/courses/${cno}`,
    method: 'put',
    data
  })
}

// 删除课程
export function deleteCourse(cno) {
  return request({
    url: `/courses/${cno}`,
    method: 'delete'
  })
} 