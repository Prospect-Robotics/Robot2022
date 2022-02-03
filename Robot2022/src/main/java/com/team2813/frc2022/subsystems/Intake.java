package com.team2813.frc2022.subsystems;

import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import com.team2813.lib.solenoid.PistonSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Intake extends Subsystem {

    // Motor Controllers
    private final TalonFXWrapper INTAKE_MOTOR;

    // Controllers
    private static final Button INTAKE_PISTONS_BUTTON = SubsystemControlsConfig.getIntakePistonsButton();
    private static final Button INTAKE_IN_BUTTON = SubsystemControlsConfig.getIntakeInButton();
    private static final Button INTAKE_OUT_BUTTON = SubsystemControlsConfig.getIntakeOutButton();

    private final PistonSolenoid PISTONS;

    private Demand demand = Demand.OFF;

    public Intake() {
        INTAKE_MOTOR = (TalonFXWrapper) MotorConfigs.talons.get("intake");
        PISTONS = new PistonSolenoid(PneumaticsModuleType.REVPH, 0, 1);
    }

    @Override
    public void outputTelemetry() {

    }

    @Override
    public void teleopControls() {
        INTAKE_PISTONS_BUTTON.whenPressed(PISTONS::toggle);

        INTAKE_IN_BUTTON.whenPressedReleased(() -> setIntake(Demand.IN), () -> setIntake(Demand.OFF));
        INTAKE_OUT_BUTTON.whenPressedReleased(() -> setIntake(Demand.OUT), () -> setIntake(Demand.OFF));
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

    @Override
    protected void writePeriodicOutputs() {
        INTAKE_MOTOR.set(ControlMode.DUTY_CYCLE, demand.percent);
    }

    public enum Demand {
        IN(0.8), OFF(0), OUT(-0.8);

        double percent;

        Demand(double percent) {
            this.percent = percent;
        }
    }

    public void setIntake(Demand demand) {
        this.demand = demand;
    }
}
