package com.smarthome.AIHome.mapper;

import com.smarthome.AIHome.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

@Mapper

public interface UserMapper {
    @Insert("""
            insert into smart_user(user_name, password, phone_number,profile_photo)
            values(#{userName}, #{password}, #{phoneNumber},#{profilePhoto})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "profilePhoto" ,column = "profile_photo")
    })
    void insert(User user);
    //用户登陆
    @Select("""
            select user_name, password, user_id, profile_photo, phone_number
            from smart_user where
            user_name = #{userName}
            """)
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "profilePhoto", column = "profile_photo"),
            @Result(property = "phoneNumber" , column = "phone_number")
    })
    User selectByName(String userName);
    @Select("""
            select user_name, password, user_id, profile_photo, phone_number
            from smart_user where
            phone_number = #{phoneNumber}
            """)
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "profilePhoto", column = "profile_photo"),
            @Result(property = "phoneNumber" , column = "phone_number")
    })
    User selectByPhone(String phoneNumber);

    @Update("""
            update smart_user set password = #{newPassword}
            where user_id = #{userId}
            """)
    @Results({
            @Result(property = "newPassword", column = "password"),
            @Result(property = "userId", column = "user_id")
    })
    int updatePassword(String newPassword, int userId);

    @Update("""
            update smart_user set password = #{newPassword}
            where phone_number = #{phoneNumber}
            """)
    @Results({
            @Result(property = "newPassword", column = "password"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "phoneNumber", column = "phone_number")
    })
    int updatePassword2(String newPassword, String phoneNumber);


    @Update("""
            update smart_user set 
            img_path = #{path}
            where user_id = #{userId}
            """)
    @Results({
            @Result(property = "path", column = "img_path"),
            @Result(property = "userId", column = "user_id")
    })
    int updateAvatar(String path, int userId);
    //显示头像
    @Select("""
            select img_path
            from smart_user
            where user_id = #{userId}
            """)
    @Result(property = "userId", column = "user_id")
    String selectAvatar(int userId);
    @Update("""
            update smart_user set
            user_name = #{newUserName}
            where user_id = #{userId}
            """)
    @Results({
            @Result(property = "newUserName", column = "user_name"),
            @Result(property = "userId", column = "user_id")
    })
    int resetUserName(String newUserName, int userId);
    @Update("""
            update smart_user
            set profile_photo = #{profilePhoto}
            WHERE user_id = #{userId}
            """)
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "profilePhoto", column = "profile_photo")
    })
    int updateAvatar2(String profilePhoto, int userId);
}
