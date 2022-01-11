package com.team2813.lib.drive;

import com.team2813.lib.config.PIDControllerConfig;
import com.team2813.lib.config.SparkConfig;
import com.team2813.lib.motors.SparkMaxWrapper;

public class VelocityDriveSpark {
    private SparkMaxWrapper spark;
    private double maxVelocity;

    public VelocityDriveSpark(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    /**
     * Configure a CANSparkMaxWrapper with configuration for velocity drive.
     * @param spark
     * @param config
     */
    public void configureMotor(SparkMaxWrapper spark, SparkConfig config) {
        this.spark = spark;
        PIDControllerConfig pidConfig = config.getPidControllers().get(0);
        spark.setPIDF(0, pidConfig.getP(), pidConfig.getI(), pidConfig.getD(), pidConfig.getF());
        spark.getPIDController().setSmartMotionMaxVelocity(pidConfig.getMaxVelocity(), 0);
        spark.getPIDController().setSmartMotionMaxAccel(pidConfig.getMaxAcceleration(), 0);
        spark.getPIDController().setSmartMotionMinOutputVelocity(0, 0);
        spark.getPIDController().setSmartMotionAllowedClosedLoopError(pidConfig.getAllowableClosedLoopError(), 0);
    }

    public void setMaxVelocity(int maxVelocity) {
        if (maxVelocity != this.maxVelocity)
            spark.getPIDController().setSmartMotionMaxVelocity(maxVelocity, 0);
        this.maxVelocity = maxVelocity;
    }

    public double getVelocityFromDemand(double demand) {
        return maxVelocity * demand;
    }
}
