package com.rabbiter.dm.dao;
import com.rabbiter.dm.entity.AbsenceRecord;
import com.rabbiter.dm.entity.AbsenceRecordQuery;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AbsenceRecordMapper {
    int insert(AbsenceRecord record);
    int update(AbsenceRecord record);
    int deleteById(@Param("id") Integer id);
    AbsenceRecord selectById(Integer id);
    // 修改参数类型为AbsenceRecordQuery
    List<AbsenceRecord> selectByCondition(@Param("condition") AbsenceRecordQuery condition);

    List<AbsenceRecord> selectByName(String name);
    List<AbsenceRecord> selectByAbsentDate(LocalDate absentDate);
    List<AbsenceRecord> selectByClassId(Long classId);
    List<AbsenceRecord> selectByReturnDateRange(
            @Param("beginDate") LocalDate beginDate,
            @Param("endDate") LocalDate endDate
    );

    List<AbsenceRecord> selectAll();

    int selectCount();

}
