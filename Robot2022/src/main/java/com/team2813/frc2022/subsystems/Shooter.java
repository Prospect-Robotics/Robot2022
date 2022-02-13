package com.team2813.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
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
    private final TalonFXWrapper KICKER_MOTOR;

    // Controllers
    private static final Button SHOOTER_BUTTON = SubsystemControlsConfig.getShooterButton();
    private static final Button INTAKE_IN_BUTTON = SubsystemControlsConfig.getIntakeInButton();
    private static final Button INTAKE_OUT_BUTTON = SubsystemControlsConfig.getIntakeOutButton();
    

    private double flywheelDemand = 0;
    private KickerDemand kickerDemand = KickerDemand.OFF;
    private boolean isFullyRevvedUp;

    public Shooter() {
        FLYWHEEL = (TalonFXWrapper) MotorConfigs.talons.get("flywheel");
        KICKER_MOTOR = (TalonFXWrapper) MotorConfigs.talons.get("kicker");
        KICKER_MOTOR.setNeutralMode(NeutralMode.Brake);
    }

    public boolean isFlywheelReady() {
        return Math.abs(Units2813.motorRevsToWheelRevs(FLYWHEEL.getVelocity(), FLYWHEEL_UPDUCTION) - flywheelDemand) < 500;
    }

    boolean isFullyRevvedUp() {
        return isFullyRevvedUp;
    }

    @Override
    public void outputTelemetry() {
        double flywheelVelocity = Units2813.motorRevsToWheelRevs(FLYWHEEL.getVelocity(), FLYWHEEL_UPDUCTION);
        SmartDashboard.putNumber("Flywheel Demand", flywheelDemand);
        SmartDashboard.putNumber("Flywheel Velocity", flywheelVelocity);
    }

    @Override
    public void teleopControls() {
        SHOOTER_BUTTON.whenPressedReleased(() -> {
            setFlywheel(0.7);
            setKicker(KickerDemand.IN);
        }, () -> {
            setFlywheel(0);
            setKicker(KickerDemand.OFF);
        });

        INTAKE_IN_BUTTON.whenPressedReleased(() -> setKicker(KickerDemand.OUT), () -> setKicker(KickerDemand.OFF));
        INTAKE_OUT_BUTTON.whenPressedReleased(() -> setKicker(KickerDemand.OUT), () -> setKicker(KickerDemand.OFF));

        isFullyRevvedUp = FLYWHEEL.getVelocity() >= Units2813.wheelRevsToMotorRevs(flywheelDemand, FLYWHEEL_UPDUCTION);
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
        FLYWHEEL.set(ControlMode.DUTY_CYCLE, flywheelDemand);
        KICKER_MOTOR.set(ControlMode.DUTY_CYCLE, kickerDemand.percent);
    }

    public void setFlywheel(double demand) {
        this.flywheelDemand = demand;
    }

    public enum KickerDemand {
        IN(0.4), OFF(0), OUT(-0.8);

        double percent;

        KickerDemand(double percent) {
            this.percent = percent;
        }
    }

    public void setKicker(KickerDemand demand) {
        this.kickerDemand = demand;
    }
}
