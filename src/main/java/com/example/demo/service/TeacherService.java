package com.example.demo.service;

import com.example.demo.domain.Teacher;
import com.example.demo.domain.User;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

public interface TeacherService {

    PageInfo<Teacher> pageTeacher(Teacher var1, Integer var2, Integer var3);

    boolean addTeacher(Teacher var1);

    boolean isStuNumberExist(String var1);

    boolean isTutorNumberExist(String var1);

    boolean updateTeacher(Teacher var1);

    boolean delTeacher(int id);

    List<Map> getTeacherList();

    String teacherImport(String fileName, MultipartFile file) throws Exception;

    List<User> getUserByNumber(String type);
}
