package com.team2813.lib.controls;

/**
 * The Button object is a Joystick button
 * that has methods to trigger functions while
 * held or when pressed.
 */
public class Button {
    private Controller controller;
    private int buttonNumber;

    /**
     * Create a controller button for triggering functions.
     *
     * @param controller     The Controller object that has the button
     * @param buttonNumber The button number
     */
    public Button(Controller controller, int buttonNumber) {
        this.controller = controller;
        this.buttonNumber = buttonNumber;
    }

    /**
     * Run if button raw boolean is true
     * @param function
     */
    public void whileHeld(Runnable function) {
        if (get()) function.run();
    }

    /**
     * Run one function when initially pressed, and another when released. Requires repeated call.
     * @param functionWhenPressed
     * @param functionWhenReleased
     */
    public void whenPressedReleased(Runnable functionWhenPressed, Runnable functionWhenReleased) {
        if (getPressed()) {
            functionWhenPressed.run();
        }
        if (getReleased()) {
            functionWhenReleased.run();
        }
    }

    /**
     * Run one function while held, and another when released. Requires repeated call.
     * @param functionWhileHeld
     * @param functionWhenReleased
     */
    public void whileHeldWhenReleased(Runnable functionWhileHeld, Runnable functionWhenReleased) {
        boolean running = false;
        if (get()) {
            functionWhileHeld.run();
            running = true;
        }
        if (getReleased() && running) {
            functionWhenReleased.run();
        }
    }

    /**
     * Run if button has been released and pressed again since last whenPressed call
     * @param function
     */
    public void whenPressed(Runnable function) {
        if (getPressed()) function.run();
    }

    /**
     * Run if button has been released since last whenReleased call
     * @param function
     */
    public void whenReleased(Runnable function) {
        if (getReleased()) function.run();
    }

    /**
     * Get current raw boolean value from button
     * @return true if currently pressed
     */
    public boolean get()  {
        return controller.getRawButton(buttonNumber);
    }

    /**
     * Get whether button has been released and pressed again since last call
     * @return true if released and pressed again since last call
     */
    public boolean getPressed() {
        return controller.getRawButtonPressed(buttonNumber);
    }

    /**
     * Get whether button has been released since last call
     * @return true if released since last call
     */
    public boolean getReleased() {
        return controller.getRawButtonReleased(buttonNumber);
    }
}
