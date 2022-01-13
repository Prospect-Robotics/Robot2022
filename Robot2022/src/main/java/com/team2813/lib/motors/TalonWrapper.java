package com.team2813.lib.motors;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.team2813.frc2022.util.Units2813;
import com.team2813.lib.ctre.CTREException;
import com.team2813.lib.ctre.TimeoutMode;
import com.team2813.lib.motors.interfaces.ControlMode;
import com.team2813.lib.motors.interfaces.LimitDirection;

import java.util.ArrayList;
import java.util.List;

public abstract class TalonWrapper<Controller extends BaseTalon> implements Motor {
    private TimeoutMode timeoutMode = TimeoutMode.CONSTRUCTING;
    public String subsystemName;

    public Controller controller;
    public List<TalonWrapper> slaves = new ArrayList<>();

    @Override
    public Object set(ControlMode controlMode, double demand) {
        this.set(controlMode, demand, 0);
        return null;
    }

    @Override
    public Object set(ControlMode controlMode, double demand, double feedForward) {
        switch (controlMode){
            case VELOCITY:
                demand = Units2813.motorRevsToTicks(demand / 60 / 10);
                break;
            case MOTION_MAGIC:
                demand = Units2813.motorRevsToTicks(demand);
                break;
        }
        controller.set(controlMode.getTalonMode(), demand, DemandType.ArbitraryFeedForward, feedForward);
        return null;
    }

    @Override
    public ErrorCode setEncoderPosition(double position) {
        return controller.setSelectedSensorPosition(0);
    }

    @Override
    public double getEncoderPosition() { // returns revolutions
        return Units2813.ticksToMotorRevs(controller.getSelectedSensorPosition());
    }

    @Override
    public ErrorCode setFactoryDefaults() {
        return controller.configFactoryDefault();
    }

    @Override
    public abstract ErrorCode setPeakCurrentLimit(double amps);

    @Override
    public void enableVoltageCompensation() {
        controller.enableVoltageCompensation(true);
    }

    public ErrorCode setStatusFramePeriod(StatusFrame statusFrame, int period) {
        return controller.setStatusFramePeriod(statusFrame, period);
    }

    @Override
    public ErrorCode setSoftLimit(LimitDirection direction, double limit, boolean enable) {
        try {
            if (direction == LimitDirection.FORWARD) {
                CTREException.throwIfNotOk(controller.configForwardSoftLimitThreshold((int) limit));
                CTREException.throwIfNotOk(controller.configForwardSoftLimitEnable(enable));
            } else {
                CTREException.throwIfNotOk(controller.configReverseSoftLimitThreshold((int) limit));
                CTREException.throwIfNotOk(controller.configReverseSoftLimitEnable(enable));
            }
            return ErrorCode.OK;
        } catch (CTREException e) {
            return e.getErrorCode();
        }
    }

    @Override
    public ErrorCode setSoftLimit(LimitDirection direction, double limit) {
        return setSoftLimit(direction, limit, true);
    }

    @Override
    public void setPIDF(int slot, double p, double i, double d, double f) {
        controller.config_kP(slot, p);
        controller.config_kI(slot, i);
        controller.config_kD(slot, d);
        controller.config_kF(slot, f);
    }

    @Override
    public void setPID(int slot, double p, double i, double d) {
        setPIDF(slot, p, i, d, 0);
    }

    @Override
    public void setPIDF(double p, double i, double d, double f) {
        setPIDF(0, p, i, d, f);
    }

    @Override
    public void setPID(double p, double i, double d) {
        setPID(0, p, i, d);
    }

    @Override
    public ErrorCode setMotionMagicVelocity(double velocity) {
        return controller.configMotionCruiseVelocity((int) velocity);
    }

    @Override
    public ErrorCode setMotionMagicAcceleration(double acceleration) {
        return controller.configMotionAcceleration((int) acceleration);
    }

    @Override
    public String getSubsystemName() {
        return subsystemName;
    }

    public abstract Object setCurrentLimit(int limitAmps);

    public void enableLimitSwitches() {
        controller.overrideLimitSwitchesEnable(true);
    }

    public Object setClosedLoopRamp(double rate) {
        return controller.configClosedloopRamp(rate);
    }

    /**
     * Reset selected feedback sensor (encoder) on a limit
     *
     * @param direction
     * @param clearOnLimit
     */
    public void setClearPositionOnLimit(LimitDirection direction, boolean clearOnLimit) {
        if (direction == LimitDirection.FORWARD) setClearPositionOnLimitF(clearOnLimit);
        else if (direction == LimitDirection.REVERSE) setClearPositionOnLimitR(clearOnLimit);
    }

    public void setClearPositionOnLimitF(boolean enable) {
        controller.configClearPositionOnLimitF(enable, timeoutMode.valueMs);
    }

    public void setClearPositionOnLimitR(boolean enable) {
        controller.configClearPositionOnLimitF(enable, timeoutMode.valueMs);
    }

    public void setLimitSwitchSource(LimitDirection direction, LimitSwitchSource type, LimitSwitchNormal normalOpenOrClose) {
        if (direction == LimitDirection.FORWARD) {
            controller.configForwardLimitSwitchSource(type, normalOpenOrClose);
        } else if (direction == LimitDirection.REVERSE) {
            controller.configReverseLimitSwitchSource(type, normalOpenOrClose);
        }
    }

    public void setNeutralMode(NeutralMode mode) {
        controller.setNeutralMode(mode);
    }

    public void setInverted(boolean inverted) {
        controller.setInverted(inverted);
    }

    public void setInverted(InvertType invertType) {
        controller.setInverted(invertType);
    }

    public void follow(TalonWrapper master) {
        controller.follow(master.controller);
    }

    public double getVelocity() { // returns in rpm
        return Units2813.ticksToMotorRevs(controller.getSelectedSensorVelocity()) * 10 * 60; // from ticks/100ms to rpm
    }

    public static double convertRPM(double rpm) {
        return rpm * (2048.0 / 60);
    }

    public enum PIDProfile {
        PRIMARY(0),
        SECONDARY(1);

        public final int id;

        private PIDProfile(int id) {
            this.id = id;
        }
    }
}
