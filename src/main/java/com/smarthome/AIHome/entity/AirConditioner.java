package com.smarthome.AIHome.entity;

public class AirConditioner extends Device{
    private int temperature;
    private FanSpeed fanSpeed;
    private Mode mode;

    public AirConditioner(String deviceId, String model, String name, Type type, String place, Integer ownerId) {
        super(deviceId, model, name, type, place, ownerId);
        this.temperature = 26;
        this.fanSpeed = FanSpeed.MEDIUM;
        this.mode = Mode.AUTO;
    }

    public AirConditioner() {
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public FanSpeed getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(FanSpeed fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void turnOn() {

    }

    @Override
    public void turnOff() {

    }

    @Override
    public void executeCommand(String command, Object... params) {

    }
    public enum FanSpeed{
        LOW,
        MEDIUM,
        HIGH;
    }
    public enum Mode{
        COOL,
        HEAT,
        AUTO,
        FAN_ONLY;
    }
}
