package com.smarthome.AIHome.controller;

import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SmsController {
    @Autowired
    private  SmsService smsService;

    @CrossOrigin(origins = "*")
    @PostMapping("/send")
    public ApiResponse<Void> sendSms(@RequestParam String phoneNumber) {
        boolean success = smsService.sendSms(phoneNumber);
        ApiResponse<Void> apiResponse= new ApiResponse<>();
        if (success){
            apiResponse.setCode(200);
            apiResponse.setMessage("短信验证码已发送");
        }else {
            apiResponse.setMessage("发送失败");
            apiResponse.setCode(500);
        }
        return apiResponse;
    }
}
