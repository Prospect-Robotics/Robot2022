package com.team2813.lib.controls;

/**
 * The Axis object is a Joystick axis
 * that has method to get the value of
 * the axis
 */
public class Axis implements ControlInput {
    private Controller controller;
    private int axisNumber;

    /**
     * Create a controller axis for getting the value
     * of the axis
     *
     * @param controller   The Joystick object that has the axis
     * @param axisNumber The axis number
     */
    public Axis(Controller controller, int axisNumber) {
        this.controller = controller;
        this.axisNumber = axisNumber;
    }

    /**
     * get current
     * @return
     */
    public double get()  {
        return controller.getRawAxis(axisNumber);
    }
}
