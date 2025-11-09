package com.rabbiter.dm.entity;

import java.time.LocalDateTime;

public class ScoreImage {
    private long id;
    private long score_id;
    private String file_name;
    private String original_name;
    private String md5;

    private LocalDateTime uploadTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getScore_id() {
        return score_id;
    }

    public void setScore_id(long score_id) {
        this.score_id = score_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "ScoreImage{" +
                "id=" + id +
                ", score_id=" + score_id +
                ", file_name='" + file_name + '\'' +
                ", original_name='" + original_name + '\'' +
                ", md5='" + md5 + '\'' +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
