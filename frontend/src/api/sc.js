import request from '@/utils/request'

// 获取选课列表
export function getSCList(params) {
  return request({
    url: '/sc',
    method: 'get',
    params
  })
}

// 添加选课
export function addSC(data) {
  return request({
    url: '/sc',
    method: 'post',
    data
  })
}

// 更新成绩
export function updateGrade(sno, cno, data) {
  return request({
    url: `/sc/${sno}/${cno}`,
    method: 'put',
    data
  })
}

// 退课
export function deleteSC(sno, cno) {
  return request({
    url: `/sc/${sno}/${cno}`,
    method: 'delete'
  })
} 