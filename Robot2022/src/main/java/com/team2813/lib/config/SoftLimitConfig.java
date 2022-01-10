package com.team2813.lib.config;

import com.team2813.lib.motors.interfaces.LimitDirection;

public class SoftLimitConfig {
    LimitDirection direction;
    boolean clearOnLimit;
    int threshold;
    boolean enable;
}
