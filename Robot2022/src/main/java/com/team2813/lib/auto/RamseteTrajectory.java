package com.team2813.lib.auto;

import edu.wpi.first.math.trajectory.Trajectory;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows RamseteAuto to only have to process one trajectory as a WHOLE and simplifies chaining together multiple trajectories.
 */
public class RamseteTrajectory {
    private List<Trajectory> trajectories = new ArrayList<>();
    private List<Boolean> reversed = new ArrayList<>();
    private Trajectory currentTrajectory;
    private int currentIndex = 0;

    public RamseteTrajectory(Trajectory trajectory, boolean reversed) {
        trajectories.add(trajectory);
        this.reversed.add(reversed);
    }

    public RamseteTrajectory(List<AutoTrajectory> generatedTrajectories) {
        for (AutoTrajectory generatedTrajectory : generatedTrajectories) {
            trajectories.add(generatedTrajectory.getTrajectory());
            reversed.add(generatedTrajectory.isReversed());
        }
    }

    public TrajectorySample sample(double dt) {
        double time = 0;
        for (int i = 0; i < trajectories.size(); i++) {
            Trajectory trajectory = trajectories.get(i);
            currentTrajectory = trajectories.get(i);
            if (dt < time + trajectory.getTotalTimeSeconds()) {
                if (trajectory instanceof PauseTrajectory) // if it is a pause
                    return new TrajectorySample().setPause(true);
                if (trajectory instanceof RotateTrajectory)
                    return new TrajectorySample(trajectory).setRotate(true, ((RotateTrajectory) trajectory).getDegrees());

                currentIndex = i;
                return new TrajectorySample(trajectory, trajectory.sample(
                        dt - time), reversed.get(i));
            } else
                time += trajectory.getTotalTimeSeconds();
        }
        currentIndex = trajectories.size() - 1;
        return new TrajectorySample(new Trajectory(List.of(new Trajectory.State())), trajectories.get(0).sample(dt), reversed.get(0));
    }

    public Trajectory getCurrentTrajectory() {
        return currentTrajectory;
    }

    public boolean isCurrentTrajectory(int index) {
        try {
            return currentIndex == index;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public List<Trajectory> getTrajectories() {
        return this.trajectories;
    }
}
