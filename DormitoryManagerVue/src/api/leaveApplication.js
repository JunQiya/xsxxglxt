// src/api/leaveApplication.js
import request from '@/utils/request'

// 创建新申请
export function createApplication(data) {
    return request({
        url: '/leave-applications',
        method: 'post',
        data
    })
}

// 提交申请（修正参数名）
export function submitApplication(id, data) { // 将参数名改为id
    return request({
        url: `/leave-applications/${id}/submit`, // 使用正确的参数名
        method: 'put',
        data
    })
}

// 更新申请信息（修正参数名）
export function updateApplication(id, data) { // 将参数名改为id
    return request({
        url: `/leave-applications/${id}`, // 使用正确的参数名
        method: 'put',
        data
    })
}

// 提交审批状态变更
export function approveApplication(id) { // 修正参数名
    return request({
        url: `/leave-applications/${id}/approve`,
        method: 'put'
    })
}

// 根据主键查询
export function getApplicationById(id) { // 修正参数名
    return request({
        url: `/leave-applications/byApplyID/${id}`,
        method: 'get'
    })
}

// 根据学号查询
export function getByStudentNo(studentNo) { // 修正参数名
    return request({
        url: '/leave-applications/student',
        method: 'get',
        params: { studentNo }
    })
}

// 获取所有待审批申请
export function getPendingApplications() {
    return request({
        url: '/leave-applications/pending',
        method: 'get'
    })
}

export function getCounselorPendingApplications() {
    return request({
        url: '/leave-applications/counselor/pending',
        method: 'get'
    })
}

// 根据状态查询
export function getByStatus(status) { // 修正参数名
    return request({
        url: '/leave-applications/status',
        method: 'get',
        params: { status }
    })
}

// 删除申请
export function deleteApplication(id) { // 修正参数名
    return request({
        url: `/leave-applications/delete/${id}`,
        method: 'delete'
    })
}

// 文件上传接口（需后端实现）
export function uploadFile(data) {
    return request({
        url: 'uploads',
        method: 'post',
        data,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

export function approveByCounselor(id) {
    return request({
        url: `/leave-applications/${id}/counselor-approve`,  // 添加路径参数
        method: 'put'  // 修改请求方法为PUT
    })
}

// 新增查询方法
export function getByClassName(className) {
    return request({
        url: '/leave-applications/by-class',
        method: 'get',
        params: { className }
    })
}

export function getByStudentName(studentName) {
    return request({
        url: '/leave-applications/by-name',
        method: 'get',
        params: { studentName }
    })
}

export function getByStudentNoAndStatus(studentNo) {
    return request({
        url: '/leave-applications/by-studentno',
        method: 'get',
        params: { studentNo }
    })
}

export function getApprovedApplications() {
    return request({
        url: '/leave-applications/approved',
        method: 'get'
    })
}