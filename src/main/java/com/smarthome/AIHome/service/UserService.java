package com.smarthome.AIHome.service;

import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.entity.User;
import com.smarthome.AIHome.mapper.UserMapper;
import com.smarthome.AIHome.utils.ImageUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    //注册用户
    public ApiResponse<Void> register(User user){
        ApiResponse<Void> apiResponse = new ApiResponse<>();

        if(user.getUserName() == null || user.getUserName().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()){
            apiResponse.setCode(400);
            apiResponse.setMessage("用户名和密码不能为空");
            return apiResponse;
        }

        if(userMapper.selectByName(user.getUserName()) != null){
            apiResponse.setCode(400);
            apiResponse.setMessage("用户名已存在");
            return apiResponse;
        }

        try{
            userMapper.insert(user);
            apiResponse.setMessage("注册成功");
            apiResponse.setCode(HttpStatus.OK.value());
            return apiResponse;
        }catch (PersistenceException e){
            apiResponse.setCode(409);
            apiResponse.setMessage("注册失败");
            return apiResponse;
        }
    }
    public ApiResponse<User> login(User user){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        User user1 = userMapper.selectByName(user.getUserName());

        if(user.getUserName() == null || user.getUserName().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()){
            apiResponse.setCode(400);
            apiResponse.setMessage("用户名和密码不能为空");
            apiResponse.setData(user);
            return apiResponse;
        }

        if (user1 == null){
            apiResponse.setMessage("用户不存在");
            apiResponse.setCode(404);
            apiResponse.setData(user);
            return apiResponse;
        }
        if(!user.getPassword().equals(user1.getPassword())){
            apiResponse.setMessage("用户密码错误");
            apiResponse.setCode(401);
            apiResponse.setData(user);
            return apiResponse;
        }
        apiResponse.setCode(200);
        apiResponse.setMessage("登录成功");
        apiResponse.setData(user);
        return apiResponse;
    }
    public ApiResponse<Void> reset(String newPassword, int userId) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        try {
            int rowsAffected = userMapper.updatePassword(newPassword, userId);
            if (rowsAffected > 0) {
                apiResponse.setMessage("密码更改成功");
                apiResponse.setCode(200);
            } else {
                apiResponse.setCode(500);
                apiResponse.setMessage("密码更改失败");
            }
            return apiResponse;
        }catch (Exception e){
            apiResponse.setCode(500);
            apiResponse.setMessage("密码更改失败");
            return apiResponse;
        }
    }

    public ApiResponse<Void> avatar(String path, byte[] bytes, int userId){
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        ImageUtils utils = new ImageUtils();
        utils.setPath(path);
        try{
            String s = utils.saveImage(bytes);
            int rowsAffected = userMapper.updateAvatar(s, userId);
            if(rowsAffected > 0){
                apiResponse.setCode(200);
                apiResponse.setMessage("头像更改成功");
            }else {
                apiResponse.setCode(400);
                apiResponse.setMessage("头像更改失败");
            }
            return apiResponse;

        }catch (IOException e){
            apiResponse.setCode(500);
            apiResponse.setMessage("图片保存失败");
            return apiResponse;
        }catch (Exception e){
            apiResponse.setCode(500);
            apiResponse.setMessage("服务器内部错误");
            return apiResponse;
        }
    }
    public ApiResponse<String> avatar(int userId){
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData(userMapper.selectAvatar(userId));
        apiResponse.setCode(200);
        apiResponse.setMessage("头像查询成功");
        return apiResponse;
    }
}
