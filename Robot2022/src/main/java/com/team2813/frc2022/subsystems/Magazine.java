package com.team2813.frc2022.subsystems;

import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;

public class Magazine extends Subsystem {
    // physical constants:

    // mag should spin forward when shooter is being run, forward when intake is running forward, and backwards when intake is being run backwards.
    // motor controllers
    private final TalonFXWrapper MAGAZINE;
    
    // controllers
    
    //Step 1: 
    
    private static final Button SHOOTER_BUTTON = SubsystemControlsConfig.getShooterButton();
    private static final Button INTAKE_IN_BUTTON = SubsystemControlsConfig.getIntakeInButton();
    private static final Button INTAKE_OUT_BUTTON = SubsystemControlsConfig.getIntakeOutButton();
    
    public Magazine() {
        MAGAZINE = (TalonFXWrapper) MotorConfigs.talons.get("magazine");
    }

    @Override
    public void outputTelemetry() {

    }

    @Override
    public void teleopControls() {
        
    }

    @Override
    public void onEnabledStart(double timestamp) {

    }

    @Override
    public void onEnabledLoop(double timestamp) {

    }

    @Override
    public void onEnabledStop(double timestamp) {

    }

    public enum Demand {
        IN(0.8), OFF(0), OUT(-0.8), SHOOT(0.8);


        double percent;

        Demand (double percent) {
            this.percent = percent;
        }
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }
}
