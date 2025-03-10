package com.smarthome.AIHome.service;

import com.smarthome.AIHome.entity.AIResponse;
import com.smarthome.AIHome.entity.ApiResponse;
import com.smarthome.AIHome.entity.Command;
import com.smarthome.AIHome.entity.Device;
import com.smarthome.AIHome.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service

public class AIService {
    @Autowired
    private DeviceMapper deviceMapper;

    private final RestTemplate restTemplate;
    private Command command;
    private int userId;
    private Integer _id = null;


    public AIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public AIResponse getAIResponse(Command command) {
        String aiApiUrl = "https://cb4b-113-54-242-186.ngrok-free.app/process";

        return restTemplate.postForObject(aiApiUrl, command, AIResponse.class);
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public ApiResponse<Command> generateCommand() {
        AIResponse aiResponse = this.getAIResponse(command);
        String type = aiResponse.getDevice();
        Device.Type deviceType = null;
        ApiResponse<Command> apiResponse = new ApiResponse<>();
        Command command1 = new Command();

        switch (type) {
            case "空调":
                deviceType = Device.Type.AC;
                break;
            case "风扇":
                deviceType = Device.Type.FAN;
                break;
            case "灯":
                deviceType = Device.Type.LIGHT;
                break;
            default:
                // 如果设备名不在预定义的范围内，返回一个默认值或者抛出异常
                System.out.println("未知设备类型: " + type);
                break;
        }

        List<Device> devices = deviceMapper.selectDeviceById(userId, deviceType);
        if (devices == null || devices.isEmpty()) {
            apiResponse.setMessage("第一步，未找到对应的设备。");
            apiResponse.setCode(404);
            return apiResponse;
        }
        for (Device d : devices) {
            if (d.getPlace().equals(aiResponse.getPlace())) {
                _id = d.get_id();
                break;
            }
        }
        if(_id == null){
            apiResponse.setMessage("第二步，未找到对应的设备。");
            apiResponse.setCode(404);
            return apiResponse;
        }

        String content = "设备ID: " + _id + ", 意图: " + aiResponse.getIntent() + ", value:" +
                aiResponse.getValue();
        command1.setText(content);


        apiResponse.setData(command1);
        apiResponse.setMessage("命令生成成功！");
        apiResponse.setCode(200);

        return apiResponse;
    }
}


