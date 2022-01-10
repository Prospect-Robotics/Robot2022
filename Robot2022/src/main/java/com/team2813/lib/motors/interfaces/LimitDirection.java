package com.team2813.lib.motors.interfaces;

import com.revrobotics.CANSparkMax;

public enum LimitDirection {
    FORWARD(CANSparkMax.SoftLimitDirection.kForward),
    REVERSE(CANSparkMax.SoftLimitDirection.kReverse);

    private CANSparkMax.SoftLimitDirection sparkMode;

    LimitDirection(CANSparkMax.SoftLimitDirection sparkMode) {
        this.sparkMode = sparkMode;
    }

    public CANSparkMax.SoftLimitDirection getSparkMode() {
        return sparkMode;
    }
}
