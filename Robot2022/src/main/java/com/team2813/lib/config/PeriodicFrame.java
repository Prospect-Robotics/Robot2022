package com.team2813.lib.config;

import com.revrobotics.CANSparkMaxLowLevel;

public enum PeriodicFrame {
    STATUS_0, STATUS_1, STATUS_2;

    public CANSparkMaxLowLevel.PeriodicFrame getValue() {
        switch(this) {
            case STATUS_0:
                return CANSparkMaxLowLevel.PeriodicFrame.kStatus0;
            case STATUS_1:
                return CANSparkMaxLowLevel.PeriodicFrame.kStatus1;
            case STATUS_2:
                return CANSparkMaxLowLevel.PeriodicFrame.kStatus2;
        }
        return null;
    }
}
