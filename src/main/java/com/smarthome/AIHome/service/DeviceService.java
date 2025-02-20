package com.smarthome.AIHome.service;

import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.entity.Device;
import com.smarthome.AIHome.entity.User;
import com.smarthome.AIHome.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;
    //展示设备
    public ApiResponse<List<Device>> selectAll(User user){
        int id = user.getUserId();
        ApiResponse<List<Device>> apiResponse = new ApiResponse<>();
        List<Device> devices = deviceMapper.selectAll(id);
        if (devices == null){
            apiResponse.setData(devices);
            apiResponse.setMessage("未查询到任何设备");
            apiResponse.setCode(200);
            return apiResponse;
        }
        apiResponse.setData(devices);
        apiResponse.setMessage("查询成功");
        apiResponse.setCode(200);
        return apiResponse;
    }
    //新增设备
    public ApiResponse<Void> addDevice(Device device){
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        if(device == null){
            apiResponse.setCode(400);
            apiResponse.setMessage("未填写设备信息");
            return apiResponse;
        }
        deviceMapper.insert(device);
        apiResponse.setMessage("设备添加成功");
        apiResponse.setCode(200);
        return apiResponse;
    }
}
