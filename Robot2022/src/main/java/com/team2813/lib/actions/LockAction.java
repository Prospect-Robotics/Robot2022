package com.team2813.lib.actions;

import java.util.concurrent.Callable;

/**
 * Action that repeatedly calls a function until it returns true
 */
public class LockAction extends Action {

    private final Callable<Boolean> lockFunction;
    private final boolean removeOnDisabled;
    private boolean unlocked = false;

    /**
     * Creates a new action that waits until a condition is met
     * @param function
     * @param removeOnDisabled
     */
    public LockAction(Callable<Boolean> function, boolean removeOnDisabled) {
        this.lockFunction = function;
        this.removeOnDisabled = removeOnDisabled;
    }

    @Override
    public void start(double timestamp) {
    }

    @Override
    protected void execute(double timestamp) {
        try {
            unlocked = lockFunction.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isFinished(double timestamp) {
        return unlocked;
    }

    @Override
    public void end(double timestamp) {

    }

    @Override
    protected boolean getRemoveOnDisabled() {
        return removeOnDisabled;
    }
}
