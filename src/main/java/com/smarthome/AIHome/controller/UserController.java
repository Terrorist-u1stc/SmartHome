package com.smarthome.AIHome.controller;

import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.entity.User;
import com.smarthome.AIHome.service.SmsService;
import com.smarthome.AIHome.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController

public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private SmsService smsService;

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
    @PostMapping("/reset_password")
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
    @PostMapping("/upload_avatar")
    public ApiResponse<Void> uploadAvatar2(@RequestBody User user , HttpSession session){
        User user1 = (User) session.getAttribute("currentUser");
        return userService.uploadAvatar(user.getProfilePhoto(), user1.getUserId());
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
    public ApiResponse<Void> resetUserName(@RequestBody User user, HttpSession session){
        User user1 = (User) session.getAttribute("currentUser");
        return userService.resetUserName(user.getUserName(), user.getUserId());
    }


    //通过短信验证码登录
    @CrossOrigin(origins = "*")
    @PostMapping("/verify")
    public ApiResponse<User> verifyCode(@RequestParam String phoneNumber, @RequestParam String code, HttpSession session) {
        ApiResponse<User> apiResponse;
        boolean isValid = smsService.verifyCode(phoneNumber, code);
        if(isValid){
            apiResponse = userService.login(phoneNumber);
        }else {
            apiResponse =new ApiResponse<>();
            apiResponse.setCode(500);
            apiResponse.setMessage("验证失败");
        }
        if (apiResponse.getCode() == HttpStatus.OK.value()){
            session.setAttribute("currentUser", apiResponse.getData());
        }
        return apiResponse;
    }
    //通过短信验证码修改密码
    @CrossOrigin(origins = "*")
    @PostMapping("/verify2")
    public ApiResponse<Void> verifyCode(@RequestParam String phoneNumber, @RequestParam String code, @RequestParam String newPassword) {
        ApiResponse<Void> apiResponse;
        boolean isValid = smsService.verifyCode(phoneNumber, code);
        if(isValid){
            apiResponse = userService.reset(phoneNumber,newPassword);
        }else {
            apiResponse =new ApiResponse<>();
            apiResponse.setCode(500);
            apiResponse.setMessage("验证失败");
        }

        return apiResponse;
    }
}
