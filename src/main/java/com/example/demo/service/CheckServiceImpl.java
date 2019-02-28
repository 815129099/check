package com.example.demo.service;

import com.example.demo.dao.CheckDao;
import com.example.demo.domain.Grade;
import com.example.demo.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private CheckDao checkDao;

    @Override
    public PageInfo<Grade> pageCheck(Grade grade, Integer pageNum, Integer pageSize) {
        PageInfo<Grade> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Map> pList = this.checkDao.listCheck(grade);
        page = new PageInfo(pList);
        return page;
    }



    @Override
    public PageInfo<Grade> pageCheckByTutor(Grade grade, Integer pageNum, Integer pageSize, String geNumber) {
        PageInfo<Grade> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Map> pList = this.checkDao.listCheckByTutor(grade,geNumber);
        page = new PageInfo(pList);
        return page;
    }
/*
    @Override
    public String updateGradeById(Grade Grade) {
        String isSuccess = "error";
        this.GradeDao.updateGradeById(Grade);
        isSuccess = "success";
        return isSuccess;
    }*/

    @Override
    @Transactional
    public boolean addCheck(Grade grade) {
        boolean isSuccess = false;
        this.checkDao.addCheck(grade);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean delCheck(int id) {
        boolean isSuccess = false;
        this.checkDao.delCheck(id);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean updateCheck(Grade grade) {
        boolean isSuccess = false;
        grade.setTotal(grade.getDuty()+grade.getTeam()+grade.getWork()+grade.getDiscipline()-grade.getDeduct());
        grade.setUpdateTime(DateUtil.getDate());
        this.checkDao.updateCheck(grade);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    public Grade getGradeById(int id) {
        return this.checkDao.getGradeById(id);
    }

    public List<Map> getGradeList(){
        return this.checkDao.getGradeList();
    }
}
