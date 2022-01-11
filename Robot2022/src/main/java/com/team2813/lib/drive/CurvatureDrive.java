package com.team2813.lib.drive;

/**
 * Curvature Drive: use one axis for forward, another
 * for reverse, and a third for steering, with a pivot
 * button
 */
public class CurvatureDrive {
    private ArcadeDrive arcadeDrive;

    public CurvatureDrive(double deadzone) {
        arcadeDrive = new ArcadeDrive();
    }

    public DriveDemand getDemand(double throttleForward, double throttleReverse, double steerX, boolean pivot) {
        double throttle =  2 * Math.asin(throttleForward - throttleReverse) / Math.PI;
        double steer = 2 * Math.asin(steerX) / Math.PI;

        if (!pivot) steer *= 1.3;

        steer = -steer;
        return arcadeDrive.getDemand(pivot ? steer : throttle * steer, throttle);
    }

    public ArcadeDrive getArcadeDrive() {
        return arcadeDrive;
    }
}
