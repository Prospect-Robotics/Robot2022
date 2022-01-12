package com.team2813.frc2022.subsystems;

import com.team2813.frc2022.loops.Loop;

/**
 * The Subsystem abstract class serves as a base framework for all robot subsystems. Each subsystem outputs commands,
 * has a stop routine (for after each match) and has a routine to zero all sensors (which helps with calibration).
 * <p>
 *     All Subsystems only have one instance, and functions get the instance of each subsystem and act accordingly.
 *     Subsystems are also a state machine with a desired state and actual state; the robot code will try to match
 *     the two states with actions. Each Subsystem also is responsible for instantiating all member components at
 *     the start of the match.
 * </p>
 */
abstract class Subsystem implements Loop {

    public void writeToLog() {
    }

    /**
     * Read in current status from motors
     * <p>
     * Optional design pattern for caching periodic reads to avoid hammering the HAL/CAN.
     */
    protected void readPeriodicInputs() {
    }

    /**
     * Output to motors
     * <p>
     * Optional design pattern for caching periodic writes to avoid hammering the HAL/CAN.
     */
    protected void writePeriodicOutputs() {
    }

    public abstract void outputTelemetry();

    public void zeroSensors() {
    }

    public abstract void teleopControls();

    //#region Looping

    @Override
    public abstract void onEnabledStart(double timestamp);

    public synchronized final void onEnabledStart_(double timestamp) {
        System.out.println("Subsystem OnEnabledStart");
        onEnabledStart(timestamp);
    }


    public abstract void onEnabledLoop(double timestamp);

    @Override
    public synchronized final void onEnabledLoop_(double timestamp) {
        readPeriodicInputs();
        this.onEnabledLoop(timestamp);
        writePeriodicOutputs();
    }

    @Override
    public abstract void onEnabledStop(double timestamp);

    @Override
    public void onDisabledStart(double timestamp) {
    };

    @Override
    public synchronized final void onDisabledLoop(double timestamp) {
        readPeriodicInputs();
//        onDisabledLoop(timestamp);
        writePeriodicOutputs();
    }

    public void onAnyLoop(double timestamp) {}
}
