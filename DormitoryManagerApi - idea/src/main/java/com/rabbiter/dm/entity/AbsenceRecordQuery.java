package com.rabbiter.dm.entity;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AbsenceRecordQuery {
    private String name;
    private Long classId;
    private LocalDate absentDate;
    private LocalDate beginReturnDate;
    private LocalDate endReturnDate;
    private LocalDate beginAbsentDate;
    private LocalDate endAbsentDate;
}
