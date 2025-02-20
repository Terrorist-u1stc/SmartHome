package com.smarthome.AIHome.entity;

public enum DeviceStatus {
    ON,
    OFF,
    STANDBY,
    ERROR;
    public String getStatus() {
        switch (this) {
            case ON:
                return "设备已开启";
            case OFF:
                return "设备已关闭";
            case STANDBY:
                return "设备处于待机状态";
            case ERROR:
                return "设备发生错误";
            default:
                return "未知状态";
        }
    }
}
