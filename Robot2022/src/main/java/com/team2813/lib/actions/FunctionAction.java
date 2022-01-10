package com.team2813.lib.actions;

/**
 * Takes a Runnable and executes it
 */
public class FunctionAction extends Action {

    private final Runnable function;
    private final boolean removeOnDisabled;

    /**
     * Creates a new action from a function
     * @param function
     * @param removeOnDisabled
     */
    public FunctionAction(Runnable function, boolean removeOnDisabled) {
        this.function = function;
        this.removeOnDisabled = removeOnDisabled;
    }

    @Override
    public void start(double timestamp) {
        function.run();
    }

    @Override
    protected void execute(double timestamp) {

    }

    @Override
    protected boolean isFinished(double timestamp) {
        return true;
    }

    @Override
    public void end(double timestamp) {

    }

    @Override
    protected boolean getRemoveOnDisabled() {
        return removeOnDisabled;
    }
}
