package com.team2813.lib.motors.interfaces;

import com.revrobotics.CANSparkMax.ControlType;

public enum ControlMode {
    DUTY_CYCLE(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, ControlType.kDutyCycle),
    VELOCITY(com.ctre.phoenix.motorcontrol.ControlMode.Velocity, ControlType.kVelocity),
    MOTION_MAGIC(com.ctre.phoenix.motorcontrol.ControlMode.MotionMagic, ControlType.kPosition);

    private com.ctre.phoenix.motorcontrol.ControlMode talonMode;
    private ControlType sparkMode;

    ControlMode(com.ctre.phoenix.motorcontrol.ControlMode talonMode, ControlType sparkMode) {
        this.talonMode = talonMode;
        this.sparkMode = sparkMode;
    }

    public com.ctre.phoenix.motorcontrol.ControlMode getTalonMode() {
        return talonMode;
    }

    public ControlType getSparkMode() {
        return sparkMode;
    }
}
