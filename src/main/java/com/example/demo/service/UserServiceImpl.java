package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import com.example.demo.util.DateUtil;
import com.example.demo.util.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public Set<String> findPermissions(String geNumber) {
        return this.userDao.findPermissions(geNumber);
    }

    public Set<String> findRoles(String geNumber) {
        return this.userDao.findRoles(geNumber);
    }

    public User findByGeNumber(String geNumber) {
        return this.userDao.findByGeNumber(geNumber);
    }

    public PageInfo<User> pageUser(User user, Integer pageNum, Integer pageSize) {
        System.out.println(user.getGeNumber() + "," + user.getGeName() + "," + user.getUserState());
        PageInfo<User> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<User> uList = this.userDao.listUser(user);
        page = new PageInfo(uList);
        return page;
    }

    @Transactional
    public boolean addUser(User user) {
        boolean isSuccess = false;
        user.setCreateTime(DateUtil.getDate());
        user.setUpdateTime(DateUtil.getDate());
        this.userDao.addUser(user);
        isSuccess = true;
        return isSuccess;
    }

    public boolean isNumberExist(String geNumber) {
        boolean isSuccess = false;
        User user = this.userDao.getUserById(geNumber);
        if (StringUtils.isEmpty(user)) {
            isSuccess = true;
        }
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateUser(User user) {
        boolean isSuccess = false;
        user.setUpdateTime(DateUtil.getDate());
        this.userDao.updateUser(user);
        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean delUser(String geNumber) {
        boolean isSuccess = false;
        this.userDao.delUser(geNumber, DateUtil.getDate());
        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updatePassword(String geNumber, String password, String newPassword) {
        boolean isSuccess = false;
        String str = this.userDao.findByGeNumber(geNumber).getPassword();
        if (str.equals(password)) {
            this.userDao.updatePassword(newPassword, geNumber, DateUtil.getDate());
            isSuccess = true;
        }

        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean lockUser(int[] arr) {
        boolean isSuccess = false;
        int[] var3 = arr;
        int var4 = arr.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int id = var3[var5];
            this.userDao.lockUser(id, DateUtil.getDate());
        }

        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean clearUser(int[] arr) {
        boolean isSuccess = false;
        int[] var3 = arr;
        int var4 = arr.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int id = var3[var5];
            this.userDao.clearUser(id, DateUtil.getDate());
        }

        isSuccess = true;
        return isSuccess;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String userImport(String fileName, MultipartFile file) throws Exception {
        Sheet sheet = ExcelUtil.isExcel(fileName,file);
        List<User> userList = new ArrayList<User>();
        User user;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            user = new User();
            if( row.getCell(0).getCellType() !=1){
                return "导入失败(第"+(r+1)+"行,请设为文本格式)";
            }
            String geNumber = row.getCell(0).getStringCellValue();
            if(geNumber == null || geNumber.isEmpty()){
                return "导入失败(第"+(r+1)+"行,工号未填写)";
            }
            String geName = row.getCell(1).getStringCellValue();
            if(geName==null || geName.isEmpty()){
                return "导入失败(第"+(r+1)+"行,姓名未填写)";
            }
            //设置Cell类型，把password以String格式取出来
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            String password = row.getCell(2).getStringCellValue();
            if(password==null){
                return "导入失败(第"+(r+1)+"行,密码未填写)";
            }
            String role = row.getCell(3).getStringCellValue();
            if(role==null){
                return "导入失败(第"+(r+1)+"行,角色未填写)";
            }
            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            String phone = row.getCell(4).getStringCellValue();
            if(StringUtils.isEmpty(phone)){
                phone = "";
            }
            String email = row.getCell(5).getStringCellValue();
            if(StringUtils.isEmpty("email")){
                email="";
            }
            user.setGeNumber(geNumber);
            user.setGeName(geName);
            user.setPassword(password);
            user.setRole(role);
            user.setCreateTime(DateUtil.getDate());
            user.setUpdateTime(DateUtil.getDate());
            user.setPhone(phone);
            user.setEmail(email);
            userList.add(user);
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
        this.userDao.insertUserList(userList);
        return "success";
    }



    public List<User> getUserList() {
        return this.userDao.getUserList();
    }

}
