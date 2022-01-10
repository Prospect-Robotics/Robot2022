package com.team2813.lib.ctre;

/**
 * Enum storing different timeout values in ms for construction time
 * or runtime updates.
 */
public enum TimeoutMode {
    /** Longer timeout, used for constructors */
    CONSTRUCTING(100),
    /** Shorter timeout, used for on the fly updates */
    RUNNING(20),
    NO_TIMEOUT(0);

    public final int valueMs;

    TimeoutMode(int valueMs) {
        this.valueMs = valueMs;
    }
}
