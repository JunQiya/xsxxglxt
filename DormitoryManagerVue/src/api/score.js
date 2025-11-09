import request from '@/utils/request'

export function listByBId(id) {
    return request({
        url: '/score/room?id=' + id,
        method: 'get'
    })
}

export function stats() {
    return request({
        url: '/score/stats',
        method: 'get'
    })
}

export function saveOrUpdate(data) {
    return request({
        url: '/score/saveOrUpdate',
        method: 'post',
        data: data
    })  
}

export function scoreByRid(id) {
    return request({
        url: '/score/transcript?room_id=' + id,
        method: 'get'
    })
}

export function listRoomsWithScores(page, size, buildingId, number) {
    return request({
      url: `/score/listRoomsWithScores?page=${page}&size=${size}` +
           `&buildingId=${buildingId || ''}&number=${number || ''}`,
      method: 'get'
    })
}

export function getScoreTrend() {
    return request({
      url: '/score/scoreTrend',
      method: 'get'
    })}