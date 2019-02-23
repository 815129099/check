package com.example.demo.dao;

import com.example.demo.domain.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportDao {

    List<Map> listReport(Report report);
    List<Map> listReportByStu(@Param("p") Report report, @Param("geNumber") String geNumber);
    List<Map> listReportByTutor(@Param("p") Report report, @Param("geNumber") String geNumber);
    void updateReportById(Report report);
    void addReport(Report report);
    void delReport(int id);
}
