package com.smarthome.AIHome.mapper;

import com.smarthome.AIHome.entity.Device;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeviceMapper {
    //新增设备
    @Insert("""
            insert into device(device_id, model, name, type, place, status, owner_id)
            values(#{deviceId}, #{model}, #{name}, #{type}, #{place}, #{status}, #{ownerId})
            """)
    @Results({
            @Result(property = "deviceId", column = "device_id"),
            @Result(property = "ownerId", column = "owner_id")
    })

    void insert(Device device);
    //查看设备
    @Select("""
            select device_id, model, name, type, place, status
            from device where owner_id = #{id}
            """)
    @Results({
            @Result(property = "deviceId", column = "device_id"),
            @Result(property = "id", column = "owner_id")
    })
    List<Device> selectAll(int id);
}
