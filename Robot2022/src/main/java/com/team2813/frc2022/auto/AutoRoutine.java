package com.team2813.frc2022.auto;

import com.team2813.lib.actions.Action;
import com.team2813.lib.auto.RamseteTrajectory;

public enum AutoRoutine {
    ROUTINE_1, ROUTINE_2, ROUTINE_3;

    private Action action;
    private RamseteTrajectory trajectory;
    public String name;

    public Action getAction() {
        return action;
    }

    public RamseteTrajectory getTrajectory() {
        return trajectory;
    }
}
