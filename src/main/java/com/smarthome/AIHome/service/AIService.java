package com.smarthome.AIHome.service;

import com.smarthome.AIHome.entity.AIResponse;
import com.smarthome.AIHome.entity.Command;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class AIService {
    private final RestTemplate restTemplate;

    public AIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public AIResponse getAIResponse(Command command){
        String aiApiUrl = "";

        return restTemplate.postForObject(aiApiUrl, command, AIResponse.class);
    }
}
