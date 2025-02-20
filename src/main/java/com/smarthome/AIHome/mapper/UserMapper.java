package com.smarthome.AIHome.mapper;

import com.smarthome.AIHome.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper

public interface UserMapper {
    @Insert("""
            insert into smart_user(user_name, password, phone_number)
            values(#{userName}, #{password}, #{phoneNumber})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNumber", column = "phone_number")
    })
    void insert(User user);
    @Select("""
            select user_name, password
            from smart_user where
            user_name = #{userName}
            """)
    @Results({
            @Result(property = "userName", column = "user_name")
    })
    User selectByName(String userName);
    @Update("""
            update smart_user set password = #{newPassword}
            where user_id = userId
            """)
    @Results({
            @Result(property = "newPassword", column = "password"),
            @Result(property = "userId", column = "user_id")
    })
    int updatePassword(String newPassword, int userId);
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
    String selectAvatar(int userId);
}
