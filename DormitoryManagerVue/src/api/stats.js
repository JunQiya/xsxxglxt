import request from '@/utils/request'

export function totalStats() {
    return request({
        url: '/stats/totalStats',
        method: 'get'
    })
}