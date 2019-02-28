package com.example.demo.controller;

import com.example.demo.domain.Teacher;
import com.example.demo.domain.User;
import com.example.demo.service.TeacherService;
import com.example.demo.util.ExcelParam;
import com.example.demo.util.ExcelUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/stuList.do")
    public String stuList(){
        return  "stuList";
    }

    @RequestMapping({"/listTeacher.do"})
    @ResponseBody
    public Map<String, Object> listTeacher(Teacher teacher, Integer pageNum, Integer pageSize) throws Exception {
        Map<String, Object> map = new HashMap();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        if (teacher.getStuNumber() != null) {
            teacher.setStuNumber("%" + teacher.getStuNumber() + "%");
        }
        if (teacher.getTutorNumber() != null) {
            teacher.setTutorNumber("%" + teacher.getTutorNumber() + "%");
        }
        PageInfo<Teacher> page = this.teacherService.pageTeacher(teacher, pageNum, pageSize);
        map.put("page", page);
        return map;
    }

    @RequestMapping({"/isStuNumberExist.do"})
    @ResponseBody
    public Map<String, Object> isStuNumberExist(String stuNumber) throws Exception {
        Map<String, Object> result = new HashMap();
        boolean isSuccess = this.teacherService.isStuNumberExist(stuNumber);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }
        return result;
    }

    @RequestMapping({"/isTutorNumberExist.do"})
    @ResponseBody
    public Map<String, Object> isTutorNumberExist(String stuNumber) throws Exception {
        Map<String, Object> result = new HashMap();
        boolean isSuccess = this.teacherService.isTutorNumberExist(stuNumber);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }
        return result;
    }


    @RequestMapping(value = {"/Teacher.do"}, method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addTeacher(Teacher teacher) throws IOException {
        System.out.println(teacher.getStuNumber()+","+teacher.getTutorNumber()+"添加关系");
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.teacherService.addTeacher(teacher);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }

    @RequestMapping(value = {"/Teacher.do"}, method = {RequestMethod.PUT})
    @ResponseBody
    public Map<String, Object> updateUser(@RequestBody Teacher teacher) throws IOException {
        System.out.println("修改用户");
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.teacherService.updateTeacher(teacher);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }

    @RequestMapping(value = {"/Teacher.do"}, method = {RequestMethod.DELETE})
    @ResponseBody
    public Map<String, Object> delUser(@RequestBody Teacher teacher) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.teacherService.delTeacher(teacher.getId());
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }



    @RequestMapping("/teacherExcelImport.do")
    @ResponseBody
    public Map<String, Object> importTeacher(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap();
        String fileName = file.getOriginalFilename();
        String isSuccess = "";
        try{
            isSuccess = this.teacherService.teacherImport(fileName, file);
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("tip", isSuccess);
        return  map;
    }

    @RequestMapping({"exportTeacher.do"})
    public void exportTeacher(HttpServletResponse response) throws Exception {
        List<Map> list = this.teacherService.getTeacherList();
        /*
        for (Map<String, Object> m : list)
        {
            for (String k : m.keySet())
            {
                System.out.println(k + " : " + m.get(k));
            }
        }*/
        String[] heads = new String[]{ "储干工号", "储干姓名", "导师工号", "导师姓名", "创建时间", "修改时间", "状态"};
        List<String[]> data = new LinkedList();
        for(int i = 0; i < list.size(); ++i) {
            Map entity =  list.get(i);
            String[] temp = new String[]{ entity.get("stuNumber").toString(), entity.get("stuName").toString(),entity.get("tutorNumber").toString(),entity.get("tutorName").toString(),entity.get("createTime").toString(),entity.get("updateTime").toString(),entity.get("state").toString()};
            data.add(temp);
        }
        ExcelParam param = (new ExcelParam.Builder("关系列表")).headers(heads).data(data).build();
        ExcelUtil.export(param, response);
    }

    @RequestMapping(value = {"/getStuNumber.do"}, method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getStuNumber() throws IOException {
        Map<String, Object> map = new HashMap();
        List<User> list = this.teacherService.getUserByNumber("student");
        map.put("list", list);
        return map;
    }

    @RequestMapping(value = {"/getTutorNumber.do"}, method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getTutorNumber() throws IOException {
        Map<String, Object> map = new HashMap();
        List<User> list = this.teacherService.getUserByNumber("tutor");
        map.put("list", list);
        return map;
    }
}
