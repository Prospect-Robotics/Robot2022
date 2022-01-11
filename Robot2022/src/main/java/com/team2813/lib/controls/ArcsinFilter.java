package com.team2813.lib.controls;

public class ArcsinFilter extends ControlFilter {

    public ArcsinFilter(ControlInput next){
        super(next);
    }

    @Override
    public double get() {
        double value = next.get();
        return 2 * Math.asin(value) / Math.PI;
    }
}
