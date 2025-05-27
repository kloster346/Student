import request from '@/utils/request'

// 获取选课列表
export function getScList(params) {
  return request({
    url: '/sc',
    method: 'get',
    params: {
      page: params.page || 1,
      size: params.size || 10,
      keyword: params.keyword
    }
  })
}

// 获取单个选课记录
export function getSc(sno, cno) {
  return request({
    url: `/sc/${sno}/${cno}`,
    method: 'get'
  })
}

// 创建选课记录
export function createSc(data) {
  return request({
    url: '/sc',
    method: 'post',
    data: {
      sno: data.sno,
      cno: data.cno
    }
  })
}

// 更新成绩
export function updateGrade(sno, cno, data) {
  return request({
    url: `/sc/${sno}/${cno}`,
    method: 'put',
    data: {
      grade: data.grade
    }
  })
}

// 退课
export function deleteSc(sno, cno) {
  return request({
    url: `/sc/${sno}/${cno}`,
    method: 'delete'
  })
}

// 批量退课
export function batchDeleteSc(records) {
  return request({
    url: '/sc/batch',
    method: 'delete',
    data: { records }
  })
} 