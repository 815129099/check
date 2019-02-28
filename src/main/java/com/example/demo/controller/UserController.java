package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.util.ExcelParam;
import com.example.demo.util.ExcelUtil;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/xkLogin.do")
    public String xkLogin(){
        return  "xkLogin";
    }
    @RequestMapping("/maSystem.do")
    public ModelAndView maSystem(){
        return  new ModelAndView("/maSystem");
    }

    @RequestMapping("/userInfo.do")
    public ModelAndView userInfo(){
        return  new ModelAndView("/userInfo");
    }

    @RequestMapping("/meList.do")
    public ModelAndView meList(){
        return  new ModelAndView("/meList");
    }

    @RequestMapping("/introduce.do")
    public ModelAndView introduce(){
        return  new ModelAndView("/introduce");
    }

    @RequestMapping("/error.do")
    public ModelAndView error(){
        return  new ModelAndView("/error");
    }

    @RequestMapping("/login.do")
    public ModelAndView login(){
        return  new ModelAndView("/login");
    }

    @RequestMapping("/testLogin.do")
    public ModelAndView testLogin(){
        return  new ModelAndView("/login1");
    }

    @RequestMapping({"/listUser.do"})
    @ResponseBody
    public Map<String, Object> listUser(User user, Integer pageNum, Integer pageSize) throws Exception {
        Map<String, Object> map = new HashMap();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 15;
        }

        if (user.getGeName() != null) {
            user.setGeName("%" + user.getGeName() + "%");
        }

        if (user.getGeNumber() != null) {
            user.setGeNumber("%" + user.getGeNumber() + "%");
        }

        PageInfo<User> page = this.userService.pageUser(user, pageNum, pageSize);
        map.put("page", page);
        return map;
    }

    @RequestMapping({"/isNumberExist.do"})
    @ResponseBody
    public Map<String, Object> isNumberExist(String geNumber) throws Exception {
        Map<String, Object> result = new HashMap();
        boolean isSuccess = this.userService.isNumberExist(geNumber);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }

        return result;
    }

    @RequestMapping(value = {"/User.do"}, method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addUser(User user) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.addUser(user);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping(value = {"/User.do"}, method = {RequestMethod.PUT})
    @ResponseBody
    public Map<String, Object> updateUser(@RequestBody User user) throws IOException {
        System.out.println("修改用户");
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.updateUser(user);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }

    @RequestMapping(value = {"/User.do"}, method = {RequestMethod.DELETE})
    @ResponseBody
    public Map<String, Object> delUser(@RequestBody User user) throws IOException {
        System.out.println("删除用户");
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.delUser(user.getGeNumber());
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }

    @RequestMapping({"/password.do"})
    @ResponseBody
    public Map<String, Object> updatePassword(String password, String newPassword) throws IOException {
        Map<String, Object> map = new HashMap();
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        System.out.println(password + "," + newPassword + "," + geNumber);
        boolean isSuccess = this.userService.updatePassword(geNumber, password, newPassword);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/lockUser.do"})
    @ResponseBody
    public Map<String, Object> lockUser(int[] arr) throws IOException {
        int[] var2 = arr;
        int var3 = arr.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            int id = var2[var4];
            System.out.println(id);
        }

        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.lockUser(arr);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/clearUser.do"})
    @ResponseBody
    public Map<String, Object> clearUser(int[] arr) throws IOException {
        int[] var2 = arr;
        int var3 = arr.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            int id = var2[var4];
            System.out.println(id);
        }
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.clearUser(arr);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }
        return map;
    }

    @RequestMapping({"/login"})
    @ResponseBody
    public Map<String, Object> Login(String geNumber, String password, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        Subject subject = SecurityUtils.getSubject();
        //Session session = subject.getSession(true);
       // Serializable sessionId = session.getId();
       // System.out.println(sessionId.toString());
        UsernamePasswordToken token = new UsernamePasswordToken(geNumber, password);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            map.put("tip", "账号不存在");
            return map;
        } catch (DisabledAccountException e) {
            map.put("tip", "账号未启用");
            return map;
        } catch (IncorrectCredentialsException e) {
            map.put("tip", "密码错误");
            return map;
        } catch (Exception e) {
            map.put("tip", "未知错误");
            return map;
        }
        //AccessRecord accessRecord = new AccessRecord();
        //accessRecord.setIpNumber(NetworkUtil.getIpAddr(request));
        //accessRecord.setGeNumber(geNumber);
       // accessRecord.setSessionId(sessionId.toString());
        //this.utilService.addAccess(accessRecord);
        map.put("tip", "success");
        return map;
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "xkLogin";
    }


    @RequestMapping("/userExcelImport.do")
    @ResponseBody
    public Map<String, Object> importUser(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap();
        String fileName = file.getOriginalFilename();
        String isSuccess = "";
        try{
             isSuccess = this.userService.userImport(fileName, file);
        }catch (Exception e){
            e.printStackTrace();
        }
            map.put("tip", isSuccess);
        return  map;
    }

    @RequestMapping({"exportUser.do"})
    public void exportUser(HttpServletResponse response) throws Exception {
        List<User> list = this.userService.getUserList();
        String[] heads = new String[]{ "工号", "姓名", "角色", "状态", "创建时间", "修改时间", "电话", "邮件"};
        List<String[]> data = new LinkedList();
        for(int i = 0; i < list.size(); ++i) {
            User entity = (User)list.get(i);
            String[] temp = new String[]{ entity.getGeNumber(), entity.getGeName(), String.valueOf(entity.getRole()), String.valueOf(entity.getUserState()), entity.getCreateTime(), entity.getUpdateTime(), entity.getPhone(), entity.getEmail()};
            data.add(temp);
        }
        ExcelParam param = (new ExcelParam.Builder("用户列表")).headers(heads).data(data).build();
        ExcelUtil.export(param, response);
    }


}
