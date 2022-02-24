package com.team2813.frc2022.subsystems;

import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.solenoid.PistonSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Climber extends Subsystem1d<Climber.Position> {

    // Controllers
    private static final Button EXTEND_BUTTON = SubsystemControlsConfig.getClimberExtendButton();
    private static final Button CLIMB_BUTTON = SubsystemControlsConfig.getClimbButton();
    private static final Button SWIVEL_BUTTON = SubsystemControlsConfig.getClimbSwivelButton();

    private final PistonSolenoid PISTONS;

    private Position currentPosition = Position.RETRACTED;

    public Climber() {
        super(MotorConfigs.talons.get("climber"));

        PISTONS = new PistonSolenoid(PneumaticsModuleType.REVPH, 0, 1);
    }

    @Override
    public void outputTelemetry() {

    }

    @Override
    public void teleopControls() {
        SWIVEL_BUTTON.whenPressed(PISTONS::toggle);

        EXTEND_BUTTON.whenPressed(() -> setNextPosition(Position.EXTENDED));
        CLIMB_BUTTON.whenPressed(() -> setNextPosition(Position.RETRACTED));
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

    public enum Position implements Subsystem1d.Position {
        RETRACTED(0), EXTENDED(2048);

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