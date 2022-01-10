package com.team2813.lib.motors;

import com.team2813.lib.motors.interfaces.ControlMode;
import com.team2813.lib.motors.interfaces.LimitDirection;

public interface Motor {
    // motor control
    public Object set(ControlMode controlMode, double demand);
    public Object set(ControlMode controlMode, double demand, double feedForward);

    // encoder position
    public Object setEncoderPosition(double position);
    public double getEncoderPosition();
    public double getVelocity();

    // config
    public Object setFactoryDefaults();
    public Object setPeakCurrentLimit(double amps);
    public void enableVoltageCompensation();
    public Object setSoftLimit(LimitDirection direction, double limit, boolean enable);
    public Object setSoftLimit(LimitDirection direction, double limit);

    // pid config
    public void setPIDF(int slot, double p, double i, double d, double f);
    public void setPID(int slot, double p, double i, double d);
    public void setPIDF(double p, double i, double d, double f);
    public void setPID(double p, double i, double d);
    public Object setMotionMagicVelocity(double velocity);
    public Object setMotionMagicAcceleration(double acceleration);

    public String getSubsystemName();
}
