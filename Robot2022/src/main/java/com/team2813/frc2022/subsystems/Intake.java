package com.team2813.frc2022.subsystems;

import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import com.team2813.lib.solenoid.PistonSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.team2813.frc2022.subsystems.Subsystems.MAGAZINE;

public class Intake extends Subsystem {

    // Motor Controllers
    private final TalonFXWrapper INTAKE_MOTOR;

    // Controllers
    private static final Button INTAKE_PISTONS_BUTTON = SubsystemControlsConfig.getIntakePistonsButton();
    private static final Button INTAKE_IN_BUTTON = SubsystemControlsConfig.getIntakeInButton();
    private static final Button INTAKE_OUT_BUTTON = SubsystemControlsConfig.getIntakeOutButton();

    private final PistonSolenoid PISTONS;

    private Demand demand = Demand.OFF;
    private boolean deployed;

    public Intake() {
        INTAKE_MOTOR = (TalonFXWrapper) MotorConfigs.talons.get("intake");
        PISTONS = new PistonSolenoid(PneumaticsModuleType.REVPH, 0, 1);
    }

    @Override
    public void outputTelemetry() {
        SmartDashboard.putBoolean("Intake Deployed", deployed);
    }

    @Override
    public void teleopControls() {
        INTAKE_PISTONS_BUTTON.whenPressed(this::togglePistons);

        INTAKE_IN_BUTTON.whenPressedReleased(() -> {
            setIntake(Demand.IN);
            MAGAZINE.setMagDemand(Magazine.MagDemand.IN);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OUT);
        }, () -> {
            setIntake(Demand.OFF);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
        });

        INTAKE_OUT_BUTTON.whenPressedReleased(() -> {
            setIntake(Demand.OUT);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OUT);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OUT);
        }, () -> {
            setIntake(Demand.OFF);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
        });
    }

    @Override
    public void onEnabledStart(double timestamp) {
        setDeployed(false);
    }

    @Override
    public void onEnabledLoop(double timestamp) {

    }

    @Override
    public void onEnabledStop(double timestamp) {
        setDeployed(false);
    }

    @Override
    protected void writePeriodicOutputs() {
        INTAKE_MOTOR.set(ControlMode.DUTY_CYCLE, demand.percent);
    }

    public enum Demand {
        IN(0.45), OFF(0), OUT(-0.45);

        double percent;

        Demand(double percent) {
            this.percent = percent;
        }
    }

    @Override
    protected void readPeriodicInputs() {
        deployed = PISTONS.get().value;
    }

    public void setIntake(Demand demand) {
        this.demand = demand;
    }

    public void togglePistons() {
        PISTONS.toggle();
    }

    public void setDeployed(boolean deployed) {
        PISTONS.set(deployed ? PistonSolenoid.PistonState.EXTENDED : PistonSolenoid.PistonState.RETRACTED);
    }
}
