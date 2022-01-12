package com.team2813.frc2022.loops;

/**
 * Interface for loops, which are routines that run periodically in the robot code
 */
public interface Loop {

    /**
     * Code to run once when robot is enabled
     *
     * @param timestamp timestamp in seconds
     */
    default void onEnabledStart(double timestamp) {}

    /**
     * Code to run while robot is enabled
     *
     * @param timestamp timestamp in seconds
     */
    default void onEnabledLoop_(double timestamp) {}

    /**
     * Code to run once when robot is disabled
     *
     * @param timestamp timestamp in seconds
     */
    default void onEnabledStop(double timestamp) {}

    /**
     * Code to run once when robot is disabled
     *
     * @param timestamp timestamp in seconds
     */
    default void onDisabledStart(double timestamp) {}

    /**
     * Code to run while robot is disabled
     *
     * @param timestamp timestamp in seconds
     */
    default void onDisabledLoop(double timestamp) {}

    /**
     * Code to run once when robot is enabled
     *
     * @param timestamp timestamp in seconds
     */
    default void onDisabledStop(double timestamp) {}

    /**
     * Code runs all the time
     *
     * @param timestamp timestamp in seconds
     */
    default void onAnyLoop(double timestamp) {}
}
