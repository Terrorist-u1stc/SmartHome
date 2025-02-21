package com.smarthome.AIHome.controller;

import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.entity.Device;
import com.smarthome.AIHome.entity.User;
import com.smarthome.AIHome.service.DeviceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    //添加设备
    @CrossOrigin(origins = "*")
    @PostMapping("/add")
    public ApiResponse<Void> addDevice(@RequestBody Device device, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        device.setOwnerId(user.getUserId());
        return deviceService.addDevice(device);
    }
    //展示设备
    @CrossOrigin(origins = "*")
    @GetMapping("/queryAll")
    public ApiResponse<List<Device>> selectAll(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return deviceService.selectAll(user);
    }
    //删除设备，通过id
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteById")
    public ApiResponse<Void> deleteById(@RequestParam String deviceId){
        return deviceService.deleteDevice(deviceId);
    }
    //删除某个用户的全部设备
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteById")
    public ApiResponse<Void> deleteAll(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return deviceService.deleteAll(user.getUserId());
    }
    @CrossOrigin(origins = "*")
    @PutMapping("/update-device")
    public ApiResponse<Void> updateDevice(@RequestBody Device device){
        return deviceService.updateDevice(device.getDeviceId());
    }
    @CrossOrigin(origins = "*")
    @PutMapping("/update-device")
    public ApiResponse<Void> updateAC(@RequestBody Device device){
        return deviceService.updateAC(device.getDeviceId());
    }
}
