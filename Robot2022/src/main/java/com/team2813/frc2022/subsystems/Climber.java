package com.team2813.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.team2813.lib.actions.*;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.LimitDirection;
import com.team2813.lib.solenoid.PistonSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import static com.team2813.frc2022.subsystems.Subsystems.INTAKE;
import static com.team2813.frc2022.subsystems.Subsystems.LOOPER;

public class Climber extends Subsystem1d<Climber.Position> {

    // Controllers
    private static final Button MID_CLIMB_BUTTON = SubsystemControlsConfig.getMidClimbButton();
    private static final Button RISE_BUTTON = SubsystemControlsConfig.getRiseButton();
    private static final Button SWIVEL_BUTTON = SubsystemControlsConfig.getClimbSwivelButton();

    private final PistonSolenoid PISTONS;

    private Position currentPosition = Position.RETRACTED;
    private double demand = 0;

    public Climber() {
        super(MotorConfigs.talons.get("climber"));

        PISTONS = new PistonSolenoid(14, PneumaticsModuleType.CTREPCM, 0, 1);
        ((TalonFXWrapper) getMotor()).setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 200);
        getMotor().setSoftLimit(LimitDirection.REVERSE, 0);
        getMotor().setSoftLimit(LimitDirection.FORWARD, 81);
    }

    @Override
    public void outputTelemetry() {

    }

    @Override
    public void teleopControls() {
        SWIVEL_BUTTON.whenPressed(PISTONS::toggle);

        MID_CLIMB_BUTTON.whenPressed(this::midClimb);
        RISE_BUTTON.whenPressed(this::riseUp);
    }

    @Override
    void setNextPosition(boolean clockwise) {
        currentPosition = (Position) currentPosition.getClock(clockwise);
        setPosition(currentPosition);
    }

    @Override
    void setNextPosition(Position position) {
        currentPosition = position;
        setPosition(currentPosition);
    }

    @Override
    public void onEnabledStop(double timestamp) {
        PISTONS.set(PistonSolenoid.PistonState.RETRACTED);
    }

    public boolean positionReached() {
        return (getMotor()).getEncoderPosition() > currentPosition.getPos();
    }

    private void midClimb() {
        Action midClimb = new SeriesAction(
                new FunctionAction(() -> INTAKE.setDeployed(true), true),
                new LockFunctionAction(() -> setNextPosition(Position.EXTENDED), this::positionReached, true),
                new LockFunctionAction(() -> setNextPosition(Position.RETRACTED), this::positionReached, true)
        );
        LOOPER.addAction(midClimb);
    }

    private void riseUp() {
        Action riseUp = new SeriesAction(
                new FunctionAction(PISTONS::toggle, true),
                new LockFunctionAction(() -> setNextPosition(Position.EXTENDED), this::positionReached, true),
                new LockFunctionAction(() -> setNextPosition(Position.RETRACTED), this::positionReached, true),
                new FunctionAction(PISTONS::toggle, true)
        );
        LOOPER.addAction(riseUp);
    }

    public enum Position implements Subsystem1d.Position {
        RETRACTED(0), EXTENDED(80);

        int encoderTicks;

        Position(int encoderTicks) {
            this.encoderTicks = encoderTicks;
        }

        @Override
        public double getPos() {
            return encoderTicks;
        }

        @Override
        public Object getNextClockwise() {
            if (this == RETRACTED) {
                return EXTENDED;
            }
            else {
                return RETRACTED;
            }
        }

        @Override
        public Object getNextCounter() {
            return getNextClockwise();
        }

        @Override
        public Object getMin() {
            return RETRACTED;
        }

        @Override
        public Object getMax() {
            return EXTENDED;
        }
    }
}