package com.team2813.frc2022.subsystems;

import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.motors.TalonFXWrapper;

public class Climber extends Subsystem {

    // Motor Controllers:
    private final TalonFXWrapper CLIMBER;

    public Climber() {
        CLIMBER = (TalonFXWrapper) MotorConfigs.talons.get("climber");
    }

    @Override
    public void outputTelemetry() {

    }

    @Override
    public void teleopControls() {

    }

    @Override
    public void onEnabledStart(double timestamp) {

    }

    @Override
    public void onEnabledLoop(double timestamp) {

    }

    @Override
    public void onEnabledStop(double timestamp) {

    }
}
