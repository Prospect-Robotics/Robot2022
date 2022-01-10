package com.team2813.lib.config;

import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;

import java.util.ArrayList;
import java.util.List;

public class TalonConfig extends MotorConfig {

    //Required
    private int deviceNumber;
    private MotorControllerType motorControllerType;
    private Inverted inverted;

    private int peakCurrentDuration = 0;
    private int peakCurrentLimit = 0;
    private boolean enableVoltageCompensation = true;
    private int compSaturationVoltage = 12;
    private int continuousCurrentLimitAmps = 40;
    private int motionAcceleration = 0;
    private int motionCruiseVelocity = 0;
    private double closedLoopRampRate = 0;
    private double openLoopRampRate = 0;
    private boolean invertSensorPhase = true;
    private List<FollowerConfig> followers = new ArrayList<>();
    private List<PIDControllerConfig> pidControllers = new ArrayList<>();
    private List<SoftLimitConfig> softLimits = new ArrayList<>();
    private List<LimitSwitchConfig> limitSwitches = new ArrayList<>();
    //    private StatusFrameEnhanced statusFrame; // cannot serialize into PeriodicFrame (see getStatusFrame)
    private int statusFramePeriod = 5;
    private VelocityMeasPeriod velocityMeasurementPeriod;

    @Override
    public int getDeviceNumber() {
        return deviceNumber;
    }

    @Override
    public void setDeviceNumber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public MotorControllerType getMotorControllerType() {
        return motorControllerType;
    }

    public void setMotorControllerType(MotorControllerType motorControllerType) {
        this.motorControllerType = motorControllerType;
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

    public double getClosedLoopRampRate() {
        return closedLoopRampRate;
    }

    public void setClosedLoopRampRate(double closedLoopRampRate) {
        this.closedLoopRampRate = closedLoopRampRate;
    }

    public double getOpenLoopRampRate() {
        return openLoopRampRate;
    }

    public void setOpenLoopRampRate(double openLoopRampRate) {
        this.openLoopRampRate = openLoopRampRate;
    }

    public boolean isInvertSensorPhase() {
        return invertSensorPhase;
    }

    public void setInvertSensorPhase(boolean invertSensorPhase) {
        this.invertSensorPhase = invertSensorPhase;
    }

    // TODO: 1/18/2020 Status Frame Commented out
//    public StatusFrameEnhanced getStatusFrame() {
//        return statusFrame;
//    }
//
//    public void setStatusFrame(StatusFrameEnhanced statusFrame) {
//        this.statusFrame = statusFrame;
//    }

    public int getStatusFramePeriod() {
        return statusFramePeriod;
    }

    public void setStatusFramePeriod(int statusFramePeriod) {
        this.statusFramePeriod = statusFramePeriod;
    }

    public VelocityMeasPeriod getVelocityMeasurementPeriod() {
        return velocityMeasurementPeriod;
    }

    public void setVelocityMeasurementPeriod(VelocityMeasPeriod velocityMeasurementPeriod) {
        this.velocityMeasurementPeriod = velocityMeasurementPeriod;
    }


    public List<PIDControllerConfig> getPidControllers() {
        return pidControllers;
    }

    public List<LimitSwitchConfig> getLimitSwitches() {
        return limitSwitches;
    }

    public List<SoftLimitConfig> getSoftLimits() {
        return softLimits;
    }

    public Inverted getInverted() {
        return inverted;
    }

    public List<FollowerConfig> getFollowers() {
        return followers;
    }
}
