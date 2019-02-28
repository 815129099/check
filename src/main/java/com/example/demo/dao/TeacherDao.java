package com.example.demo.dao;

import com.example.demo.domain.Teacher;
import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherDao {

    List<Map> listTeacher(Teacher var1);

    void addTeacher(Teacher var1);

    void updateTeacher(Teacher var1);

    void delTeacher(@Param("id") int id);

    Teacher getTeacherByStuNumber(String stuNumber);

    List<Map> getTeacherList();

    void insertTeacherList(List<Teacher> teacherList);

    List<User> getStuNumber();
    List<User> getTutorNumber();

}
