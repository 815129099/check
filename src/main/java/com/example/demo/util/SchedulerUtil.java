package com.example.demo.util;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.Grade;
import com.example.demo.domain.Plan;
import com.example.demo.domain.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class SchedulerUtil {

    @Autowired
    private UserDao userDao;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //每月1号1:15触发
    @Scheduled(cron = "0 15 1 1 * ?")
    public void testTasks() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        List<Report> reportList = new ArrayList<>();
        List<Plan> planList = new ArrayList<>();
        List<Grade> gList = new ArrayList<>();
        List<String> list = userDao.getStudentList();
        if(month==5 || month==11){
            for(String geNumber:list){
                Report report = new Report();
                report.setStuNumber(geNumber);
                report.setReportName(month+"月实习报告");
                report.setReportType("月度");
                reportList.add(report);

                Report report1 = new Report();
                report1.setStuNumber(geNumber);
                report1.setReportName("年度实习报告");
                report1.setReportType("年度");
                reportList.add(report1);

                Plan plan = new Plan();
                plan.setStuNumber(geNumber);
                plan.setPlanName(month+"月培训计划");
                plan.setPlanType("月度");
                planList.add(plan);

                Plan plan1 = new Plan();
                plan1.setStuNumber(geNumber);
                plan1.setPlanName("年度培训计划");
                plan1.setPlanType("年度");
                planList.add(plan1);

                Grade grade1 = new Grade();
                grade1.setStuNumber(geNumber);
                grade1.setCheckName("年度考核");
                grade1.setCheckType("年度");
                gList.add(grade1);

                Grade grade = new Grade();
                grade.setStuNumber(geNumber);
                grade.setCheckName(month+"月考核");
                grade.setCheckType("月度");
                gList.add(grade);
            }
        }else {
            for(String geNumber:list){
                Report report = new Report();
                report.setStuNumber(geNumber);
                report.setReportName(month+"月实习报告");
                report.setReportType("月度");
                reportList.add(report);

                Plan plan = new Plan();
                plan.setStuNumber(geNumber);
                plan.setPlanName(month+"月培训计划");
                plan.setPlanType("月度");
                planList.add(plan);

                Grade grade = new Grade();
                grade.setStuNumber(geNumber);
                grade.setCheckName(month+"月考核");
                grade.setCheckType("月度");
                gList.add(grade);
            }
        }

        this.userDao.insertReportList(reportList);
        this.userDao.insertPlanList(planList);
        this.userDao.insertCheckList(gList);
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
    }



}
