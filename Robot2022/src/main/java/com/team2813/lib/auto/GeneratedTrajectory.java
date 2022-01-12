package com.team2813.lib.auto;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;

import java.io.IOException;
import java.nio.file.Paths;

public class GeneratedTrajectory implements AutoTrajectory {
    private boolean reversed;
    private final int INDEX;

    private Trajectory trajectory;

    public GeneratedTrajectory(String pathName, boolean reversed, int index) {
        this.reversed = reversed;
        INDEX = index;

        try {
            trajectory = TrajectoryUtil.fromPathweaverJson(Paths.get(Filesystem.getDeployDirectory().getAbsolutePath(), "paths", pathName + ".wpilib.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isReversed() {
        return reversed;
    }

    @Override
    public int getIndex() {
        return INDEX;
    }

    public Trajectory getTrajectory() {
        return trajectory;
    }

    @Override
    public double getTotalTimeSeconds() {
        return trajectory.getTotalTimeSeconds();
    }
}
