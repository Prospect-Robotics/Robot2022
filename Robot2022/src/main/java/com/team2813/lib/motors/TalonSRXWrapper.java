package com.team2813.lib.motors;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonSRXWrapper extends TalonWrapper<TalonSRX> {

    /**
     * Constructor for TalonSRX object
     *
     * @param deviceNumber CAN Device ID of Device
     */
    public TalonSRXWrapper(int deviceNumber, String subsystem) {
        controller = new TalonSRX(deviceNumber);
        this.subsystemName = subsystem;
        System.out.println("Initializing Talon SRX with ID " + deviceNumber);
    }

    public Object setCurrentLimit(int limitAmps) {
        return controller.configContinuousCurrentLimit(limitAmps);
    }

    @Override
    public ErrorCode setPeakCurrentLimit(double amps) {
        return controller.configPeakCurrentLimit((int) amps);
    }
}
