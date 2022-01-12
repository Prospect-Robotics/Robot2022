package com.team2813.lib.actions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SeriesAction extends Action {

    private Queue<Action> actions;

    private Action currentAction;

    /**
     * Creates a new action from a list of actions to be run sequentially
     * @param actions
     */
    public SeriesAction(List<? extends Action> actions) {
        this.actions = new LinkedList<>(actions);
    }

    /**
     * Creates a new action from a list of actions to be run sequentially
     * @param actions
     */
    public SeriesAction(Action... actions) {
        this(Arrays.asList(actions));
    }

    @Override
    public void start(double timestamp) {
        currentAction = actions.poll();
        currentAction.start(timestamp);
    }

    @Override
    protected void execute(double timestamp) {
        currentAction.execute(timestamp);
        if (currentAction.isFinished(timestamp)) {
            currentAction.end(timestamp);
            currentAction = actions.poll();
            if (currentAction == null) {
                return;
            };
            currentAction.start(timestamp);
        }
    }

    @Override
    protected boolean isFinished(double timestamp) {
        return currentAction == null; }

    @Override
    public void end(double timestamp) {
        if(currentAction != null) currentAction.end(timestamp);
    }

    @Override
    protected boolean getRemoveOnDisabled() {
        if(currentAction == null) return false;
        else return currentAction.getRemoveOnDisabled();
    }
}
