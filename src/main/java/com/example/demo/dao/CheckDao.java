package com.example.demo.dao;

import com.example.demo.domain.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CheckDao {

    List<Map> listCheck(Grade grade);
  //  List<Map> listCheckByStu(@Param("p") Check Check, @Param("geNumber") String geNumber);
    List<Map> listCheckByTutor(@Param("p") Grade grade, @Param("geNumber") String geNumber);
   // void updateCheckById(Check Check);
    void addCheck(Grade grade);
    void updateCheck(Grade grade);
    void delCheck(int id);
    Grade getGradeById(int id);
    List<Map> getGradeList();
}
