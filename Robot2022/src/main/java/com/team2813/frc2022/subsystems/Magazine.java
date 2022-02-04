package com.team2813.frc2022.subsystems;

import com.team2813.frc2022.util.Units2813;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Axis;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.CANifier;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// issues will get resolved when Intake branch gets merged with main
import static com.team2813.frc2022.subsystems.Subsystems.INTAKE;
import static com.team2813.frc2022.subsystems.Subsystems.SHOOTER;

public class Magazine extends Subsystem {
    // initializing physical constants
    // initializing motor controllers
    private final TalonFXWrapper MOTOR; 
    private final CANifier INTAKE_COUNTER;
    private final Joystick OPERATOR_JOYSTICK = SubsystemControlsConfig.getOperatorJoystick();
    private final Button START_STOP_BUTTON = SubsystemControlsConfig.getMagButton();
    private final Button FORWARD_BUTTON = SubsystemControlsConfig.getMagForward();
    private final Button REVERSE_BUTTON = SubsystemControlsConfig.getMagReverse();
    private final Button AUTO_BUTTON = SubsystemControlsConfig.getAutoButton();
    private Demand demand;

    public int ammo = 0;
    public boolean triggered = false;
    public boolean ballDetected = false;



    public boolean isCounterBlocked() {
        return INTAKE_COUNTER.getGeneralInput(CANifier.GeneralPin.SDA);
    }

    public void spinMagazineForward() {
        if (SHOOTER.demand == Shooter.Demand.LOW_RANGE)
            demand = Demand.LOW_RANGE;
        else demand = Demand.FAR_RANGE;
        SHOOTER.setKicker(Shooter.KickerDemand.ON);
        INTAKE.setIntake(Intake.Demand.IN);
    }


    @Override
    protected void readPeriodicInputs() {  
        ballDetected = isCounterBlocked();
        if (ballDetected) { // counts balls
            if (ammo < 5 && !triggered) { // block only runs once
                ammo++;
            }
            triggered = true;
        } else triggered = false;
    }

    enum Demand {
        LOW_RANGE(1), FAR_RANGE(.5), OFF(0.0), REV(-0.3), INTAKE(0.2);

        double percent;

        Demand(double percent) {
            this.percent = percent;
        }
    }

    @Override
    public void outputTelemetry() {
        // TODO Auto-generated method stub
        SmartDashboard.putBoolean("Ball Detected", ballDetected);
        SmartDashboard.putNumber("Ammo", ammo);
        SmartDashboard.putNumber("Magazine Demand", demand.percent);
        
    }


    @Override
    public void teleopControls() {
        // TODO Auto-generated method stub
        START_STOP_BUTTON.whenPressedReleased(this::spinMagazineForward, this::stopMagazine);
        FORWARD_BUTTON.whenPressedReleased(this::spinMagazineIntake, this::stopMagazine);
        REVERSE_BUTTON.whenPressedReleased(this::spinMagazineReverse, this::stopMagazine);
        
    }


    @Override
    public void onEnabledStart(double timestamp) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void onEnabledLoop(double timestamp) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void onEnabledStop(double timestamp) {
        // TODO Auto-generated method stub
        
    }

}
