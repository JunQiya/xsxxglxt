package com.rabbiter.dm.dto;

public class GradeStatsDTO {
    private int ACount;
    private int BCount;
    private int CCount;
    private int DCount;

    @Override
    public String toString() {
        return "GradeStatsDTO{" +
                "ACount=" + ACount +
                ", BCount=" + BCount +
                ", CCount=" + CCount +
                ", DCount=" + DCount +
                '}';
    }

    public int getACount() {
        return ACount;
    }

    public void setACount(int ACount) {
        this.ACount = ACount;
    }

    public int getBCount() {
        return BCount;
    }

    public void setBCount(int BCount) {
        this.BCount = BCount;
    }

    public int getCCount() {
        return CCount;
    }

    public void setCCount(int CCount) {
        this.CCount = CCount;
    }

    public int getDCount() {
        return DCount;
    }

    public void setDCount(int DCount) {
        this.DCount = DCount;
    }
}
