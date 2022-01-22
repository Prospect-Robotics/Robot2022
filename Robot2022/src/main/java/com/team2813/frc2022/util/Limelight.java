package com.team2813.frc2022.util;

import com.team2813.lib.util.LimelightValues;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Limelight {

    private LimelightValues values = new LimelightValues();
    private final double kP = 0.2; // TODO: tune kP
    private static final double MAX_CORRECTION_STEER_SPEED = 0.7;
    private static final double MIN_CORRECTION_STEER_SPEED = 0.05;
    private static final double MOUNT_ANGLE = 52; // degrees
    private static final double MOUNT_HEIGHT = 27.6; // inches
    private static final double TARGET_HEIGHT = 104; // inches

    private static NetworkTableEntry trimEntry = Shuffleboard.getTab("Tuning").addPersistent("Trim", 0).getEntry();

    private Limelight() {
        setStream(2);
    }

    private static Limelight instance = new Limelight();

    public static Limelight getInstance() {
        return instance;
    }

    public double getSteer() {
        if (Math.abs(values.getTx()) > 0.5) {
            double sign = Math.abs(values.getTx()) / values.getTx();
            return (((values.getTx()) / 29.8) * kP * MAX_CORRECTION_STEER_SPEED + (sign * MIN_CORRECTION_STEER_SPEED));
        }
        return 0;
    }

    private double calculateHorizontalDistance() {
        double angle = Math.toRadians(MOUNT_ANGLE - values.getTy());
        return (Units.inchesToMeters(TARGET_HEIGHT - MOUNT_HEIGHT) / Math.tan(angle)) + Units.inchesToMeters(24);
    }

    public double getShooterDemand() { // returns in m/s
        double distance = calculateHorizontalDistance();
        return 2 * Math.sqrt(4.9) * distance / Math.sqrt((distance * Math.sqrt(3)) - Units.inchesToMeters(TARGET_HEIGHT - MOUNT_HEIGHT));
    }

    public void setLights(boolean enable) {
        values.getLedMode().setNumber(enable ? 0 : 1);
    }

    public LimelightValues getValues() {
        return values;
    }

    public void setStream(int stream) {
        values.getStream().setNumber(stream);
    }
}
