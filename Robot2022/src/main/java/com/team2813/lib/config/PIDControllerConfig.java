package com.team2813.lib.config;

public class PIDControllerConfig {
    private double p;
    private double i;
    private double d;
    private double f;
    private double maxVelocity;
    private double maxAcceleration;
    private double minVelocity = 0; // default
    private double maxIntegralAccumulator;
    private double integralZone;
    private double allowableClosedLoopError;

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public double getMinVelocity() {
        return minVelocity;
    }

    public void setMinVelocity(double minVelocity) {
        this.minVelocity = minVelocity;
    }

    public double getMaxIntegralAccumulator() {
        return maxIntegralAccumulator;
    }

    public void setMaxIntegralAccumulator(double maxIntegralAccumulator) {
        this.maxIntegralAccumulator = maxIntegralAccumulator;
    }

    public double getIntegralZone() {
        return integralZone;
    }

    public void setIntegralZone(double integralZone) {
        this.integralZone = integralZone;
    }

    public double getAllowableClosedLoopError() {
        return allowableClosedLoopError;
    }

    public void setAllowableClosedLoopError(double allowableClosedLoopError) {
        this.allowableClosedLoopError = allowableClosedLoopError;
    }
}
