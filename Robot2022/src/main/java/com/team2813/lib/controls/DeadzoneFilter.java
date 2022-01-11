package com.team2813.lib.controls;

public class DeadzoneFilter extends ControlFilter {
    public double deadzone;

    public DeadzoneFilter(ControlInput next, double deadzone){
        super(next);
        this.deadzone = deadzone;
    }

    @Override
    public double get() {
        double value = next.get();
        if(Math.abs(value) < deadzone){
            return 0;
        }
        if(value > 0){
            return value-deadzone;
        }
        return value+deadzone;
    }
}
