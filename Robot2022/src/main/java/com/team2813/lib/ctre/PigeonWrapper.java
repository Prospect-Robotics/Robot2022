package com.team2813.lib.ctre;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.team2813.lib.motors.TalonWrapper;

public class PigeonWrapper extends PigeonIMU {
    String subsystemName;
    double[] ypr = new double[3];

    public PigeonWrapper(int deviceNumber, String subsystemName) {
        super(deviceNumber);
        this.subsystemName = subsystemName;
    }

    public PigeonWrapper(TalonWrapper talon) {
        super((TalonSRX) talon.controller); // this could throw castexception
        subsystemName = talon.subsystemName;
    }

    public double getHeading() {
        getYawPitchRoll(ypr);
        return ypr[0];
    }

    public void setHeading(double angle) {
        setYaw(angle);
        setAccumZAngle(0);
    }
}
