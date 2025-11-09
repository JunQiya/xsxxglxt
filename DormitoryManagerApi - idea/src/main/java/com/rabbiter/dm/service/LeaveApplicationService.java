package com.rabbiter.dm.service;

import com.rabbiter.dm.annotation.RequirePermission;
import com.rabbiter.dm.dao.LeaveApplicationMapper;
import com.rabbiter.dm.entity.LeaveApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class LeaveApplicationService {

    @Autowired
    private LeaveApplicationMapper leaveApplicationMapper;

    // 新增申请
    @Transactional
    public int createApplication(LeaveApplication application) {
        application.setCreateTime(new Date());
        application.setUpdateTime(new Date());
        application.setStatus("draft");
        return leaveApplicationMapper.insertApplication(application);
    }

    // 更新申请信息
    @Transactional
    public int updateApplication(LeaveApplication application) {
        application.setUpdateTime(new Date());
        return leaveApplicationMapper.updateApplication(application);
    }

    // 删除申请
    @Transactional
    public int deleteApplication(Long applyId) {
        return leaveApplicationMapper.deleteByPrimaryKey(applyId);
    }

    // 提交申请
    @Transactional
    public int submitApplication(LeaveApplication application) {
        validateApplication(application);
        application.setStatus("submitted");
        application.setUpdateTime(new Date());
        return leaveApplicationMapper.insertApplication(application);
    }

    // 更新审批状态
    @Transactional
    public int updateApplicationStatus(Long applyId, String newStatus) {
        LeaveApplication app = leaveApplicationMapper.selectByPrimaryKey(applyId);
        if (app == null) {
            throw new IllegalArgumentException("申请不存在");
        }
        app.setStatus(newStatus);
        app.setUpdateTime(new Date());
        return leaveApplicationMapper.updateApplication(app);
    }

    // 根据学号查询申请
    public LeaveApplication getByStudentNo(String studentNo) {
        return leaveApplicationMapper.selectByStudentNo(studentNo);
    }

    // 获取待审批列表
    public List<LeaveApplication> getPendingApplications() {
        return leaveApplicationMapper.selectPendingApplications();
    }

    // 根据状态查询申请
    public List<LeaveApplication> getApplicationsByStatus(String status) {
        return leaveApplicationMapper.selectByStatus(status);
    }

    public List<LeaveApplication> getCounselorApplications() {
        return leaveApplicationMapper.selectCounselorApplications();
    }

    // 删除附件记录
    @Transactional
    public int removeAttachments(Long applyId) {
        return leaveApplicationMapper.deleteAttachments(applyId);
    }

    // 私有方法：基础校验
    private void validateApplication(LeaveApplication app) {
        if (app.getStartDate() == null || app.getEndDate() == null) {
            throw new IllegalArgumentException("必须填写有效时间范围");
        }
        if (app.getEndDate().before(app.getStartDate())) {
            throw new IllegalArgumentException("结束日期不能早于开始日期");
        }
        if (app.getStatus() == null) {
            app.setStatus("draft");
        }
    }
    public LeaveApplication getByApplyId(Long applyId) {
        return leaveApplicationMapper.selectByPrimaryKey(applyId);
    }

    @Transactional
    public int approveByCounselor(Long applyId) {
        LeaveApplication app = leaveApplicationMapper.selectByPrimaryKey(applyId);
        if (app == null) {
            throw new IllegalArgumentException("申请不存在");
        }
        return leaveApplicationMapper.updateCounselorStatus(applyId, "已通过");
    }

    // 新增查询服务方法
    public List<LeaveApplication> getByClassName(String className) {
        return leaveApplicationMapper.selectByClassName(className);
    }

    public List<LeaveApplication> getByStudentName(String studentName) {
        return leaveApplicationMapper.selectByStudentName(studentName);
    }

    public List<LeaveApplication> getByStudentNoAndStatus(String studentNo) {
        return leaveApplicationMapper.selectByStudentNoAndStatus(studentNo);
    }

    public List<LeaveApplication> getApprovedApplications() {
        return leaveApplicationMapper.selectApprovedApplications();
    }
}