package com.team2813.lib.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MotorConfig {
    private int deviceNumber;
    private MotorType motorType;
    private String subsystemName;

    public int getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public MotorType getMotorType() {
        return motorType;
    }

    public void setMotorType(MotorType motorType) {
        this.motorType = motorType;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void getSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }
}
