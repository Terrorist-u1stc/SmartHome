package com.smarthome.AIHome.controller;

import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.entity.HelpTopic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelpController {
    @GetMapping("/help")
    public ApiResponse<String> help(@RequestParam HelpTopic topic){

        return new ApiResponse<String>();
    }
}
