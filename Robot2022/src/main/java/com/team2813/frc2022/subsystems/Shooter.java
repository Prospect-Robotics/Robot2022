package com.team2813.frc2022.subsystems;

import com.team2813.frc2022.util.Units2813;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {

    // Physical Constants
    private static final double FLYWHEEL_DIAMETER = Units.inchesToMeters(4);
    private static final double FLYWHEEL_CIRCUMFERENCE = Math.PI * FLYWHEEL_DIAMETER;
    public static final double FLYWHEEL_UPDUCTION = 3.0 / 2.0;

    // Motor Controllers
    private final TalonFXWrapper FLYWHEEL;

    // Controllers
    private static final Button SHOOTER_BUTTON = SubsystemControlsConfig.getShooterButton();

    private double demand = 0;
    private boolean isFullyRevvedUp;

    public Shooter() {
        FLYWHEEL = (TalonFXWrapper) MotorConfigs.talons.get("flywheel");
    }

    public boolean isFlywheelReady() {
        return Math.abs(Units2813.motorRevsToWheelRevs(FLYWHEEL.getVelocity(), FLYWHEEL_UPDUCTION) - demand) < 500;
    }

    boolean isFullyRevvedUp() {
        return isFullyRevvedUp;
    }

    @Override
    public void outputTelemetry() {
        double flywheelVelocity = Units2813.motorRevsToWheelRevs(FLYWHEEL.getVelocity(), FLYWHEEL_UPDUCTION);
        SmartDashboard.putNumber("Flywheel Demand", demand);
        SmartDashboard.putNumber("Flywheel Velocity", flywheelVelocity);
    }

    @Override
    public void teleopControls() {
        SHOOTER_BUTTON.whenPressedReleased(() -> setFlywheel(0.5), () -> setFlywheel(0));

        isFullyRevvedUp = FLYWHEEL.getVelocity() >= Units2813.wheelRevsToMotorRevs(demand, FLYWHEEL_UPDUCTION);
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

    public void setFlywheel(double demand) {
        this.demand = demand;
    }
}
