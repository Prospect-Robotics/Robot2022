package com.team2813.lib.motors;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class TalonFXWrapper extends TalonWrapper<TalonFX> {

    /**
     * Constructor for TalonFX object
     *
     * @param deviceNumber CAN Device ID of Device
     */
    public TalonFXWrapper(int deviceNumber, String subsystem) {
        controller = new TalonFX(deviceNumber);
        this.subsystemName = subsystem;
        System.out.println("Initializing Talon FX with ID " + deviceNumber);
    }

    public Object setCurrentLimit(int amps) {

        SupplyCurrentLimitConfiguration currentLimitConfiguration =
                new SupplyCurrentLimitConfiguration(true, amps, amps, 0.25);
        return controller.configSupplyCurrentLimit(currentLimitConfiguration);
//		return null;
    }

    @Override
    public ErrorCode setPeakCurrentLimit(double amps) {
//		StatorCurrentLimitConfiguration currentLimitConfiguration =
//				  new StatorCurrentLimitConfiguration(true, amps, amps, 0.25);
//		return controller.configStatorCurrentLimit(currentLimitConfiguration);
        return null;
    }

    public ErrorCode configEncoder(TalonFXFeedbackDevice encoder, PIDProfile profile, int timeoutMs) {
        return controller.configSelectedFeedbackSensor(encoder, profile.id, timeoutMs);
    }
}
