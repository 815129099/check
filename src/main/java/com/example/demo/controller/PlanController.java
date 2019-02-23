package com.example.demo.controller;

import com.example.demo.domain.Plan;
import com.example.demo.domain.User;
import com.example.demo.service.PlanService;
import com.example.demo.service.UserService;
import com.example.demo.util.DateUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class PlanController {

    @Autowired
    PlanService planService;
    @Autowired
    UserService userService;

    @RequestMapping("/planList.do")
    public ModelAndView planList(){
        return  new ModelAndView("/planList");
    }

    @RequestMapping({"/listPlan.do"})
    @ResponseBody
    public Map<String, Object> listPlan(Plan plan, Integer pageNum, Integer pageSize) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        Map<String, Object> map = new HashMap();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        if (plan.getStuNumber() != null) {
            plan.setStuNumber("%" + plan.getStuNumber() + "%");
        }
        if (plan.getPlanName() != null) {
            plan.setPlanName("%" + plan.getPlanName() + "%");
        }
        Set<String> set = this.userService.findRoles(geNumber);
        String role = "";
        if (!set.isEmpty()) {
            role = (String)set.iterator().next();
            System.out.println(role);
        }
        PageInfo page;
        if (role.equals("admin")){
            page = this.planService.pagePlan(plan, pageNum, pageSize);
            map.put("page", page);
        }else if(role.equals("student")){
            page = this.planService.pagePlanByStu(plan,pageNum,pageSize,geNumber);
            map.put("page", page);
        }else if(role.equals("tutor")){
            page = this.planService.pagePlanByTutor(plan,pageNum,pageSize,geNumber);
            map.put("page", page);
        }
        return map;
    }

    @RequestMapping("/upPlan.do")
    @ResponseBody
    public Map<String, Object> upPlan(@RequestParam("file") MultipartFile file,@RequestParam("id") int id,@RequestParam("geNumber") String geNumber,@RequestParam("geName") String geName,@RequestParam("planName") String planName) {
        Map<String, Object> map = new HashMap();
        try {
            if (file.isEmpty()) {
                map.put("tip","文件为空");
                return map;
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("文件的后缀名为：" + suffixName);
            // 设置文件存储路径
            String filePath = "C:/check/plan/";
            String path = filePath + geNumber+"-"+geName+"-"+planName+suffixName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            Subject subject = SecurityUtils.getSubject();
            String upName = (String)subject.getPrincipal();
            Plan plan = new Plan();
            plan.setId(id);
            plan.setUpName(upName);
            plan.setUpTime(DateUtil.getDate());
            String isSuccess = planService.updatePlanById(plan);
            map.put("tip",isSuccess);

            return map;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("tip", "error");
        return  map;
    }

    @RequestMapping({"/downPlan.do"})
    public ResponseEntity<byte[]> downPlan(HttpServletRequest request, @RequestParam("fileName") String fileName) throws Exception {
        String path = "C:/check/plan/";
        String fName =fileName+".xlsx";
        File file = new File(path + File.separator + fName);
        if(!file.exists()){
            fName =fileName+".xls";
             file = new File(path + File.separator + fName);
        }
        HttpHeaders headers = new HttpHeaders();
        String downloadFielName = new String(fName.getBytes("UTF-8"), "iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFielName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = {"/Plan.do"}, method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addPlan(Plan plan) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.planService.addPlan(plan);//添加
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }


    @RequestMapping(value = {"/Plan.do"}, method = {RequestMethod.DELETE})
    @ResponseBody
    public Map<String, Object> delPlan(@RequestBody Plan plan) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.planService.delPlan(plan.getId());
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }
}
