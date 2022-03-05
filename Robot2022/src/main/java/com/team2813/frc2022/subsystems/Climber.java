package com.team2813.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.team2813.lib.actions.*;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import com.team2813.lib.solenoid.PistonSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.team2813.frc2022.subsystems.Subsystems.INTAKE;
import static com.team2813.frc2022.subsystems.Subsystems.LOOPER;

public class Climber extends Subsystem1d<Climber.Position> {

    // Controllers
    private static final Button EXTEND_BUTTON = SubsystemControlsConfig.getExtendButton();
    private static final Button MID_CLIMB_BUTTON = SubsystemControlsConfig.getMidClimbButton();
    private static final Button RISE_BUTTON = SubsystemControlsConfig.getRiseButton();

    private final PistonSolenoid PISTONS;

    private Position currentPosition = Position.RETRACTED;

    public Climber() {
        super(MotorConfigs.talons.get("climber"));

        PISTONS = new PistonSolenoid(14, PneumaticsModuleType.CTREPCM, 0, 1);
        ((TalonFXWrapper) getMotor()).setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 200);
    }

    @Override
    public void outputTelemetry() {
        SmartDashboard.putNumber("Climber Encoder", getMotor().getEncoderPosition());
        SmartDashboard.putNumber("Climber Velocity", getMotor().getVelocity());
    }

    @Override
    public void teleopControls() {
        EXTEND_BUTTON.whenPressed(() -> setNextPosition(Position.EXTENDED));
        MID_CLIMB_BUTTON.whenPressed(this::midClimb);
        RISE_BUTTON.whenPressed(this::riseUp);
    }

    @Override
    void setNextPosition(boolean clockwise) {
        currentPosition = (Position) currentPosition.getClock(clockwise);
        setPosition(currentPosition);
        disableMotionMagic(false);
    }

    @Override
    void setNextPosition(Position position) {
        currentPosition = position;
        setPosition(currentPosition);
        disableMotionMagic(false);
    }

    @Override
    public void onEnabledStop(double timestamp) {
        PISTONS.set(PistonSolenoid.PistonState.RETRACTED);
    }

    public boolean positionReached() {
        return Math.abs(currentPosition.getPos() - getMotor().getEncoderPosition()) < 0.05;
    }

    private void retract() {
        setPower(-0.98);
        double timeStart = Timer.getFPGATimestamp();
        double dt = Timer.getFPGATimestamp() - timeStart;
        while ((dt < 0.25) || (Math.abs(getMotor().getVelocity()) > 0.5)) {
            dt = Timer.getFPGATimestamp() - timeStart;
            // wait...
        }
        setPower(0);
        zeroSensors();
    }

    private void midClimb() {
        Action midClimb = new SeriesAction(
                new FunctionAction(() -> INTAKE.setDeployed(true), true),
                new FunctionAction(this::retract, true)
        );
        LOOPER.addAction(midClimb);
    }

    private void riseUp() {
        Action riseUp = new SeriesAction(
                new FunctionAction(PISTONS::toggle, true),
                new LockFunctionAction(() -> setNextPosition(Position.RISE_POS), this::positionReached, true),
                new ParallelAction(
                        new FunctionAction(PISTONS::toggle, true),
                        new FunctionAction(this::retract, true)
                )
        );
        LOOPER.addAction(riseUp);
    }

    public enum Position implements Subsystem1d.Position {
        RETRACTED(0) {
            @Override
            public Object getNextClockwise() {
                return RISE_POS;
            }

            @Override
            public Object getNextCounter() {
                return EXTENDED;
            }
        }, RISE_POS(80) {
            @Override
            public Object getNextClockwise() {
                return EXTENDED;
            }

            @Override
            public Object getNextCounter() {
                return RETRACTED;
            }
        }, EXTENDED(122) {
            @Override
            public Object getNextClockwise() {
                return RETRACTED;
            }

            @Override
            public Object getNextCounter() {
                return RISE_POS;
            }
        };

        int encoderTicks;

        Position(int encoderTicks) {
            this.encoderTicks = encoderTicks;
        }

        @Override
        public double getPos() {
            return encoderTicks;
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