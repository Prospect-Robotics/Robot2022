package com.team2813.lib.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightValues {
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry tv = table.getEntry("tv");
    private NetworkTableEntry tx = table.getEntry("tx");
    private NetworkTableEntry ty = table.getEntry("ty");
    private NetworkTableEntry ta = table.getEntry("ta");
    private NetworkTableEntry ts = table.getEntry("ts");
    private NetworkTableEntry tl = table.getEntry("tl");
    private NetworkTableEntry tshort = table.getEntry("tshort");
    private NetworkTableEntry tlong = table.getEntry("tlong");
    private NetworkTableEntry thor = table.getEntry("thor");
    private NetworkTableEntry tvert = table.getEntry("tvert");
    private NetworkTableEntry getPipe = table.getEntry("getpipe");
    private NetworkTableEntry camtranEntry = table.getEntry("camtran");
    private NetworkTableEntry ledMode = table.getEntry("ledMode");
    private NetworkTableEntry stream = table.getEntry("stream");

    public void update() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public NetworkTable getTable() {
        return table;
    }

    public NetworkTableEntry getTv() {
        return tv;
    }

    public double getTx() {
        return tx.getDouble(0);
    }

    public double getTy() {
        return ty.getDouble(0);
    }

    public NetworkTableEntry getTa() {
        return ta;
    }

    public NetworkTableEntry getTs() {
        return ts;
    }

    public NetworkTableEntry getTl() {
        return tl;
    }

    public NetworkTableEntry getTshort() {
        return tshort;
    }

    public NetworkTableEntry getTlong() {
        return tlong;
    }

    public NetworkTableEntry getThor() {
        return thor;
    }

    public NetworkTableEntry getTvert() {
        return tvert;
    }

    public NetworkTableEntry getGetPipe() {
        return getPipe;
    }

    public NetworkTableEntry getCamtranEntry() {
        return camtranEntry;
    }

    public NetworkTableEntry getLedMode() {
        return ledMode;
    }

    public NetworkTableEntry getStream() {
        return stream;
    }
}
