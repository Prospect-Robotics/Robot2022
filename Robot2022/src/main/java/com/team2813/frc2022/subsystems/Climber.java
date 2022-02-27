package com.team2813.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import com.team2813.lib.motors.interfaces.LimitDirection;
import com.team2813.lib.solenoid.PistonSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Climber extends Subsystem1d<Climber.Position> {

    // Controllers
    private static final Button EXTEND_BUTTON = SubsystemControlsConfig.getClimberExtendButton();
    private static final Button CLIMB_BUTTON = SubsystemControlsConfig.getClimbButton();
    private static final Button SWIVEL_BUTTON = SubsystemControlsConfig.getClimbSwivelButton();

    private final PistonSolenoid PISTONS;

    private Position currentPosition = Position.RETRACTED;
    private double demand = 0;

    public Climber() {
        super(MotorConfigs.talons.get("climber"));

        PISTONS = new PistonSolenoid(14, PneumaticsModuleType.CTREPCM, 0, 1);
        ((TalonFXWrapper) getMotor()).setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 200);
//        getMotor().setSoftLimit(LimitDirection.REVERSE, 0);
//        getMotor().setSoftLimit(LimitDirection.FORWARD, 81);
    }

    @Override
    public void outputTelemetry() {

    }

    @Override
    public void teleopControls() {
        SWIVEL_BUTTON.whenPressed(PISTONS::toggle);

//        EXTEND_BUTTON.whenPressed(() -> setNextPosition(Position.EXTENDED));
//        CLIMB_BUTTON.whenPressed(() -> setNextPosition(Position.RETRACTED));
        EXTEND_BUTTON.whenPressedReleased(() -> demand = 0.2, () -> demand = 0);
        CLIMB_BUTTON.whenPressedReleased(() -> demand = -0.95, () -> demand = 0);
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

    @Override
    public void writePeriodicOutputs() {
        getMotor().set(ControlMode.DUTY_CYCLE, demand);
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