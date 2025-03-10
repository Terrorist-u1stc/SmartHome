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
//        user1.setProfilePhoto("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");

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
        apiResponse.setData(user1);
        return apiResponse;
    }
    public ApiResponse<Void> reset(String newPassword, int userId) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        {
            int rowsAffected = userMapper.updatePassword(newPassword, userId);
            if (rowsAffected > 0) {
                apiResponse.setMessage("密码更改成功");
                apiResponse.setCode(200);
            } else {
                apiResponse.setCode(500);
                apiResponse.setMessage("密码更改失败");
            }
            return apiResponse;
        }//catch (Exception e){
         //   apiResponse.setCode(500);
         //   apiResponse.setMessage("密码更改失败，未知错误");
         //   return apiResponse;
        //}
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
    public ApiResponse<Void> resetUserName(String newUserName, int userId){
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        if(userMapper.resetUserName(newUserName, userId) > 0){
            apiResponse.setCode(200);
            apiResponse.setMessage("修改用户名成功");
        }else {
            apiResponse.setCode(400);
            apiResponse.setMessage("修改失败");
        }
        return apiResponse;
    }
    public ApiResponse<Void> uploadAvatar(String profilePhoto, int userId){
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        try{
            int rows = userMapper.updateAvatar2(profilePhoto, userId);
            if(rows > 0){
                apiResponse.setCode(200);
                apiResponse.setMessage("头像上传成功");
            }else {
                apiResponse.setCode(500);
                apiResponse.setMessage("头像上传失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResponse.setCode(500);
            apiResponse.setMessage("服务器内部错误");

        }
        return apiResponse;
    }
}
