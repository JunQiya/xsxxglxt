package com.rabbiter.dm.service;
import com.rabbiter.dm.dao.AbsenceRecordMapper;
import com.rabbiter.dm.entity.AbsenceRecord;
import com.rabbiter.dm.entity.AbsenceRecordQuery;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AbsenceRecordService {
    private final AbsenceRecordMapper absenceRecordMapper;

    public AbsenceRecordService(AbsenceRecordMapper absenceRecordMapper) {
        this.absenceRecordMapper = absenceRecordMapper;
    }

    // 新增缺勤记录
    public int addAbsenceRecord(AbsenceRecord record) {
        return absenceRecordMapper.insert(record);
    }

    // 更新缺勤记录
    public int updateAbsenceRecord(AbsenceRecord record) {
        return absenceRecordMapper.update(record);
    }

    // 组合条件查询
    public List<AbsenceRecord> searchRecords(AbsenceRecordQuery condition) {
        return absenceRecordMapper.selectByCondition(condition);
    }

    // 姓名模糊查询
    public List<AbsenceRecord> searchByName(String name) {
        return absenceRecordMapper.selectByName(name);
    }

    // 按缺勤日期查询
    public List<AbsenceRecord> searchByAbsentDate(LocalDate date) {
        return absenceRecordMapper.selectByAbsentDate(date);
    }

    // 按班级查询
    public List<AbsenceRecord> searchByClass(Long classId) {
        return absenceRecordMapper.selectByClassId(classId);
    }

    // 返校日期范围查询
    public List<AbsenceRecord> searchByReturnDateRange(LocalDate start, LocalDate end) {
        return absenceRecordMapper.selectByReturnDateRange(start, end);
    }
    public int deleteRecord(Integer id) {
        return absenceRecordMapper.deleteById(id);
    }

    public List<AbsenceRecord> searchAllRecords() {
        return absenceRecordMapper.selectAll();
    }
}
