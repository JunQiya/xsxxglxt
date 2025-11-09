import request from '@/utils/request'

// 组合条件查询
export function searchRecords(condition) {
  return request({
    url: '/absence/list',
    method: 'post',
    data: condition
  })
}

// 新增记录
export function addAbsenceRecord(record) {
  return request({
    url: '/absence/save',
    method: 'post',
    data: record
  })
}

// 更新记录
export function updateAbsenceRecord(record) {
  return request({
    url: '/absence/update',
    method: 'put',
    data: record
  })
}

// 删除记录
export function deleteRecord(id) {
  return request({
    url: `/absence/delete/${id}`,
    method: 'delete'
  })
}

// 独立查询接口
export function searchByName(name) {
  return request({
    url: '/absence/search/name',
    method: 'get',
    params: { name }
  })
}

export function searchByAbsentDate(date) {
  return request({
    url: '/absence/search/date',
    method: 'get',
    params: { date }
  })
}

export function searchByClass(classId) {
  return request({
    url: '/absence/search/class',
    method: 'get',
    params: { classId }
  })
}

export function searchByReturnDateRange(start, end) {
  return request({
    url: '/absence/search/return',
    method: 'get',
    params: { start, end }
  })
}

// 新增全量查询
export function getAllRecords() {
  return request({
    url: '/absence/all',
    method: 'get'
  })
}
