package com.rabbiter.dm.entity;

public class ScoreTrend {
    private double globalAvg;
    private double buildingAvg;
    private double bedAvg;
    private double indoorAvg;
    private String month;

    @Override
    public String toString() {
        return "ScoreTrend{" +
                "globalAvg=" + globalAvg +
                ", buildingAvg=" + buildingAvg +
                ", bedAvg=" + bedAvg +
                ", indoorAvg=" + indoorAvg +
                ", month='" + month + '\'' +
                '}';
    }

    public double getGlobalAvg() {
        return globalAvg;
    }

    public void setGlobalAvg(double globalAvg) {
        this.globalAvg = globalAvg;
    }

    public double getBuildingAvg() {
        return buildingAvg;
    }

    public void setBuildingAvg(double buildingAvg) {
        this.buildingAvg = buildingAvg;
    }

    public double getBedAvg() {
        return bedAvg;
    }

    public void setBedAvg(double bedAvg) {
        this.bedAvg = bedAvg;
    }

    public double getIndoorAvg() {
        return indoorAvg;
    }

    public void setIndoorAvg(double indoorAvg) {
        this.indoorAvg = indoorAvg;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}


