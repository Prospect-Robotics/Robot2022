package com.team2813.frc2022.subsystems;

import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;

public class Magazine extends Subsystem {
    // physical constants: TODO

    // motor controllers
    private final TalonFXWrapper MAGAZINE;

    // controllers
    private final Button START_STOP_BUTTON = SubsystemControlsConfig.getMagButton();

    public Magazine() {
        MAGAZINE = (TalonFXWrapper) MotorConfigs.talons.get("magazine");
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
