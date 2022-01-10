package com.team2813.lib.config;

import com.ctre.phoenix.motorcontrol.InvertType;

public enum Inverted {
    NONINVERTED(InvertType.None),
    INVERTED(InvertType.InvertMotorOutput),
    FOLLOW_LEADER(InvertType.FollowMaster),
    OPPOSE_LEADER(InvertType.OpposeMaster);
    InvertType value;

    Inverted(InvertType value) {
        this.value = value;
    }
}
