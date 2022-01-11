package com.team2813.lib.drive;

import com.team2813.lib.config.PIDControllerConfig;
import com.team2813.lib.config.TalonConfig;
import com.team2813.lib.motors.TalonWrapper;

public class VelocityDriveTalon {
    private TalonWrapper talon;
    private double maxVelocity;

    public VelocityDriveTalon(double maxVelocity) { // can be in any units
        this.maxVelocity = maxVelocity;
    }

    /**
     * Configure a TalonWrapper with configuration for velocity drive.
     * @param talon
     * @param config
     */
    public void configureMotor(TalonWrapper talon, TalonConfig config) {
        this.talon = talon;
        PIDControllerConfig pidConfig = config.getPidControllers().get(0);
        talon.setPIDF(TalonWrapper.PIDProfile.PRIMARY.id, pidConfig.getP(), pidConfig.getI(), pidConfig.getD(), pidConfig.getF());
        talon.setMotionMagicVelocity(0);
        talon.setMotionMagicAcceleration(0);
//        talon.getPIDController().setSmartMotionMinOutputVelocity(0, 0);
//        talon.getPIDController().setSmartMotionAllowedClosedLoopError(pidConfig.getAllowableClosedLoopError(), 0);
    }

    public DriveDemand getVelocity(DriveDemand driveDemand) {
        return new DriveDemand(maxVelocity * driveDemand.getLeft(), maxVelocity * driveDemand.getRight());
    }
}
