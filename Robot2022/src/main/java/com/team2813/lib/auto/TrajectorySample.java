package com.team2813.lib.auto;

import edu.wpi.first.math.trajectory.Trajectory;

public class TrajectorySample {
    private Trajectory.State state;
    private boolean reversed;
    private double degrees;
    private boolean isPause = false;
    private boolean isRotate = false;
    private Trajectory trajectory;

    public TrajectorySample() {

    }

    public double getDegrees() {
        return degrees;
    }

    public TrajectorySample(Trajectory trajectory) {
        this.trajectory = trajectory;
    }

    public TrajectorySample(Trajectory trajectory, Trajectory.State state, boolean reversed) {
        this.trajectory = trajectory;
        this.state = state;
        this.reversed = reversed;
    }

    public boolean isPause() {
        return isPause;
    }

    public boolean isRotate() {
        return isRotate;
    }

    public TrajectorySample setPause(boolean pause) {
        isPause = pause;
        return this;
    }

    public TrajectorySample setRotate(boolean rotate, double degrees) {
        this.degrees = degrees;
        isRotate = rotate;
        return this;
    }

    public Trajectory.State getState() {
        return state;
    }

    public boolean isReversed() {
        return reversed;
    }

    public Trajectory getTrajectory() {
        return trajectory;
    }
}
