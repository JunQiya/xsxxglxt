package com.rabbiter.dm.dao;

import com.github.pagehelper.Page;
import com.rabbiter.dm.entity.Score;
import com.rabbiter.dm.entity.ScoreTrend;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreMapper {

    void insert(Score score);

    void update(Score score);

    List<Score> listByRoomId(long roomId);

    Page<Score> listRoomsWithScores( @Param("buildingId") Long buildingId,
                                     @Param("number") String number,
                                     @Param("time") String time);

    List<ScoreTrend> scoreTrend();
}
