package com.team2813.lib.actions;

import com.team2813.frc2022.Robot.RobotMode;

import java.util.List;

public abstract class Action {

    public static void updateActions(List<Action> actions, RobotMode mode, double now) {
        // remove actions that have finished
        actions.removeIf(action -> {
            action.execute(now);
            if(action.isFinished(now) || (action.getRemoveOnDisabled() && mode == RobotMode.DISABLED)){
                action.end(now);
                return true;
            }
            else return false;
        });
    }

    /**
     * runs the Action
     * @param timestamp
     */
    protected abstract void execute(double timestamp);

    /**
     * Returns whether or not the code has finished execution.
     *
     * @return true if finished, false otherwise
     */
    protected abstract boolean isFinished(double timestamp);

    /**
     * Run code once when the action is started, for set up
     */
    public abstract void start(double timestamp);

    /**
     * Run after update returns true
     */
    public abstract void end(double timestamp);

    /**
     * Returns whether action should be removed when robot has been disabled.
     *
     * @return Always returns false
     */
    protected boolean getRemoveOnDisabled() {return false;}
}
