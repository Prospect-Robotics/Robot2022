package com.team2813.lib.controls;

public class ExpFilter extends ControlFilter {
    public ExpFilter(ControlInput next){
        super(next);
    }

    @Override
    public double get() {
        double value = next.get();
        double f = 2.5; // Change for more/less acceleration
        double s = 1.0 / (Math.exp(f)-1);

        if(value > 0){
            return s * (Math.exp(f*value)-1);
        } else {
            return -s * (Math.exp(f*-value)-1);
        }
    }
}
