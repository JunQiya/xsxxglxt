package com.rabbiter.dm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rabbiter.dm.dao.ScoreMapper;
import com.rabbiter.dm.dto.GradeStatsDTO;
import com.rabbiter.dm.entity.Score;
import com.rabbiter.dm.entity.ScoreTrend;
import com.rabbiter.dm.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    public Score save(Score score) {
        scoreMapper.insert(score);
        return score;
    }

    public void update(Score score) {
        scoreMapper.update(score);
    }

    public List<Score> list(long roomId){
        return scoreMapper.listByRoomId(roomId);
    }

    public PageResult<Score> listRoomsWithScores(int page, int size, Long buildingId, String number) {
        PageHelper.startPage(page, size);
        List<Score> scores = scoreMapper.listRoomsWithScores(buildingId, number,null);

        scores.forEach(score -> {
            double sum = score.getGlobal_score()
                    + score.getBuilding_score()
                    + score.getBed_score()
                    + score.getIndoor_score();
            score.setCe_score(sum / 4.0);
        });

        PageInfo<Score> pageInfo = new PageInfo<>(scores);
        return new PageResult<>(
                pageInfo.getList(),
                pageInfo.getTotal(),
                pageInfo.getPages(),
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                200
        );
    }
    public boolean isNUll(long roomId){
        if (scoreMapper.listByRoomId(roomId).isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    public GradeStatsDTO countScoreGrades(Long buildingId, String number) {
        // 禁用分页获取全部数据
        PageHelper.startPage(1, Integer.MAX_VALUE, false);
        List<Score> scores = scoreMapper.listRoomsWithScores(buildingId, number,"1");
        System.out.println("chengji"+scores);

        GradeStatsDTO stats = new GradeStatsDTO();

        scores.forEach(score -> {
            double sum = score.getGlobal_score()
                    + score.getBuilding_score()
                    + score.getBed_score()
                    + score.getIndoor_score();
            double ceScore = sum / 4.0;

            if (ceScore >= 8.7) {
                stats.setACount(stats.getACount() + 1);
            } else if (ceScore >= 7) {
                stats.setBCount(stats.getBCount() + 1);
            } else if (ceScore >= 6) {
                stats.setCCount(stats.getCCount() + 1);
            } else {
                stats.setDCount(stats.getDCount() + 1);
            }
        });

        return stats;
    }

    public List<ScoreTrend> getScoreTrend(){
        return scoreMapper.scoreTrend();
    }
}
