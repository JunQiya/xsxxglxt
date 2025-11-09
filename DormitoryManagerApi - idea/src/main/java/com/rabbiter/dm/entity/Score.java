package com.rabbiter.dm.entity;

public class Score {
    private long id;

    private long room_id;

    private int global_score;

    private int building_score;

    private int bed_score;

    private int indoor_score;

    private double ce_score;

    private String img_name;

    private Room room;

    private String rated_at;

    private Building building;


    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", room_id=" + room_id +
                ", global_score=" + global_score +
                ", building_score=" + building_score +
                ", bed_score=" + bed_score +
                ", indoor_score=" + indoor_score +
                ", ce_score=" + ce_score +
                ", img_name='" + img_name + '\'' +
                ", room=" + room +
                ", rated_at='" + rated_at + '\'' +
                ", building=" + building +
                ", building_name='" + building_name + '\'' +
                '}';
    }

    public String getRated_at() {
        return rated_at;
    }

    public void setRated_at(String rated_at) {
        this.rated_at = rated_at;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    private String building_name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public int getGlobal_score() {
        return global_score;
    }

    public void setGlobal_score(int global_score) {
        this.global_score = global_score;
    }

    public int getBuilding_score() {
        return building_score;
    }

    public void setBuilding_score(int building_score) {
        this.building_score = building_score;
    }

    public int getBed_score() {
        return bed_score;
    }

    public void setBed_score(int bed_score) {
        this.bed_score = bed_score;
    }

    public int getIndoor_score() {
        return indoor_score;
    }

    public void setIndoor_score(int indoor_score) {
        this.indoor_score = indoor_score;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public double getCe_score() {
        return ce_score;
    }

    public void setCe_score(double   ce_score) {
        this.ce_score = ce_score;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }


}