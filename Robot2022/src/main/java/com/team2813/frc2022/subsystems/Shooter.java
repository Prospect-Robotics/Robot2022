package com.team2813.frc2022.subsystems;

import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;

public class Shooter extends Subsystem {

    // Motor Controllers
    private final TalonFXWrapper FLYWHEEL;

    // Controllers
    private static final Button SHOOTER_BUTTON = SubsystemControlsConfig.getShooterButton();

    private double demand = 0;

    public Shooter() {
        FLYWHEEL = (TalonFXWrapper) MotorConfigs.talons.get("flywheel");
    }

    @Override
    public void outputTelemetry() {

    }

    @Override
    public void teleopControls() {
        SHOOTER_BUTTON.whenPressedReleased(() -> demand = 0.5, () -> demand = 0);
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

    @Override
    protected void writePeriodicOutputs() {
        FLYWHEEL.set(ControlMode.DUTY_CYCLE, demand);
    }
}
