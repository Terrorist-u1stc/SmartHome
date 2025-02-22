package com.smarthome.AIHome.controller;

import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.entity.User;
import com.smarthome.AIHome.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController

public class UserController {
    @Autowired
    UserService userService;
    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody User user){
        return userService.register(user);
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ApiResponse<User> login(@RequestBody User user, HttpSession session){
        ApiResponse<User> apiResponse = userService.login(user);
        if (apiResponse.getCode() == HttpStatus.OK.value()){
            session.setAttribute("currentUser", apiResponse.getData());
        }
        return apiResponse;
    }
    //用户登出
    @CrossOrigin(origins = "*")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session){
        session.removeAttribute("currentUser");
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("用户已成功注销");
        apiResponse.setCode(200);
        return apiResponse;
    }
    //通过旧密码重新设置密码
    @CrossOrigin(origins = "*")
    @PostMapping("/reset-password")
    public ApiResponse<Void> reset(@RequestParam String newPassword ,@RequestParam String oldPassword, HttpSession session){
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        User user = (User) session.getAttribute("currentUser");

        if (!oldPassword.equals(user.getPassword())){
            apiResponse.setCode(400);
            apiResponse.setMessage("密码错误");
            return apiResponse;
        }
        apiResponse = userService.reset(newPassword, user.getUserId());
        if(apiResponse.getCode() == 200){
            session.removeAttribute("currentUser");
        }
        return apiResponse;
    }
    //上传头像
    @CrossOrigin(origins = "*")
    @PostMapping("/upload/avatar")
    public ApiResponse<Void> uploadAvatar(@RequestParam("image") MultipartFile file, HttpSession session){
        String path = "D:\\images\\avatars";
        User user = (User) session.getAttribute("currentUser");
        try{
            byte[] bytes = file.getBytes();
            return userService.avatar(path, bytes, user.getUserId());
        }catch (IOException e){
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.setCode(500);
            apiResponse.setMessage("图片保存失败，发生 IO 错误：" + e.getMessage());
            return apiResponse;
        }
    }
    //上传头像，二进制数组
    @CrossOrigin(origins = "*")
    @PostMapping("/upload-avatar")
    public ApiResponse<Void> uploadAvatar2(@RequestBody byte[] profilePhoto, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return userService.uploadAvatar(profilePhoto, user.getUserId());
    }
    //显示头像
    @CrossOrigin(origins = "*")
    @GetMapping("/avatar")
    public ApiResponse<String> avatar(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return userService.avatar(user.getUserId());
    }
    //更改用户名
    @CrossOrigin(origins = "*")
    @PostMapping("/reset-userName")
    public ApiResponse<Void> resetUserName(@RequestParam String newUserName, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return userService.resetUserName(newUserName, user.getUserId());
    }
    //修改某设备的信息

}
