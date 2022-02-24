package com.team2813.frc2022.subsystems;

import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;

public class Climber extends Subsystem1d<Climber.Position> {

    // Controllers
    private static final Button EXTEND_BUTTON = SubsystemControlsConfig.getClimberExtendButton();
    private static final Button CLIMB_BUTTON = SubsystemControlsConfig.getClimbButton();

    private Position currentPosition = Position.RETRACTED;

    public Climber() {
        super(MotorConfigs.talons.get("climber"));
    }

    @Override
    public void outputTelemetry() {

    }

    @Override
    public void teleopControls() {
        EXTEND_BUTTON.whenPressed(() -> setNextPosition(Position.EXTENDED));
        CLIMB_BUTTON.whenPressed(this::climb);
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

    public void climb() {
        setPosition(Position.RETRACTED);
        // TODO: add code to lock climber
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