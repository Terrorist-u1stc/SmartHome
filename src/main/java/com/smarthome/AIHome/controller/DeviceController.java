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
    @PostMapping("/add")
    public ApiResponse<Void> addDevice(@RequestBody Device device, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        device.setOwnerId(user.getUserId());
        return deviceService.addDevice(device);
    }
    //展示设备
    @GetMapping("/selectAll")
    public ApiResponse<List<Device>> selectAll(HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        return deviceService.selectAll(user);
    }
}
