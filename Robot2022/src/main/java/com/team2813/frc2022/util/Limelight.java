package com.team2813.frc2022.util;

import com.team2813.lib.util.LimelightValues;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Limelight {

    private LimelightValues values = new LimelightValues();
    private static final double MOUNT_ANGLE = 0; // degrees
    private static final double MOUNT_HEIGHT = 0; // inches
    private static final double TARGET_HEIGHT = 104; // inches

    private static NetworkTableEntry trimEntry = Shuffleboard.getTab("Tuning").addPersistent("Trim", 0).getEntry();

    private Limelight() {
        setStream(2);
    }

    private static Limelight instance = new Limelight();

    private Limelight getInstance() {
        return instance;
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
