package com.smarthome.AIHome.controller;
import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.entity.Command;
import com.smarthome.AIHome.entity.User;
import com.smarthome.AIHome.service.AIService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {
    @Autowired
    AIService aiService;

    @CrossOrigin(origins = "*")
    @PostMapping("/command")
    public ApiResponse<Command> processCommand(@RequestBody Command command, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        aiService.setCommand(command);
        aiService.setUserId(user.getUserId());
        return aiService.generateCommand();
    }
}
