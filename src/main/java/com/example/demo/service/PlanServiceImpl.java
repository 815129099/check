package com.example.demo.service;

import com.example.demo.dao.PlanDao;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.Plan;
import com.example.demo.domain.Teacher;
import com.example.demo.domain.User;
import com.example.demo.util.DateUtil;
import com.example.demo.util.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanDao planDao;

    @Override
    public PageInfo<Plan> pagePlan(Plan plan, Integer pageNum, Integer pageSize) {
        PageInfo<Plan> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Map> pList = this.planDao.listPlan(plan);
        page = new PageInfo(pList);
        return page;
    }

    @Override
    public PageInfo<Plan> pagePlanByStu(Plan plan, Integer pageNum, Integer pageSize, String geNumber) {
        PageInfo<Plan> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Map> pList = this.planDao.listPlanByStu(plan,geNumber);
        page = new PageInfo(pList);
        return page;
    }

    @Override
    public PageInfo<Plan> pagePlanByTutor(Plan plan, Integer pageNum, Integer pageSize, String geNumber) {
        PageInfo<Plan> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Map> pList = this.planDao.listPlanByTutor(plan,geNumber);
        page = new PageInfo(pList);
        return page;
    }

    @Override
    public String updatePlanById(Plan plan) {
        String isSuccess = "error";
        this.planDao.updatePlanById(plan);
        isSuccess = "success";
        return isSuccess;
    }

    @Override
    public boolean addPlan(Plan plan) {
        boolean isSuccess = false;
        this.planDao.addPlan(plan);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    public boolean delPlan(int id) {
        boolean isSuccess = false;
        this.planDao.delPlan(id);
        isSuccess = true;
        return isSuccess;
    }
}
