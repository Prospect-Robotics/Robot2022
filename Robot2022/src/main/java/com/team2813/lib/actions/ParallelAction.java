package com.team2813.lib.actions;

import com.team2813.frc2022.Robot.RobotMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.team2813.frc2022.subsystems.Subsystems.LOOPER;

public class ParallelAction extends Action {

    private List<Action> actions;

    /**
     * Creates a new action that runs a list of actions to be run simultaneously
     * @param actions
     */
    public ParallelAction(List<? extends Action> actions) {
        this.actions = new ArrayList<>(actions);
    }

    /**
     * Creates a new action that runs a list of actions to be run simultaneously
     * @param actions
     */
    public ParallelAction(Action...actions) {
        this(Arrays.asList(actions));
    }

    @Override
    public void start(double timestamp) {
        for(Action action : actions) action.start(timestamp);
    }

    @Override
    protected void execute(double timestamp) {
        actions.removeIf(action -> {
            action.execute(timestamp);
            if(action.isFinished(timestamp) || (LOOPER.mode == RobotMode.DISABLED && action.getRemoveOnDisabled())){
                action.end(timestamp);
                return true;
            }
            else return false;
        });

        // actions.removeIf(action -> action.update(timestamp));
    }

    @Override
    protected boolean isFinished(double timestamp) {
        return actions.size() == 0; // done if no actions left
    }

    @Override
    public void end(double timestamp) {
        for(Action action : actions){
            action.end(timestamp);
        }
    }

    @Override
    protected boolean getRemoveOnDisabled() {
        return false;
    }

}
