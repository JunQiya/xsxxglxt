package com.rabbiter.dm.dao;// src/main/java/com/example/mapper/LeaveApplicationMapper.java


import com.rabbiter.dm.entity.LeaveApplication;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface LeaveApplicationMapper {
    // 基础操作
    int insertApplication(LeaveApplication record);
    int updateApplication(LeaveApplication record);
    int deleteByPrimaryKey(Long applyId);

    // 状态相关
    int updateApplicationStatus(@Param("applyId") Long applyId, @Param("status") String status);

    // 查询操作
    LeaveApplication selectByStudentNo(String studentNo);
    List<LeaveApplication> selectPendingApplications();
    List<LeaveApplication> selectByStatus(String status);
    LeaveApplication selectByPrimaryKey(Long applyId);
    List<LeaveApplication> selectCounselorApplications();
    int updateCounselorStatus(@Param("applyId") Long applyId, @Param("status") String status);
    // 文件操作
    int deleteAttachments(@Param("applyId") Long applyId);

    // 新增查询方法
    List<LeaveApplication> selectByClassName(@Param("className") String className);

    List<LeaveApplication> selectByStudentName(@Param("studentName") String studentName);

    List<LeaveApplication> selectByStudentNoAndStatus(@Param("studentNo") String studentNo);

    List<LeaveApplication> selectApprovedApplications();

    int selectCount();
}