package com.team2813.lib.config;

import com.ctre.phoenix.motorcontrol.InvertType;

public class FollowerConfig {
    private int id;

    private MotorType type;

    private MotorControllerType motorControllerType;
    private InvertType inverted = InvertType.FollowMaster;

    public void setMotorControllerType(MotorControllerType motorControllerType) {
        this.motorControllerType = motorControllerType;
    }



    public MotorControllerType getMotorControllerType() {
        return motorControllerType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MotorType getType() {
        return type;
    }

    public void setType(MotorType type) {
        this.type = type;
    }

    public InvertType getInverted() {
        return inverted;
    }

    public void setInverted(InvertType inverted) {
        this.inverted = inverted;
    }
}
