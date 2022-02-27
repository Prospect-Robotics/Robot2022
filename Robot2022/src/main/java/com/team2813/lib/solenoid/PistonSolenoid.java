package com.team2813.lib.solenoid;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

import java.util.ArrayList;

/**
 * Keeps track of piston states on solenoid as extended/retracted
 */
public class PistonSolenoid {

    private ArrayList<Solenoid> solenoids = new ArrayList<>();

    /**
     * Allows for multiple solenoids to be tied to the same piston in the code
     * @param ids on PCM 0
     */
    public PistonSolenoid(PneumaticsModuleType moduleType, int... ids) {
        for (int id : ids) {
            solenoids.add(new Solenoid(moduleType, id));
        }
    }

    public PistonSolenoid(int canId, PneumaticsModuleType moduleType, int... ids) {
        for (int id : ids) {
            solenoids.add(new Solenoid(canId, moduleType, id));
        }
    }

    public void set(PistonState state) {
        for (Solenoid solenoid : solenoids) {
            solenoid.set(state.value);
        }
    }

    public void retract(){
        set(PistonState.RETRACTED);
    }

    public void extend(){
        set(PistonState.EXTENDED);
    }

    public void toggle() {
        set(get() == PistonState.RETRACTED ? PistonState.EXTENDED : PistonState.RETRACTED);
    }

    public PistonState get() {
        return PistonState.from(solenoids.get(0).get());
    }

    public enum PistonState {
        RETRACTED(false), EXTENDED(true);

        public final boolean value;

        PistonState(boolean value) {
            this.value = value;
        }

        private static PistonState from(boolean b) {
            return b ? EXTENDED : RETRACTED;
        }
    }
}
