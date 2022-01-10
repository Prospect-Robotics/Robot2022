package com.team2813.lib.config;

import java.util.ArrayList;
import java.util.List;

public class SparkConfig extends MotorConfig {
    private int deviceNumber;
    private MotorType type;
    private Inverted inverted;

    private int peakCurrentDuration = 0;
    private int peakCurrentLimit = 0;
    private boolean enableVoltageCompensation = true;
    private int compSaturationVoltage = 12;
    private int continuousCurrentLimitAmps = 40;
    private int motionAcceleration = 0;
    private int motionCruiseVelocity = 0;
    private int closedLoopRampRate = 0;
    private int openLoopRampRate = 0;
    private boolean invertSensorPhase = true;
    private PeriodicFrame statusFrame = PeriodicFrame.STATUS_2; // cannot serialize into PeriodicFrame (see getStatusFrame)
    private int statusFramePeriod = 5;
    private List<FollowerConfig> followers = new ArrayList<>();
    private List<PIDControllerConfig> pidControllers = new ArrayList<>();

    public int getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public MotorType getType() {
        return type;
    }

    public void setType(MotorType type) {
        this.type = type;
    }

    public int getPeakCurrentDuration() {
        return peakCurrentDuration;
    }

    public void setPeakCurrentDuration(int peakCurrentDuration) {
        this.peakCurrentDuration = peakCurrentDuration;
    }

    public int getPeakCurrentLimit() {
        return peakCurrentLimit;
    }

    public void setPeakCurrentLimit(int peakCurrentLimit) {
        this.peakCurrentLimit = peakCurrentLimit;
    }

    public boolean isEnableVoltageCompensation() {
        return enableVoltageCompensation;
    }

    public void setEnableVoltageCompensation(boolean enableVoltageCompensation) {
        this.enableVoltageCompensation = enableVoltageCompensation;
    }

    public int getCompSaturationVoltage() {
        return compSaturationVoltage;
    }

    public void setCompSaturationVoltage(int compSaturationVoltage) {
        this.compSaturationVoltage = compSaturationVoltage;
    }

    public int getContinuousCurrentLimitAmps() {
        return continuousCurrentLimitAmps;
    }

    public void setContinuousCurrentLimitAmps(int continuousCurrentLimitAmps) {
        this.continuousCurrentLimitAmps = continuousCurrentLimitAmps;
    }

    public int getMotionAcceleration() {
        return motionAcceleration;
    }

    public void setMotionAcceleration(int motionAcceleration) {
        this.motionAcceleration = motionAcceleration;
    }

    public int getMotionCruiseVelocity() {
        return motionCruiseVelocity;
    }

    public void setMotionCruiseVelocity(int motionCruiseVelocity) {
        this.motionCruiseVelocity = motionCruiseVelocity;
    }

    public int getClosedLoopRampRate() {
        return closedLoopRampRate;
    }

    public void setClosedLoopRampRate(int closedLoopRampRate) {
        this.closedLoopRampRate = closedLoopRampRate;
    }

    public int getOpenLoopRampRate() {
        return openLoopRampRate;
    }

    public void setOpenLoopRampRate(int openLoopRampRate) {
        this.openLoopRampRate = openLoopRampRate;
    }

    public boolean isInvertSensorPhase() {
        return invertSensorPhase;
    }

    public void setInvertSensorPhase(boolean invertSensorPhase) {
        this.invertSensorPhase = invertSensorPhase;
    }

    public PeriodicFrame getStatusFrame() {
        return this.statusFrame;
    }

    public void setStatusFrame(PeriodicFrame statusFrame) {
        this.statusFrame = statusFrame;
    }

    public int getStatusFramePeriod() {
        return statusFramePeriod;
    }

    public void setStatusFramePeriod(int statusFramePeriod) {
        this.statusFramePeriod = statusFramePeriod;
    }

    public List<FollowerConfig> getFollowers() {
        return followers;
    }

    public void setFollowers(List<FollowerConfig> followers) {
        this.followers = followers;
    }

    public Inverted getInverted() {
        return inverted;
    }

    public void setInverted(Inverted inverted) {
        this.inverted = inverted;
    }

    public List<PIDControllerConfig> getPidControllers() {
        return pidControllers;
    }

    public void setPidControllers(List<PIDControllerConfig> pidControllers) {
        this.pidControllers = pidControllers;
    }
}
