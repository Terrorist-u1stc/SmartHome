package com.smarthome.AIHome.service;

import com.smarthome.AIHome.entity.AirConditioner;
import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.entity.Device;
import com.smarthome.AIHome.entity.User;
import com.smarthome.AIHome.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            apiResponse.setCode(404);
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
    public ApiResponse<Void> deleteDevice(int _id){
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        if(deviceMapper.deleteById(_id) > 0) {
            apiResponse.setCode(200);
            apiResponse.setMessage("已成功删除");
        }else {
            apiResponse.setCode(500);
            apiResponse.setMessage("删除失败");
        }
        return apiResponse;
    }
    public ApiResponse<Void> deleteAll(int userId){
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        if(deviceMapper.deleteAll(userId) > 0) {
            apiResponse.setCode(200);
            apiResponse.setMessage("已成功删除");
        }else {
            apiResponse.setCode(500);
            apiResponse.setMessage("删除失败");
        }
        return apiResponse;
    }
    public ApiResponse<Void> updateDevice(Device device){
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        if(deviceMapper.updateDevice(device) > 0){
            apiResponse.setCode(200);
            apiResponse.setMessage("修改成功");
        }else {
            apiResponse.setCode(500);
            apiResponse.setMessage("修改失败");
        }
        return apiResponse;
    }
    public ApiResponse<Void> updateAC(AirConditioner airConditioner){
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        if(deviceMapper.updateAC(airConditioner) > 0){
            apiResponse.setCode(200);
            apiResponse.setMessage("修改成功");
        }else {
            apiResponse.setCode(500);
            apiResponse.setMessage("修改失败");
        }
        return apiResponse;
    }
    public ApiResponse<Device> selectById(int _id, Device.Type type){
        ApiResponse<Device> apiResponse = new ApiResponse<>();
       try{
           Device device = deviceMapper.selectById(_id, type);
           if (device == null){
               apiResponse.setCode(404);
               apiResponse.setMessage("设备未找到");
               apiResponse.setData(null);
           }
           if(device instanceof AirConditioner){
               AirConditioner ac = (AirConditioner) device;
               apiResponse.setData(ac);
           }else {
               apiResponse.setData(device);
           }
           apiResponse.setCode(200);
           apiResponse.setMessage("设备查询成功");
       }catch (Exception e){
           apiResponse.setCode(500);
           apiResponse.setMessage("服务器内部错误");
           e.printStackTrace();
       }
       return apiResponse;
    }
}
