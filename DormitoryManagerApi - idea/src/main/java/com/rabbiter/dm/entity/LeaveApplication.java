package com.rabbiter.dm.entity;// src/main/java/com/example/model/LeaveApplication.java


import lombok.Data;
import java.util.Date;

@Data
public class LeaveApplication {
    private Long applyId;
    private String studentNo;
    private String studentName;
    private String gender;
    private String department;
    private String className;
    private String phone;
    private String parentName;
    private String parentPhone;
    private String address;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String emergencyContact;
    private String emergencyPhone;
    private String parentOpinion;
    private String parentAgree;
    private String counselorStatus;
    private String collegeStatus;
    private String studentSignature;
    private String parentSignature;
    private String status;
    private Date createTime;
    private Date updateTime;

    // 文件路径字段
    private String idCardPath;
    private String contractPath;
    private String otherFilePath;
}