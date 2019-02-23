package com.example.demo.service;

import com.example.demo.dao.ReportDao;
import com.example.demo.domain.Report;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public PageInfo<Report> pageReport(Report report, Integer pageNum, Integer pageSize) {
        PageInfo<Report> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Map> pList = this.reportDao.listReport(report);
        page = new PageInfo(pList);
        return page;
    }

    @Override
    public PageInfo<Report> pageReportByStu(Report report, Integer pageNum, Integer pageSize, String geNumber) {
        PageInfo<Report> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Map> pList = this.reportDao.listReportByStu(report,geNumber);
        page = new PageInfo(pList);
        return page;
    }

    @Override
    public PageInfo<Report> pageReportByTutor(Report report, Integer pageNum, Integer pageSize, String geNumber) {
        PageInfo<Report> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Map> pList = this.reportDao.listReportByTutor(report,geNumber);
        page = new PageInfo(pList);
        return page;
    }

    @Override
    public String updateReportById(Report report) {
        String isSuccess = "error";
        this.reportDao.updateReportById(report);
        isSuccess = "success";
        return isSuccess;
    }

    @Override
    public boolean addReport(Report report) {
        boolean isSuccess = false;
        this.reportDao.addReport(report);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    public boolean delReport(int id) {
        boolean isSuccess = false;
        this.reportDao.delReport(id);
        isSuccess = true;
        return isSuccess;
    }
}
