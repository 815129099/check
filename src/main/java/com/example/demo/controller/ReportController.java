package com.example.demo.controller;

import com.example.demo.domain.Report;
import com.example.demo.service.ReportService;
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
public class ReportController {

    @Autowired
    ReportService reportService;
    @Autowired
    UserService userService;

    @RequestMapping("/reportList.do")
    public ModelAndView ReportList(){
        return  new ModelAndView("/reportList");
    }

    @RequestMapping({"/listReport.do"})
    @ResponseBody
    public Map<String, Object> listReport(Report report, Integer pageNum, Integer pageSize) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        Map<String, Object> map = new HashMap();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        if (report.getStuNumber() != null) {
            report.setStuNumber("%" + report.getStuNumber() + "%");
        }
        if (report.getReportName() != null) {
            report.setReportName("%" + report.getReportName() + "%");
        }
        Set<String> set = this.userService.findRoles(geNumber);
        String role = "";
        if (!set.isEmpty()) {
            role = (String)set.iterator().next();
        }
        PageInfo page;
        if (role.equals("admin")){
            page = this.reportService.pageReport(report, pageNum, pageSize);
            map.put("page", page);
        }else if(role.equals("student")){
            page = this.reportService.pageReportByStu(report,pageNum,pageSize,geNumber);
            map.put("page", page);
        }else if(role.equals("tutor")){
            page = this.reportService.pageReportByTutor(report,pageNum,pageSize,geNumber);
            map.put("page", page);
        }
        return map;
    }

    @RequestMapping("/upReport.do")
    @ResponseBody
    public Map<String, Object> upReport(@RequestParam("file") MultipartFile file,@RequestParam("id") int id,@RequestParam("geNumber") String geNumber,@RequestParam("geName") String geName,@RequestParam("reportName") String reportName) {
        System.out.println(id+" "+geNumber+" "+geName+" "+reportName);
        Map<String, Object> map = new HashMap();
        try {
            if (file.isEmpty()) {
                map.put("tip","文件为空");
                return map;
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 设置文件存储路径
            String filePath = "C:/check/report/";
            String path = filePath + geNumber+"-"+geName+"-"+reportName+suffixName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            Report report = new Report();
            report.setId(id);
            report.setUpTime(DateUtil.getDate());
            String isSuccess = reportService.updateReportById(report);
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

    @RequestMapping({"/downReport.do"})
    public ResponseEntity<byte[]> downReport(HttpServletRequest request, @RequestParam("fileName") String fileName) throws Exception {
        String path = "C:/check/report/";
        String fName =fileName+".docx";
        File file = new File(path + File.separator + fName);
        if(!file.exists()){
             fName =fileName+".doc";
             file = new File(path + File.separator + fName);
            if(!file.exists()){
                fName =fileName+".pdf";
                file = new File(path + File.separator + fName);
            }
        }
        HttpHeaders headers = new HttpHeaders();
        String downloadFielName = new String(fName.getBytes("UTF-8"), "iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFielName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = {"/Report.do"}, method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addReport(Report report) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.reportService.addReport(report);//添加
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }


    @RequestMapping(value = {"/Report.do"}, method = {RequestMethod.DELETE})
    @ResponseBody
    public Map<String, Object> delReport(@RequestBody Report report) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.reportService.delReport(report.getId());
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }
}
