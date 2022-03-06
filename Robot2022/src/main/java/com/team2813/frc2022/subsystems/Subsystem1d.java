package com.team2813.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.team2813.lib.motors.Motor;
import com.team2813.lib.motors.SparkMaxWrapper;
import com.team2813.lib.motors.TalonWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import edu.wpi.first.wpilibj.Timer;

import static com.team2813.lib.logging.LogLevel.DEBUG;

public abstract class Subsystem1d<P extends Subsystem1d.Position> extends Subsystem {

    private Motor motor;
    protected PeriodicIO periodicIO = new PeriodicIO();
    private boolean zeroed = false;
    private boolean motionMagicDisabled = false;
//	Mode mode = Mode.HOLDING;

    Subsystem1d(SparkMaxWrapper motor) {
        try {
            this.motor = motor;
            motor.setPeriodicFrame(CANSparkMaxLowLevel.PeriodicFrame.kStatus2);
            motor.set(ControlMode.DUTY_CYCLE, 0);
            motor.setNeutralMode(CANSparkMax.IdleMode.kBrake);
        } catch (Exception e) {
//			new Exception("Subsystem construction failed", e).printStackTrace();
            e.printStackTrace();
        }
    }

    Subsystem1d(TalonWrapper motor) {
        try {
            this.motor = motor;
//            motor.setStatusFramePeriod(CANSparkMaxLowLevel.PeriodicFrame.kStatus2);
            motor.set(ControlMode.DUTY_CYCLE, 0);
            motor.setNeutralMode(NeutralMode.Brake);
        } catch (Exception e) {
//			new Exception("Subsystem construction failed", e).printStackTrace();
            e.printStackTrace();
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
    public void writePeriodicOutputs() {
        try {
            resetIfAtLimit();
            if (!periodicIO.openLoop && !motionMagicDisabled)
                motor.set(ControlMode.MOTION_MAGIC, periodicIO.demand);
        } catch (Exception e) {
            System.out.println("Subsystem initialization failed");
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void readPeriodicInputs() {
        final double t = Timer.getFPGATimestamp();
        periodicIO.positionTicks = motor.getEncoderPosition();
//		if (periodicIO.positionTicks + 0.5
    }

    public synchronized void resetIfAtLimit() {
        if (periodicIO.limitSwitch) {
            zeroSensors();
        }
    }

    @Override
    public synchronized void zeroSensors() {
        motor.setEncoderPosition(0);
        DEBUG.log(motor.getSubsystemName(), "zeroed 1", motor.getEncoderPosition());
        zeroed = true;
    }

    public boolean isZeroed() {
        return zeroed;
    }

    class PeriodicIO {
        double demand;

        boolean limitSwitch;

        double positionTicks;

        boolean openLoop = false;
    }

    /*==========================
     * POSITION
     * ==========================*/

    protected interface Position<E> {
        /**
         * int encoder ticks
         */
        double getPos();

        E getNextClockwise();

        E getNextCounter();

        E getMin();

        E getMax();

        default E getClock(boolean clockwise) {
            return clockwise ? getNextClockwise() : getNextCounter();
        }
    }

    synchronized void setPosition(double encoderPosition) {
//        System.out.println(motor.getSubsystemName() + "Setting Position to " + encoderPosition);
        periodicIO.demand = encoderPosition;
        periodicIO.openLoop = false;
//		mode = Mode.MOVING;
    }

    synchronized void setPosition(P position) {
        setPosition(position.getPos());
    }

    abstract void setNextPosition(boolean clockwise);

    abstract void setNextPosition(P position);

    public Motor getMotor() {
        return motor;
    }

    public void disableMotionMagic(boolean disabled) {
        motionMagicDisabled = disabled;
    }
}
