package com.team2813.frc2022.subsystems;

import com.team2813.lib.actions.Action;
import com.team2813.lib.actions.FunctionAction;
import com.team2813.lib.actions.SeriesAction;
import com.team2813.lib.actions.WaitAction;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import com.team2813.lib.solenoid.PistonSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.team2813.frc2022.subsystems.Subsystems.LOOPER;
import static com.team2813.frc2022.subsystems.Subsystems.MAGAZINE;

public class Intake extends Subsystem {

    // Motor Controllers
    private final TalonFXWrapper INTAKE_MOTOR;

    // Controllers
    private static final Button INTAKE_IN_BUTTON = SubsystemControlsConfig.getIntakeInButton();
    private static final Button INTAKE_OUT_BUTTON = SubsystemControlsConfig.getIntakeOutButton();

    private final PistonSolenoid PISTONS;

    private Demand demand = Demand.OFF;
    private boolean deployed;

    public Intake() {
        INTAKE_MOTOR = (TalonFXWrapper) MotorConfigs.talons.get("intake");
        PISTONS = new PistonSolenoid(14, PneumaticsModuleType.CTREPCM, 2, 3);
    }

    @Override
    public void outputTelemetry() {
        SmartDashboard.putBoolean("Intake Deployed", deployed);
    }

    @Override
    public void teleopControls() {
        INTAKE_IN_BUTTON.whenPressedReleased(() -> {
            Action intakeAction = new SeriesAction(
                    new FunctionAction(() -> setDeployed(true), true),
                    new WaitAction(0.4),
                    new FunctionAction(() ->setIntake(Demand.IN), true),
                    new FunctionAction(() -> MAGAZINE.setMagDemand(Magazine.MagDemand.IN), true),
                    new FunctionAction(() -> MAGAZINE.setKickerDemand(Magazine.KickerDemand.IN), true)
            );
            LOOPER.addAction(intakeAction);
        }, () -> {
            setIntake(Demand.OFF);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
            setDeployed(false);
        });

        INTAKE_OUT_BUTTON.whenPressedReleased(() -> {
            MAGAZINE.setMagDemand(Magazine.MagDemand.OUT);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OUT);
        }, () -> {
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
        });
    }

    public void autoIntake(boolean on) {
        setDeployed(on);
        if (on) {
            double timeStart = Timer.getFPGATimestamp();
            double dt = Timer.getFPGATimestamp() - timeStart;
            while (dt < 0.4) {
                dt = Timer.getFPGATimestamp() - timeStart;
                // wait...
            }
            setIntake(Demand.IN);
            MAGAZINE.setMagDemand(Magazine.MagDemand.IN);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OUT);
        }
        else {
            setIntake(Demand.OFF);
            MAGAZINE.setMagDemand(Magazine.MagDemand.OFF);
            MAGAZINE.setKickerDemand(Magazine.KickerDemand.OFF);
            setDeployed(false);
        }
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
        IN(0.85), OFF(0);

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

    public void setDeployed(boolean deployed) {
        PISTONS.set(deployed ? PistonSolenoid.PistonState.EXTENDED : PistonSolenoid.PistonState.RETRACTED);
    }
}
