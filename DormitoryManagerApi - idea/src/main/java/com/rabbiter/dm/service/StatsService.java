package com.rabbiter.dm.service;

import com.rabbiter.dm.dao.AbsenceRecordMapper;
import com.rabbiter.dm.dao.LeaveMapper;
import com.rabbiter.dm.dao.RepairMapper;
import com.rabbiter.dm.dao.StudentMapper;
import com.rabbiter.dm.dto.TotalStatsDTO;
import com.rabbiter.dm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {
    @Autowired
    private RepairMapper repairMapper;
    @Autowired
    private LeaveMapper leaveMapper;
    @Autowired
    private AbsenceRecordMapper absenceRecordMapper;
    @Autowired
    private StudentMapper studentMapper;

    public TotalStatsDTO TotalStats(){
        TotalStatsDTO totalStatsDTO = new TotalStatsDTO();

        totalStatsDTO.setRepairCount(repairMapper.selectCount());
        totalStatsDTO.setLeaveCount(leaveMapper.selectCount());
        totalStatsDTO.setAbsenceCount(absenceRecordMapper.selectCount());
        totalStatsDTO.setStudentNum(studentMapper.selectCount());

        return totalStatsDTO;
    }

}
