package com.team2813.lib.actions;

import java.util.concurrent.Callable;

public class LockFunctionAction extends Action {

    private final Callable<Boolean> lockFunction;
    private final Runnable function;
    private final boolean removeOnDisabled;
    private boolean unlocked = false;

    /**
     * Creates a new action that waits until a condition is met
     * @param function
     * @param lockFunction
     * @param removeOnDisabled
     */
    public LockFunctionAction(Runnable function, Callable<Boolean> lockFunction, boolean removeOnDisabled){
        this.function = function;
        this.lockFunction = lockFunction;
        this.removeOnDisabled = removeOnDisabled;
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
    public void start(double timestamp) {
        function.run();
    }

    @Override
    public void end(double timestamp) {

    }

    @Override
    protected boolean getRemoveOnDisabled() {
        return removeOnDisabled;
    }
}
