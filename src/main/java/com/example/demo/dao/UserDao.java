package com.example.demo.dao;

import com.example.demo.domain.Grade;
import com.example.demo.domain.Plan;
import com.example.demo.domain.Report;
import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Set;

@Mapper
public interface UserDao {
    Set<String> findRoles(String var1);

    Set<String> findPermissions(String var1);

    User findByGeNumber(String var1);

    List<User> listUser(User var1);

    void addUser(User var1);

    User getUserById(String var1);

    void updateUser(User var1);

    void delUser(@Param("geNumber") String var1, @Param("updateTime") String var2);

    void updatePassword(@Param("password") String var1, @Param("geNumber") String var2, @Param("updateTime") String var3);

    void lockUser(@Param("id") int var1, @Param("updateTime") String var2);

    void clearUser(@Param("id") int var1, @Param("updateTime") String var2);

    void insertUserList(List<User> userList);

    List<User> getUserList();

    List<String> getStudentList();

    void insertReportList(List<Report> rList);

    void insertPlanList(List<Plan> pList);

    void insertCheckList(List<Grade> gList);
}
