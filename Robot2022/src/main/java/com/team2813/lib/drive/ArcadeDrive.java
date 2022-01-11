package com.team2813.lib.drive;

/**
 * Arcade Drive: determine steer using x, y, and a deadzone
 */
public class ArcadeDrive {
    public DriveDemand getDemand(double x, double y) {
        double maxPercent = 1.0;
        double throttleLeft;
        double throttleRight;

        double steer = 0;

        throttleLeft = maxPercent * y;
        throttleRight = maxPercent * y;
        steer = 1.0 * x;

        return new DriveDemand(throttleLeft + steer, throttleRight - steer);
    }
}
