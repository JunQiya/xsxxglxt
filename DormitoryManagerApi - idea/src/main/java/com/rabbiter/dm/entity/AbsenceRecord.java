package com.rabbiter.dm.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class AbsenceRecord {
    private Integer id;
    private String school;
    private String department;
    private String registrar;

    @JsonProperty("registrar_phone")
    private String registrarPhone;
    @JsonProperty("report_date")
    private LocalDate reportDate;
    @JsonProperty("absent_date")
    private LocalDate absentDate;
    private String name;
    private Integer gender;
    private Integer age;
    @JsonProperty("class_id")
    private Long classId;  // 对应BIGINT类型
    private String phone;
    private String symptoms;
    @JsonProperty("visit_date")
    private LocalDate visitDate;
    private String hospital;
    private String diagnosis;
    @JsonProperty("return_date")
    private LocalDate returnDate;
    @JsonProperty("is_cured")
    private Integer isCured;
    @JsonProperty("is_infectious")
    private Integer isInfectious;

    // 省略getter/setter


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRegistrar() {
        return registrar;
    }

    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }

    public String getRegistrarPhone() {
        return registrarPhone;
    }

    public void setRegistrarPhone(String registrarPhone) {
        this.registrarPhone = registrarPhone;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public LocalDate getAbsentDate() {
        return absentDate;
    }

    public void setAbsentDate(LocalDate absentDate) {
        this.absentDate = absentDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getIsCured() {
        return isCured;
    }

    public void setIsCured(Integer isCured) {
        this.isCured = isCured;
    }

    public Integer getIsInfectious() {
        return isInfectious;
    }

    public void setIsInfectious(Integer isInfectious) {
        this.isInfectious = isInfectious;
    }
}