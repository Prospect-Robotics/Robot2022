package com.team2813.lib.config;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.revrobotics.SparkMaxLimitSwitch.Type;
import com.team2813.lib.motors.interfaces.LimitDirection;

public class LimitSwitchConfig {

    LimitDirection direction;
    boolean clearOnLimit;
    Polarity polarity;


    enum Polarity {
        OPEN(LimitSwitchNormal.NormallyOpen, Type.kNormallyOpen),
        CLOSED(LimitSwitchNormal.NormallyClosed, Type.kNormallyClosed);

        LimitSwitchNormal ctre;
        Type sparkMax;

        Polarity(LimitSwitchNormal ctre, Type sparkMax) {
            this.ctre = ctre;
            this.sparkMax = sparkMax;
        }
    }
}
