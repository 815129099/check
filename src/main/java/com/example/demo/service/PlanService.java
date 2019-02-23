package com.example.demo.service;

import com.example.demo.domain.Plan;
import com.example.demo.domain.Teacher;
import com.example.demo.domain.User;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PlanService {

    PageInfo<Plan> pagePlan(Plan var1, Integer var2, Integer var3);
    PageInfo<Plan> pagePlanByStu(Plan var1, Integer var2, Integer var3,String geNumber);
    PageInfo<Plan> pagePlanByTutor(Plan var1, Integer var2, Integer var3,String geNumber);
    String updatePlanById(Plan plan);

    boolean addPlan(Plan plan);

    boolean delPlan(int id);

}
