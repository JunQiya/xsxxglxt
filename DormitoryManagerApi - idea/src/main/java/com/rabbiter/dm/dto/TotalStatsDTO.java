package com.rabbiter.dm.dto;

public class TotalStatsDTO {

    private int repairCount;
    private int leaveCount;
    private int absenceCount;
    private int studentNum;

    @Override
    public String toString() {
        return "TotalStatsDTO{" +
                "repairCount=" + repairCount +
                ", leaveCount=" + leaveCount +
                ", absenceCount=" + absenceCount +
                ", studentNum=" + studentNum +
                '}';
    }

    public int getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(int repairCount) {
        this.repairCount = repairCount;
    }

    public int getLeaveCount() {
        return leaveCount;
    }

    public void setLeaveCount(int leaveCount) {
        this.leaveCount = leaveCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public void setAbsenceCount(int absenceCount) {
        this.absenceCount = absenceCount;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }
}
