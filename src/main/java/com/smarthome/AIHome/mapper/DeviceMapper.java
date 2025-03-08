package com.smarthome.AIHome.mapper;

import com.smarthome.AIHome.entity.AirConditioner;
import com.smarthome.AIHome.entity.Device;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeviceMapper {
    //新增设备
    @Insert("""
            insert into device(model, name, type, place, status, owner_id)
            values( #{model}, #{name}, #{type}, #{place}, #{status}, #{ownerId})
            """)
    @Results({
            @Result(property = "_id", column = "device_id"),
            @Result(property = "ownerId", column = "owner_id")
    })

    void insert(Device device);
    //查看设备
    @Select("""
            select device_id, model, name, type, place, status
            from device where owner_id = #{id}
            """)
    @Results({
            @Result(property = "_id", column = "device_id"),
            @Result(property = "id", column = "owner_id")
    })
    List<Device> selectAll(int id);
    //删除设备,通过设备id
    @Delete("""
            delete from device where
            device_id = #{deviceId}
            """)
    @Result(property = "_id", column = "device_id")
    int deleteById(int _id);
    //删除某用户所有设备
    @Delete("""
            delete from device where
            owner_id = #{userId}
            """)
    @Result(property = "userId", column = "owner_id")
    int deleteAll(int userId);
    int updateDevice(Device device);
    int updateAC(AirConditioner airConditioner);
    Device selectById(int _id, Device.Type type);


    @Select("""
            select device_id, place
            from device where owner_id = #{id} and type = #{type}
            """)
    @Results({
            @Result(property = "_id", column = "device_id"),
            @Result(property = "id", column = "owner_id")
    })
    List<Device> selectDeviceById(int id, Device.Type type);
}
