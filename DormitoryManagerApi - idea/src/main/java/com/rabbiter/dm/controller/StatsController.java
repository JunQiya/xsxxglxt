package com.rabbiter.dm.controller;

import com.rabbiter.dm.dto.TotalStatsDTO;
import com.rabbiter.dm.service.StatsService;
import com.rabbiter.dm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/totalStats")
    public Result<List<TotalStatsDTO>> getTotalStats(){
        List<TotalStatsDTO> list = new ArrayList<>();
        list.add(statsService.TotalStats());
        return Result.<List<TotalStatsDTO>>ok().add(list);
    }
}
