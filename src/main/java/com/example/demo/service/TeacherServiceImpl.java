package com.example.demo.service;

import com.example.demo.dao.TeacherDao;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.Teacher;
import com.example.demo.domain.User;
import com.example.demo.util.DateUtil;
import com.example.demo.util.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private UserDao userDao;

    public PageInfo<Teacher> pageTeacher(Teacher teacher, Integer pageNum, Integer pageSize) {
        PageInfo<Teacher> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Map> uList = this.teacherDao.listTeacher(teacher);
        page = new PageInfo(uList);
        return page;
    }

    @Transactional
    public boolean addTeacher(Teacher teacher) {
        boolean isSuccess = false;
        teacher.setCreateTime(DateUtil.getDate());
        teacher.setUpdateTime(DateUtil.getDate());
        this.teacherDao.addTeacher(teacher);
        isSuccess = true;
        return isSuccess;
    }

    public boolean isStuNumberExist(String stuNumber) {
        boolean isSuccess = false;
        Teacher teacher = this.teacherDao.getTeacherByStuNumber(stuNumber);
        if (StringUtils.isEmpty(teacher)) {
            isSuccess = true;
        }
        return isSuccess;
    }

    public boolean isTutorNumberExist(String stuNumber) {
        boolean isSuccess = true;
        User user = this.userDao.getUserById(stuNumber);
        if (StringUtils.isEmpty(user)) {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean updateTeacher(Teacher teacher) {
        boolean isSuccess = false;
        teacher.setUpdateTime(DateUtil.getDate());
        this.teacherDao.updateTeacher(teacher);
        isSuccess = true;
        return isSuccess;
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean delTeacher(int id) {
        boolean isSuccess = false;
        this.teacherDao.delTeacher(id);
        isSuccess = true;
        return isSuccess;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String teacherImport(String fileName, MultipartFile file) throws Exception {
        Sheet sheet = ExcelUtil.isExcel(fileName,file);
        List<Teacher> teacherList = new ArrayList<Teacher>();
        Teacher teacher;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            teacher = new Teacher();
            if( row.getCell(0).getCellType() !=1){
                return "导入失败(第"+(r+1)+"行,请设为文本格式)";
            }
            String stuNumber = row.getCell(0).getStringCellValue();
            if(stuNumber == null || stuNumber.isEmpty()){
                return "导入失败(第"+(r+1)+"行,工号未填写)";
            }
            String tutorNumber = row.getCell(1).getStringCellValue();
            if(tutorNumber==null || tutorNumber.isEmpty()){
                return "导入失败(第"+(r+1)+"行,姓名未填写)";
            }
            teacher.setStuNumber(stuNumber);
            teacher.setTutorNumber(tutorNumber);
            teacher.setCreateTime(DateUtil.getDate());
            teacher.setUpdateTime(DateUtil.getDate());
            teacherList.add(teacher);
        }
        /*
        for (User userResord : userList) {
            String name = userResord.getName();
            int cnt = userMapper.selectByName(name);
            if (cnt == 0) {
                userMapper.addUser(userResord);
                System.out.println(" 插入 "+userResord);
            } else {
                userMapper.updateUserByName(userResord);
                System.out.println(" 更新 "+userResord);
            }
        }
        */
        this.teacherDao.insertTeacherList(teacherList);
        return "success";
    }

    public List<Map> getTeacherList(){
        return this.teacherDao.getTeacherList();
    }

    @Override
    public List<User> getUserByNumber(String type) {
        if(type.equals("student")){
            return teacherDao.getStuNumber();
        }else if(type.equals("tutor")){
            return teacherDao.getTutorNumber();
        }
        return null;
    }
}
