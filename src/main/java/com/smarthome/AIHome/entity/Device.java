package com.smarthome.AIHome.entity;

public  class Device {
    private String deviceId;
    private String model;
    private String name;
    private Type type;
    private String place;
    private DeviceStatus status;
    private Integer ownerId;

    public Device(String deviceId, String model, String name,Type type, String place, Integer ownerId) {
        this.deviceId = deviceId;
        this.model = model;
        this.name = name;
        this.status = DeviceStatus.OFF;
        this.type = type;
        this.place = place;
        this.ownerId = ownerId;
    }

    public Device() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    //public abstract void turnOn();

    // 关闭设备
    //public abstract void turnOff();

    // 执行具体设备的控制命令
    //public abstract void executeCommand(String command, Object... params);
    public enum Type{
        AC;//空调
    }
}
