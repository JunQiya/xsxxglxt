package com.rabbiter.dm.dao;

import com.rabbiter.dm.entity.Image;
import com.rabbiter.dm.entity.ScoreImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreImageMapper {
    List<ScoreImage> selectByMd5(String md5);

    void save(ScoreImage scoreImage);
    String selectByScoreId(long score_id);
}
