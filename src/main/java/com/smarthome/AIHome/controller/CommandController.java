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
    @PostMapping("/command")
    public ApiResponse<Command> processCommand(@RequestBody Command command) {
        ApiResponse<Command> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("");
        apiResponse.setData();
        apiResponse.setCode(200);
        return apiResponse;
    }
}
