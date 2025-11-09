package com.rabbiter.dm.controller;
import com.rabbiter.dm.entity.AbsenceRecord;
import com.rabbiter.dm.entity.AbsenceRecordQuery;
import com.rabbiter.dm.service.AbsenceRecordService;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/absence")
public class AbsenceRecordController {
    private final AbsenceRecordService absenceRecordService;

    public AbsenceRecordController(AbsenceRecordService absenceRecordService) {
        this.absenceRecordService = absenceRecordService;
    }

    // 组合条件查询
    @PostMapping("/list")
    public Map<String, Object> listByCondition(@RequestBody AbsenceRecordQuery condition) {
        System.err.println("Received search params: " + condition);
        return new HashMap<String, Object>() {{
            put("statusCode", 200);
            put("data", absenceRecordService.searchRecords(condition));
        }};
    }

    // 新增记录
    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody AbsenceRecord record) {
        absenceRecordService.addAbsenceRecord(record);
        return new HashMap<String, Object>() {{
            put("statusCode", 200);
            put("message", "记录添加成功");
        }};
    }

    // 更新记录
    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody AbsenceRecord record) {
        absenceRecordService.updateAbsenceRecord(record);
        return new HashMap<String, Object>() {{
            put("statusCode", 200);
            put("message", "记录更新成功");
        }};
    }

    // 删除记录
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Integer id) {
        absenceRecordService.deleteRecord(id);
        return new HashMap<String, Object>() {{
            put("statusCode", 200);
            put("message", "记录删除成功");
        }};
    }

    // 以下为独立查询接口
    @GetMapping("/search/name")
    public Map<String, Object> searchByName(@RequestParam String name) {
        return new HashMap<String, Object>() {{
            put("statusCode", 200);
            put("data", absenceRecordService.searchByName(name));
        }};
    }

    @GetMapping("/search/date")
    public Map<String, Object> searchByDate(@RequestParam LocalDate date) {
        return new HashMap<String, Object>() {{
            put("statusCode", 200);
            put("data", absenceRecordService.searchByAbsentDate(date));
        }};
    }

    @GetMapping("/search/class")
    public Map<String, Object> searchByClass(@RequestParam Long classId) {
        return new HashMap<String, Object>() {{
            put("statusCode", 200);
            put("data", absenceRecordService.searchByClass(classId));
        }};
    }

    @GetMapping("/search/return")
    public Map<String, Object> searchReturnDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return new HashMap<String, Object>() {{
            put("statusCode", 200);
            put("data", absenceRecordService.searchByReturnDateRange(start, end));
        }};
    }

    @GetMapping("/all")
    public Map<String, Object> listAll() {
        return new HashMap<String, Object>(2) {{
            put("statusCode", 200);
            put("data", absenceRecordService.searchAllRecords());
        }};
    }
}