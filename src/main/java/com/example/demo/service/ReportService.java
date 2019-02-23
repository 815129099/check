package com.example.demo.service;

import com.example.demo.domain.Report;
import com.github.pagehelper.PageInfo;

public interface ReportService {

    PageInfo<Report> pageReport(Report var1, Integer var2, Integer var3);
    PageInfo<Report> pageReportByStu(Report var1, Integer var2, Integer var3, String geNumber);
    PageInfo<Report> pageReportByTutor(Report var1, Integer var2, Integer var3, String geNumber);
    String updateReportById(Report Report);

    boolean addReport(Report Report);

    boolean delReport(int id);

}
