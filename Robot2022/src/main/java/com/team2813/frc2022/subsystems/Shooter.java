package com.team2813.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.team2813.frc2022.util.Limelight;
import com.team2813.frc2022.util.Units2813;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.team2813.frc2022.subsystems.Subsystems.DRIVE;
import static com.team2813.frc2022.subsystems.Subsystems.MAGAZINE;

public class Shooter extends Subsystem {

    // Physical Constants
    private static final double FLYWHEEL_DIAMETER = Units.inchesToMeters(4);
    public static final double FLYWHEEL_CIRCUMFERENCE = Math.PI * FLYWHEEL_DIAMETER;
    public static final double FLYWHEEL_UPDUCTION = 3.0 / 2.0;

    // Motor Controllers
    private final TalonFXWrapper FLYWHEEL;

    // Controllers
    private static final Button SHOOTER_BUTTON = SubsystemControlsConfig.getShooterButton();
    private static final Button MANUAL_SHOOT_BUTTON = SubsystemControlsConfig.getManualShootButton();
    private static final Button LOW_SHOOT_BUTTON = SubsystemControlsConfig.getLowShootButton();
    private static final Button SPOOL_BUTTON = SubsystemControlsConfig.getSpoolButton();

    private Limelight limelight = Limelight.getInstance();

    private double demand = 0;
    //private final double spoolDemand = 0.435;
    private final double spoolDemand = 3600;
    private final double lowDemand = 1500;

    private boolean isFullyRevvedUp;

    public Shooter() {
        FLYWHEEL = (TalonFXWrapper) MotorConfigs.talons.get("flywheel");
        FLYWHEEL.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 200);
    }

    public boolean isFlywheelReady() {
        return Math.abs(Units2813.motorRevsToWheelRevs(FLYWHEEL.getVelocity(), FLYWHEEL_UPDUCTION) - demand) < 175;
    }

    boolean isFullyRevvedUp() {
        return isFullyRevvedUp;
    }

    @Override
    public void outputTelemetry() {
        double flywheelVelocity = Units2813.motorRevsToWheelRevs(FLYWHEEL.getVelocity(), FLYWHEEL_UPDUCTION);
        double error = demand - flywheelVelocity;
        SmartDashboard.putNumber("Flywheel Demand", demand);
        SmartDashboard.putNumber("Flywheel Velocity", flywheelVelocity);
        SmartDashboard.putNumber("Flywheel Error", error);
        SmartDashboard.putNumber("Flywheel Encoder", FLYWHEEL.getEncoderPosition());
        SmartDashboard.putNumber("Distance to Target", limelight.calculateHorizontalDistance());
    }

    public void teleopControls() {
        SPOOL_BUTTON.whenPressed(() -> setShooter(spoolDemand));

        if (SHOOTER_BUTTON.get()) {
            if (DRIVE.getIsAimed()) {
                setShooter(limelight.getShooterDemand());
                if (isFlywheelReady()) {
                    MAGAZINE.setMagDemand(Magazine.MagDemand.SHOOT);
                    MAGAZINE.setKickerDemand(Magazine.KickerDemand.IN);
                }
                else {
                    MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
                    MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
                }
            }
        }

        SHOOTER_BUTTON.whenReleased(() -> {
            setShooter(0);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
        });

        if (MANUAL_SHOOT_BUTTON.get()) {
            //setShooter(limelight.getShooterDemand());
            if (isFlywheelReady()) {
                MAGAZINE.setMagDemand(Magazine.MagDemand.SHOOT);
                MAGAZINE.setKickerDemand(Magazine.KickerDemand.IN);
            }
            else {
                MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
                MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
            }
        }

        MANUAL_SHOOT_BUTTON.whenReleased(() -> {
            setShooter(0);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
        });

        if (LOW_SHOOT_BUTTON.get()) {
            setShooter(lowDemand);
            if (isFlywheelReady()) {
                MAGAZINE.setMagDemand(Magazine.MagDemand.SHOOT);
                MAGAZINE.setKickerDemand(Magazine.KickerDemand.LOW);
            }
            else {
                MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
                MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
            }
        }

        LOW_SHOOT_BUTTON.whenReleased(() -> {
            setShooter(0);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
        });

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
        if (demand == 0) {
            double error = Math.abs(demand - Units2813.motorRevsToWheelRevs(FLYWHEEL.getVelocity(), FLYWHEEL_UPDUCTION));
            if (error <= 10) {
                FLYWHEEL.set(ControlMode.DUTY_CYCLE, 0);
            }
            else {
                FLYWHEEL.set(ControlMode.VELOCITY, 0);
            }
        }
        else {
            double motorDemand = Units2813.wheelRevsToMotorRevs(demand, FLYWHEEL_UPDUCTION);
            FLYWHEEL.set(ControlMode.VELOCITY, motorDemand);
        }
    }

    public void setShooter(double demand) {
        this.demand = demand;
    }
}
