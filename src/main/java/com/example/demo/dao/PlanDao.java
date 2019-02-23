package com.example.demo.dao;

import com.example.demo.domain.Plan;
import com.example.demo.domain.Teacher;
import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface PlanDao {

    List<Map> listPlan(Plan plan);
    List<Map> listPlanByStu(@Param("p") Plan plan,@Param("geNumber")String geNumber);
    List<Map> listPlanByTutor(@Param("p") Plan plan,@Param("geNumber")String geNumber);
    void updatePlanById(Plan plan);
    void addPlan(Plan plan);
    void delPlan(int id);
}
