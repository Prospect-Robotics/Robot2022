package com.team2813.frc2022.subsystems;

import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;

public class Magazine extends Subsystem {

    // mag should spin forward when shooter is being run, forward when intake is running forward, and backwards when intake is being run backwards.
    // motor controllers
    private final TalonFXWrapper MAGAZINE;
    
    // controllers
    /* Step 1: set the demand, teloep controls
       Step 2: write the motors (make them do stuff)
       Step 3: 
    */

    private static final Button SHOOTER_BUTTON = SubsystemControlsConfig.getShooterButton();
    private static final Button INTAKE_IN_BUTTON = SubsystemControlsConfig.getIntakeInButton();
    private static final Button INTAKE_OUT_BUTTON = SubsystemControlsConfig.getIntakeOutButton();

    private Demand demand;
    
    public Magazine() {
        MAGAZINE = (TalonFXWrapper) MotorConfigs.talons.get("magazine");
    }

    @Override
    public void outputTelemetry() {
        
    }

    @Override
    public void teleopControls() {
        // here 
        SHOOTER_BUTTON.whenPressedReleased(() -> setDemand(Demand.SHOOT), () -> setDemand(Demand.OFF));
        INTAKE_IN_BUTTON.whenPressedReleased(() -> setDemand(Demand.IN), () -> setDemand(Demand.OFF));
        INTAKE_OUT_BUTTON.whenPressedReleased(() -> setDemand(Demand.OUT), () -> setDemand(Demand.OFF));
        

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

    protected void writePeriodicOutputs() {
        MAGAZINE.set(ControlMode.DUTY_CYCLE, demand.percent);
    }
}
