package com.example.demo;

import com.example.demo.domain.Plan;
import com.example.demo.service.PlanService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PlanServiceTest extends DemoApplicationTests {

    @Autowired
    private PlanService planService;

    @Ignore
    @Test
    public void testDelPlan(){
        boolean isSuccess = planService.delPlan(21);
        if(isSuccess){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }
    }

    @Test
    public void testAddPlan(){
        Plan plan = new Plan();
        plan.setStuNumber("ge14444");
        plan.setPlanName("7月份计划");
        plan.setPlanType("月度");
        plan.setPlanState("未上传");
        boolean isSuccess = planService.addPlan(plan);
        if(isSuccess){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }
    }
}
