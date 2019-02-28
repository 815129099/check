package com.example.demo.service;


import com.example.demo.domain.User;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Set;

public interface UserService {
    Set<String> findPermissions(String var1);

    Set<String> findRoles(String var1);

    User findByGeNumber(String var1);

    PageInfo<User> pageUser(User var1, Integer var2, Integer var3);


    boolean addUser(User var1);

    boolean isNumberExist(String var1);

    boolean updateUser(User var1);

    boolean delUser(String var1);

    boolean updatePassword(String var1, String var2, String var3);

    boolean lockUser(int[] var1);

    boolean clearUser(int[] var1);

    String userImport(String fileName, MultipartFile file) throws Exception;

    List<User> getUserList();

}
