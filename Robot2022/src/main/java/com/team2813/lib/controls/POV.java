package com.team2813.lib.controls;

/**
 * The POV object is a Joystick D-Pad
 * that has method to get the angle of
 * the POV
 */
public class POV {
    private Controller controller;

    /**
     * Create a POV for getting the value
     * of the controller POV
     *
     * @param controller   The Joystick object that has the axis
     */
    public POV(Controller controller){
        this.controller = controller;
    }

    /**
     * get current
     * @return
     */
    public int get() {
        return controller.getPOV();
    }
}
