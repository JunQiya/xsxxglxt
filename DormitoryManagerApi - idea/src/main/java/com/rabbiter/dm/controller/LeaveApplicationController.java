package com.rabbiter.dm.controller;

import com.rabbiter.dm.entity.LeaveApplication;
import com.rabbiter.dm.service.LeaveApplicationService;
import com.rabbiter.dm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@RestController
@RequestMapping("/leave-applications")
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    // 创建新申请
    @PostMapping
    public Result<String> createApplication(@RequestBody LeaveApplication application) {
        leaveApplicationService.createApplication(application);
        return Result.ok("申请创建成功");
    }

    // 提交申请
    @PutMapping("/{id}/submit")
    public Result<String> submitApplication(@PathVariable Long id, @RequestBody LeaveApplication application) {
        application.setApplyId(id);
        leaveApplicationService.submitApplication(application);
        return Result.ok("申请提交成功");
    }

    // 更新申请信息
    @PutMapping("update/{id}")
    public Result<String> updateApplication(@PathVariable Long id, @RequestBody LeaveApplication application) {
        application.setApplyId(id);
        leaveApplicationService.updateApplication(application);
        return Result.ok("申请信息更新成功");
    }

    // 提交审批状态变更
    @PutMapping("/{id}/approve")
    public Result<String> approveApplication(@PathVariable Long id) {
        leaveApplicationService.updateApplicationStatus(id, "approved");
        return Result.ok("审批通过");
    }

    // 根据主键查询
    @GetMapping("byApplyID/{id}")
    public Result<LeaveApplication> getApplicationById(@PathVariable Long id) {
        LeaveApplication app = leaveApplicationService.getByApplyId(id);
        return Result.<LeaveApplication>ok().add(app);
    }

    // 根据学号查询
    @GetMapping("/student")
    public Result<LeaveApplication> getByStudentNo(@RequestParam String studentNo) {
        LeaveApplication app = leaveApplicationService.getByStudentNo(studentNo);
        return Result.<LeaveApplication>ok().add(app);
    }

    // 获取所有待审批申请
    @GetMapping("/pending")
    public Result<List<LeaveApplication>> getPendingApplications() {
        List<LeaveApplication> apps = leaveApplicationService.getPendingApplications();
        return Result.<List<LeaveApplication>>ok().add(apps);
    }

    // 根据状态查询
    @GetMapping("/status")
    public Result<List<LeaveApplication>> getByStatus(@RequestParam String status) {
        List<LeaveApplication> apps = leaveApplicationService.getApplicationsByStatus(status);
        return Result.<List<LeaveApplication>>ok().add(apps);
    }

    // 删除申请
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        leaveApplicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/counselor/pending")
    public ResponseEntity<Map<String, Object>> getCounselorPendingApplications() {
        try {
            List<LeaveApplication> apps = leaveApplicationService.getCounselorApplications();
            Map<String, Object> response = new HashMap<>();
            // 修改字段名匹配前端拦截器
            response.put("statusCode", 200);  // 原字段名 code → statusCode
            response.put("data", apps != null ? apps : Collections.emptyList());
            response.put("msg", "Success");   // 原字段名 message → msg
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("statusCode", 500);  // 同步修改错误状态字段
            errorResponse.put("msg", "服务器内部错误");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}/counselor-approve")
    public Result<String> approveByCounselor(@PathVariable Long id) {
        leaveApplicationService.approveByCounselor(id);
        return Result.ok("辅导员审批通过");
    }

    // 新增查询接口
    @GetMapping("/by-class")
    public ResponseEntity<Map<String, Object>> getByClassName(@RequestParam String className) {
        return handleQueryResult(() -> leaveApplicationService.getByClassName(className));
    }

    @GetMapping("/by-name")
    public ResponseEntity<Map<String, Object>> getByStudentName(@RequestParam String studentName) {
        return handleQueryResult(() -> leaveApplicationService.getByStudentName(studentName));
    }

    @GetMapping("/by-studentno")
    public ResponseEntity<Map<String, Object>> getByStudentNoWhereApproved(@RequestParam String studentNo) {
        return handleQueryResult(() -> leaveApplicationService.getByStudentNoAndStatus(studentNo));
    }

    @GetMapping("/approved")
    public ResponseEntity<Map<String, Object>> getApprovedApplications() {
        return handleQueryResult(leaveApplicationService::getApprovedApplications);
    }

    // 公共响应处理方法
    private ResponseEntity<Map<String, Object>> handleQueryResult(Supplier<List<LeaveApplication>> supplier) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<LeaveApplication> apps = supplier.get();
            response.put("statusCode", 200);
            response.put("data", apps != null ? apps : Collections.emptyList());
            response.put("msg", "查询成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("statusCode", 500);
            response.put("msg", "查询失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}