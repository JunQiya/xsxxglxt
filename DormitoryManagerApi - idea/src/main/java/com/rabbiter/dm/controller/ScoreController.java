package com.rabbiter.dm.controller;

import com.rabbiter.dm.annotation.RequirePermission;
import com.rabbiter.dm.dto.GradeStatsDTO;
import com.rabbiter.dm.entity.Room;
import com.rabbiter.dm.entity.Score;

import com.rabbiter.dm.entity.ScoreTrend;
import com.rabbiter.dm.service.RoomService;
import com.rabbiter.dm.service.ScoreImageService;
import com.rabbiter.dm.service.ScoreService;
import com.rabbiter.dm.vo.PageResult;
import com.rabbiter.dm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private ScoreImageService scoreImageService;

    @GetMapping("/listRoomsWithScores")
    public ResponseEntity<PageResult<Score>> getScores(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long buildingId,
            @RequestParam(required = false) String number) {
        // 强制参数有效性
        page = Math.max(page, 1);
        size = Math.min(Math.max(size, 1), 10);
        return ResponseEntity.ok(scoreService.listRoomsWithScores(page, size,buildingId, number));
    }

    @GetMapping("/room")
    public Result<List<Room>> listByBuilding(@RequestParam("id") Long id){
        List<Room> list = roomService.listByBuildingId(id);
        return Result.<List<Room>>ok().add(list);
    }

    @GetMapping("/transcript")
    public Result<List<Score>> listByRId(@RequestParam("room_id") long room_id){
        List<Score> list = scoreService.list(room_id);
        if (!list.isEmpty()){
            double[] arr = {list.get(0).getGlobal_score(),list.get(0).getBuilding_score(),list.get(0).getBed_score(),list.get(0).getIndoor_score()};
            double avg = Arrays.stream(arr).average().orElse(0);
            list.get(0).setCe_score(avg);
            list.get(0).setImg_name(scoreImageService.selectFileName(list.get(0).getId()));
            return Result.<List<Score>>ok().add(list);
        } else {
            return Result.fail(204,"未评分");
        }
    }

    @PostMapping(value = "/saveOrUpdate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<?> saveOrUpdate(
            @RequestPart @Valid Score score,
            @RequestParam("roomId") @Min(1) Long roomId,
            @RequestPart(value = "file",required = false)MultipartFile file) {
        score.setRoom_id(roomId);

        if (scoreService.isNUll(roomId)){
           Score nscore = scoreService.save(score);

           if(file != null && !file.isEmpty()){
               scoreImageService.uploadFile(file,nscore.getId());
           }
        } else {
            scoreService.update(score);
            List<Score> list = scoreService.list(roomId);
            long score_id = list.get(0).getId();

            if(file != null && !file.isEmpty()){
                scoreImageService.uploadFile(file,score_id);
            }
        }
        return Result.ok("操作成功");
    }

    @GetMapping("/stats")
    public Result<List<GradeStatsDTO>> getScoreStats(
            @RequestParam(required = false) Long buildingId,
            @RequestParam(required = false) String number) {

        List<GradeStatsDTO> data = new ArrayList<>();
        data.add(scoreService.countScoreGrades(buildingId, number));

        return Result.<List<GradeStatsDTO>>ok().add(data);
    }

    @GetMapping("/scoreTrend")
    public Result<List<ScoreTrend>> getScoreTrend(){
        List<ScoreTrend> list = scoreService.getScoreTrend();

        return Result.<List<ScoreTrend>>ok().add(list);
    }
}
