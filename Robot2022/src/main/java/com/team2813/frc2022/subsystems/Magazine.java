package com.team2813.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import edu.wpi.first.wpilibj.Timer;

import static com.team2813.frc2022.subsystems.Subsystems.MAGAZINE;

public class Magazine extends Subsystem {

    // mag should spin forward when shooter is being run, forward when intake is running forward, and backwards when intake is being run backwards.
    // motor controllers
    private final TalonFXWrapper MAGAZINE;
    private final TalonFXWrapper KICKER;
    
    // controllers
    /* Step 1: set the demand, teloep controls
       Step 2: write the motors (make them do stuff)
       Step 3: 
    */

    private MagDemand magDemand = MagDemand.OFF;
    private KickerDemand kickerDemand = KickerDemand.OFF;
    
    public Magazine() {
        MAGAZINE = (TalonFXWrapper) MotorConfigs.talons.get("magazine");
        MAGAZINE.setNeutralMode(NeutralMode.Brake);

        KICKER = (TalonFXWrapper) MotorConfigs.talons.get("kicker");
        KICKER.setNeutralMode(NeutralMode.Brake);
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

    public void autoShoot(boolean on) {
        if (on) {
            double timeStart = Timer.getFPGATimestamp();
            double dt = Timer.getFPGATimestamp() - timeStart;
            while (dt <= 0.1) {
                dt = Timer.getFPGATimestamp() - timeStart;
                setMagDemand(MagDemand.OUT);
                setKickerDemand(KickerDemand.OUT);
            }
            setMagDemand(MagDemand.SHOOT);
            setKickerDemand(KickerDemand.IN);
        }
        else {
            setMagDemand(MagDemand.OFF);
            setKickerDemand(KickerDemand.OFF);
        }
    }

    public enum MagDemand {
        IN(0.2), OFF(0), OUT(-0.2), SHOOT(0.125);

        double percent;

        MagDemand(double percent) {
            this.percent = percent;
        }
    }

    public void setMagDemand(MagDemand magDemand) {
        this.magDemand = magDemand;
    }

    public enum KickerDemand {
        IN(0.7), OFF(0), OUT(-0.4);

        double percent;

        KickerDemand(double percent) {
            this.percent = percent;
        }
    }

    public void setKickerDemand(KickerDemand kickerDemand) {
        this.kickerDemand = kickerDemand;
    }

    protected void writePeriodicOutputs() {
        MAGAZINE.set(ControlMode.DUTY_CYCLE, magDemand.percent);
        KICKER.set(ControlMode.DUTY_CYCLE, kickerDemand.percent);
    }
}
