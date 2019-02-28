package com.example.demo.controller;

import com.example.demo.domain.Grade;
import com.example.demo.domain.Plan;
import com.example.demo.domain.User;
import com.example.demo.service.CheckService;
import com.example.demo.service.PlanService;
import com.example.demo.service.UserService;
import com.example.demo.util.DateUtil;
import com.example.demo.util.ExcelParam;
import com.example.demo.util.ExcelUtil;
import com.github.pagehelper.PageInfo;
import com.sun.tools.javac.comp.Check;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class CheckController {

    @Autowired
    CheckService checkService;
    @Autowired
    UserService userService;

    @RequestMapping("/checkList.do")
    public String planList(){
        return  "checkList";
    }

    @RequestMapping({"/listCheck.do"})
    @ResponseBody
    public Map<String, Object> listCheck(Grade grade, Integer pageNum, Integer pageSize) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        Map<String, Object> map = new HashMap();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        if (grade.getStuNumber() != null) {
            grade.setStuNumber("%" + grade.getStuNumber() + "%");
        }
        if (grade.getCheckName() != null) {
            grade.setCheckName("%" + grade.getCheckName() + "%");
        }
        Set<String> set = this.userService.findRoles(geNumber);
        String role = "";
        if (!set.isEmpty()) {
            role = (String)set.iterator().next();
            System.out.println(role);
        }
        PageInfo page;
        if (role.equals("admin")){
            page = this.checkService.pageCheck(grade, pageNum, pageSize);
            map.put("page", page);
        }else if(role.equals("tutor")){
            page = this.checkService.pageCheckByTutor(grade, pageNum, pageSize,geNumber);
            map.put("page", page);
        }
        return map;
    }

    @RequestMapping(value = {"/Check.do"}, method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addCheck(Grade grade) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.checkService.addCheck(grade);//添加
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }


    @RequestMapping(value = {"/Check.do"}, method = {RequestMethod.DELETE})
    @ResponseBody
    public Map<String, Object> delCheck(@RequestBody Grade grade) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.checkService.delCheck(grade.getId());
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }

    @RequestMapping(value = {"/Check.do"}, method = {RequestMethod.PUT})
    @ResponseBody
    public Map<String, Object> updateCheck(@RequestBody Grade grade) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        grade.setCheckPeople(geNumber);
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.checkService.updateCheck(grade);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }

    @RequestMapping(value = {"/getGradeById.do"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getGradeById(int id) throws IOException {
        System.out.println(id);
        Map<String, Object> map = new HashMap();
        Grade grade = this.checkService.getGradeById(id);
        if(StringUtils.isEmpty(grade)){
            map.put("tip", "error");
        }else {
            map.put("tip","success");
            map.put("grade",grade);
        }
        return map;
    }

    @RequestMapping({"exportGrade.do"})
    public void exportGrade(HttpServletResponse response) throws Exception {
        List<Map> list = this.checkService.getGradeList();
        String[] heads = new String[]{ "储干工号", "储干姓名","考核名称",
                "类型","状态","评分人",
                 "修改时间", "责任心，主动性",
                "组织纪律、原则性","工作能力","团队协作能力",
                "特殊事项","扣分","总分"};
        List<String[]> data = new LinkedList();
        for(int i = 0; i < list.size(); ++i) {
            Map entity =  list.get(i);
            String[] temp = new String[]{ entity.get("stuNumber").toString(), entity.get("stuName").toString(),entity.get("checkName").toString(),
                    entity.get("checkType").toString(),entity.get("checkState").toString(),entity.get("checkPeopleName").toString(),
                   entity.get("updateTime").toString(),entity.get("duty").toString(),
                    entity.get("discipline").toString(),entity.get("work").toString(),entity.get("team").toString(),
                    entity.get("item").toString(),entity.get("deduct").toString(),entity.get("total").toString()};
            data.add(temp);
        }
        ExcelParam param = (new ExcelParam.Builder("成绩列表")).headers(heads).data(data).build();
        ExcelUtil.export(param, response);
    }


}
