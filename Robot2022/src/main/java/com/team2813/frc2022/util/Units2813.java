package com.team2813.frc2022.util;

import com.team2813.frc2022.subsystems.Drive;
import com.team2813.lib.drive.DriveDemand;

public final class Units2813 {
    // ticks to motor revolutions
    public static double ticksToMotorRevs(double ticks, int cpr) {
        return ticks / (double) cpr;
    }

    public static double ticksToMotorRevs(double ticks) {
        return ticksToMotorRevs(ticks, 2048); // cause falcons
    }

    // motor revolutions to ticks
    public static int motorRevsToTicks(double revs, int cpr) {
        return (int) (revs * cpr);
    }

    public static int motorRevsToTicks(double revs) {
        return motorRevsToTicks(revs, 2048); // cause falcons
    }

    // motor revolutions to wheel revolutions of the drivetrain
    public static double motorRevsToWheelRevs(double revolutions, double gearRatio) {
        return revolutions * gearRatio;
    }

    public static double motorRevsToWheelRevs(double revolutions) {
        return motorRevsToWheelRevs(revolutions, Drive.GEAR_RATIO);
    }

    // wheel revs of the drivetrain to motor revs
    public static double wheelRevsToMotorRevs(double revs, double gearRatio) {
        return revs / gearRatio;
    }

    public static double wheelRevsToMotorRevs(double revolutions) {
        return wheelRevsToMotorRevs(revolutions, Drive.GEAR_RATIO);
    }

    // drivetrain velocity to motor rpms
    public static double dtVelocityToMotorRpm(double speed) {
        return wheelRevsToMotorRevs(speed / Drive.WHEEL_CIRCUMFERENCE) * 60;
    }

    public static double motorRpmToDtVelocity(double rpm) {
        return motorRevsToWheelRevs(rpm) * Drive.WHEEL_CIRCUMFERENCE / 60;
    }

    public static DriveDemand dtDemandToMotorDemand(DriveDemand demand) { // input in m/s output in motor rpm
        return new DriveDemand(dtVelocityToMotorRpm(demand.getLeft()), dtVelocityToMotorRpm(demand.getRight()));
    }
}
