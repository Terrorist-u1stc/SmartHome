package com.smarthome.AIHome.entity;

public  class Device {
    private int _id;
    private String model;
    private String name;
    private Type type;
    private String place;
    private DeviceStatus status;
    private Integer ownerId;
    private byte[] pic;

    public Device(int _id, String model, String name,Type type, String place, Integer ownerId) {
        this._id = _id;
        this.model = model;
        this.name = name;
        this.status = DeviceStatus.OFF;
        this.type = type;
        this.place = place;
        this.ownerId = ownerId;
    }

    public Device() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
    //public abstract void turnOn();

    // 关闭设备
    //public abstract void turnOff();

    // 执行具体设备的控制命令
    //public abstract void executeCommand(String command, Object... params);
    public enum Type{
        AC,//空调
        FAN,
        LIGHT
    }
}
