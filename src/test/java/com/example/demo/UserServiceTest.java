package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.service.TeacherService;
import com.example.demo.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserServiceTest extends DemoApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setGeNumber("ge14444");
        boolean isSuccess = userService.addUser(user);
    }

    @Test
    public void testGradeList(){
        List<Map> list = this.teacherService.getTeacherList();
        for (Map<String, Object> m : list)
        {
            for (String k : m.keySet())
            {
                System.out.println(k + " : " + m.get(k));
            }
        }
    }
}
