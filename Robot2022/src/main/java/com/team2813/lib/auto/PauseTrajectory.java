package com.team2813.lib.auto;

import edu.wpi.first.math.trajectory.Trajectory;

import java.util.List;

public class PauseTrajectory extends Trajectory implements AutoTrajectory {
    private double time;
    private final int INDEX;

    public PauseTrajectory(double time, int index) {
        super(List.of(new Trajectory.State()));
        this.time = time;
        INDEX = index;
    }

    @Override
    public double getTotalTimeSeconds() {
        return time;
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
