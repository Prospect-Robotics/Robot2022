package com.team2813.frc2022.util;

import com.team2813.frc2022.subsystems.Shooter;
import com.team2813.lib.util.LimelightValues;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Limelight {

    private LimelightValues values = new LimelightValues();
    private final double kP = 0.875;
    private static final double MAX_CORRECTION_STEER_SPEED = 0.7;
    private static final double MIN_CORRECTION_STEER_SPEED = 0.05;
    private static final double MOUNT_ANGLE = 38; // degrees (this is mount angle without washers)
    private static final double MOUNT_HEIGHT = 27; // inches
    private static final double TARGET_HEIGHT = 104; // inches

    private Limelight() {
        setStream(0);
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

    public double calculateHorizontalDistance() {
        double angle = Math.toRadians(MOUNT_ANGLE + values.getTy() + 1); // adding offset for washers
        return Units.inchesToMeters(((TARGET_HEIGHT - MOUNT_HEIGHT) / Math.tan(angle)) + 26.5);
    }

    public double getShooterDemand() { // returns in rpm
        double distance = calculateHorizontalDistance();
        return -5444.444 + (6602.661 * distance) - (1606.313 * Math.pow(distance, 2)) + (134.8647 * Math.pow(distance, 3));
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
