package com.example.demo.service;

import com.example.demo.domain.Grade;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CheckService {

    PageInfo<Grade> pageCheck(Grade var1, Integer var2, Integer var3);

    PageInfo<Grade> pageCheckByTutor(Grade var1, Integer var2, Integer var3, String geNumber);
   // String updateCheckById(Grade Check);

    boolean addCheck(Grade grade);

    boolean delCheck(int id);

    boolean updateCheck(Grade grade);

    Grade getGradeById(int id);

    List<Map> getGradeList();

}
