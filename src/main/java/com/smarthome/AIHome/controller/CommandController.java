package com.smarthome.AIHome.controller;
import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.entity.Command;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {
    @CrossOrigin(origins = "*")
    @PostMapping
    public ApiResponse<Void> processCommand(@RequestBody Command command) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(command.getCommand());
        apiResponse.setCode(200);
        return apiResponse;
    }
}
