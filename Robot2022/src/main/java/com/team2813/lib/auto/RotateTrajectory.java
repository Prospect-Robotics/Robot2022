package com.team2813.lib.auto;

import edu.wpi.first.math.trajectory.Trajectory;

import java.util.List;

public class RotateTrajectory extends Trajectory implements AutoTrajectory {
    private boolean finished;
    private double degrees;
    private double seconds = 0.05; // to always stay a little ahead
    private static final double MAX_ROTATION_TIME = 4;
    private final int INDEX;

    private RotateTrajectory(List<State> states, int index) { // should never be used
        super(states);
        INDEX = index;
    }

    public RotateTrajectory(double degrees, int index) {
        this(List.of(new Trajectory.State()), index);
        this.degrees = degrees;
    }

    public double getDegrees() {
        return degrees;
    }

    public void poll() {
        this.seconds += 1.0 / 50; // every 20ms
    }

    public void finish() {
        finished = true;
    }

    public void resetTimer(){
        seconds = 0.05;
    }

    @Override
    public double getTotalTimeSeconds() {
        return finished ? seconds : (seconds + 1);// +1 is unreachable so that it will never stop if unfinished
    }

    @Override
    public Trajectory getTrajectory() {
        return this;
    }

    @Override
    public boolean isReversed() {
        return false;
    }

    @Override
    public int getIndex() {
        return INDEX;
    }
}
