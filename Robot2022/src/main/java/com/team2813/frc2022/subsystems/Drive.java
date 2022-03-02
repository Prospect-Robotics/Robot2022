package com.team2813.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.team2813.frc2022.util.Limelight;
import com.team2813.frc2022.util.ShuffleboardData;
import com.team2813.frc2022.util.Units2813;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.*;
import com.team2813.lib.ctre.PigeonWrapper;
import com.team2813.lib.drive.ArcadeDrive;
import com.team2813.lib.drive.CurvatureDrive;
import com.team2813.lib.drive.DriveDemand;
import com.team2813.lib.drive.VelocityDriveTalon;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Subsystem {

    // Physical Constants
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4);
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    // Motor Controllers
    private final TalonFXWrapper LEFT;
    private final TalonFXWrapper RIGHT;

    // Controllers
    private static final double TELEOP_DEAD_ZONE = 0.01;
    private static final Axis ARCADE_X_AXIS = SubsystemControlsConfig.getDriveX();
    private static final Axis ARCADE_Y_AXIS = SubsystemControlsConfig.getDriveY();
    private static final Axis CURVATURE_STEER = SubsystemControlsConfig.getDriveSteer();
    private static final Axis CURVATURE_FORWARD = SubsystemControlsConfig.getDriveForward();
    private static final Axis CURVATURE_REVERSE = SubsystemControlsConfig.getDriveReverse();
    private static final Button PIVOT_BUTTON = SubsystemControlsConfig.getPivotButton();
    private static final Button SHOOTER_BUTTON = SubsystemControlsConfig.getShooterButton();
    private ControlInput arcade_x;
    private ControlInput arcade_y;

    // Encoders
    private static final double ENCODER_TICKS_PER_REVOLUTION = 2048;
    private static final double ENCODER_TICKS_PER_INCH = ENCODER_TICKS_PER_REVOLUTION / WHEEL_CIRCUMFERENCE;

    // Mode
    private static DriveMode driveMode = DriveMode.OPEN_LOOP;
    private static TeleopDriveType teleopDriveType = TeleopDriveType.CURVATURE;

    // Gyro
//    private final int pigeonID = 13;
//    private PigeonWrapper pigeon = new PigeonWrapper(pigeonID, "Drive");
//    public PigeonWrapper getPigeon() {
//        return pigeon;
//    }

    // Autonomous
    public static final double GEAR_RATIO = 1 / 7.64;
    private Limelight limelight = Limelight.getInstance();
    private double aimStart;
    private double aimingTime;
    private boolean isAiming = false;
    private boolean isAimed = false;

    public boolean getIsAimed() {
        return isAimed;
    }

    // Odometry
//    private static DifferentialDriveOdometry odometry;
//    public Pose2d robotPosition;
//
//    public static DifferentialDriveOdometry getOdometry() {
//        return odometry;
//    }

    public enum TeleopDriveType {
        ARCADE, CURVATURE
    }

    private static final double MAX_VELOCITY = 0; // max velocity of velocity drive in m/s
    public double getMaxVelocity() {
        return MAX_VELOCITY;
    }

    public VelocityDriveTalon velocityDrive = new VelocityDriveTalon(MAX_VELOCITY);
    public CurvatureDrive curvatureDrive = new CurvatureDrive(TELEOP_DEAD_ZONE);
    ArcadeDrive arcadeDrive = curvatureDrive.getArcadeDrive();
    DriveDemand driveDemand = new DriveDemand(0, 0);
    public DriveDemand getDriveDemand() {
        return driveDemand;
    }

    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0, 0, 0); // gains in meters

    public Drive() {
        ShuffleboardData.driveModeChooser.addOption("Open Loop", DriveMode.OPEN_LOOP);
        ShuffleboardData.driveModeChooser.addOption("Velocity", DriveMode.VELOCITY);
        ShuffleboardData.teleopDriveTypeChooser.addOption("Arcade", TeleopDriveType.ARCADE);
        ShuffleboardData.teleopDriveTypeChooser.addOption("Curvature", TeleopDriveType.CURVATURE);
        arcade_x = new ArcsinFilter(new DeadzoneFilter(ARCADE_X_AXIS, TELEOP_DEAD_ZONE));
        arcade_y = new ArcsinFilter(new DeadzoneFilter(ARCADE_Y_AXIS, TELEOP_DEAD_ZONE));

        LEFT = (TalonFXWrapper) MotorConfigs.talons.get("driveLeft");
        RIGHT = (TalonFXWrapper) MotorConfigs.talons.get("driveRight");

        LEFT.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 200);
        RIGHT.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 200);

        DriveDemand.circumference = WHEEL_CIRCUMFERENCE;

//        pigeon.setYawToCompass();
//        pigeon.setHeading(0);
//        odometry = new DifferentialDriveOdometry(new Rotation2d(pigeon.getHeading()));
    }

    private void teleopDrive(TeleopDriveType driveType) {
        limelight.setLights(true); // permanently on because it's outside
        if (SHOOTER_BUTTON.get()) {
            if (!isAiming) {
                isAiming = true;
                aimingTime = (0.022 * Math.abs(limelight.getValues().getTx())) + 0.1;
                aimStart = Timer.getFPGATimestamp();
            }

            double dt = Timer.getFPGATimestamp() - aimStart;
            if (dt <= aimingTime) {
                driveDemand = curvatureDrive.getDemand(0, 0, limelight.getSteer(), true);
            }
            else {
                driveDemand = new DriveDemand(0, 0);
                isAimed = true;
            }
        }
        else {
            isAiming = false;
            isAimed = false;

            if (driveType == TeleopDriveType.ARCADE) {
                driveDemand = arcadeDrive.getDemand(arcade_x.get(), arcade_y.get());
            }
            else {
                double steer = CURVATURE_STEER.get();
                if (PIVOT_BUTTON.get()) steer *= .8; // cap it so it's not too sensitive

                driveDemand = curvatureDrive.getDemand(CURVATURE_FORWARD.get(), CURVATURE_REVERSE.get(), steer, PIVOT_BUTTON.get());
            }
        }

        if (driveMode == DriveMode.VELOCITY) {
            driveDemand = velocityDrive.getVelocity(driveDemand); // convert from duty cycle to m/s
        }
    }

    @Override
    public void outputTelemetry() {
        double leftEncoder = LEFT.getEncoderPosition();
        double rightEncoder = RIGHT.getEncoderPosition();
        double leftVelocity = Units2813.motorRpmToDtVelocity(LEFT.getVelocity()); // rpm to m/s
        double rightVelocity = Units2813.motorRpmToDtVelocity(RIGHT.getVelocity());
        SmartDashboard.putNumber("Left Encoder", leftEncoder);
        SmartDashboard.putNumber("Right Encoder", rightEncoder);
        SmartDashboard.putNumber("Left Velocity", leftVelocity);
        SmartDashboard.putNumber("Right Velocity", rightVelocity);
        SmartDashboard.putString("Control Drive Mode", driveMode.toString());
//        SmartDashboard.putNumber("Gyro", pigeon.getHeading());
//        SmartDashboard.putString("Odometry", odometry.getPoseMeters().toString());
        SmartDashboard.putNumber("Limelight Angle", limelight.getValues().getTx());

        SmartDashboard.putNumber("Left Demand", driveDemand.getLeft());
        SmartDashboard.putNumber("Right Demand", driveDemand.getRight());
    }

    @Override
    public void teleopControls() {
        driveMode = ShuffleboardData.driveModeChooser.getSelected();
        if (driveMode == null) driveMode = DriveMode.OPEN_LOOP;
        teleopDrive(teleopDriveType);
    }

    @Override
    public void onEnabledStart(double timestamp) {
        setBrakeMode(true);
    }

    @Override
    public void onDisabledStart(double timestamp) {
        setBrakeMode(false);
    }

    @Override
    public void onEnabledLoop(double timestamp) {

    }

    @Override
    public void onEnabledStop(double timestamp) {

    }

    @Override
    public void zeroSensors() {
        LEFT.setEncoderPosition(0);
        RIGHT.setEncoderPosition(0);
        //pigeon.setHeading(0);
    }

    @Override
    protected void writePeriodicOutputs() {

        if (driveMode == DriveMode.VELOCITY) {
            DriveDemand demand = Units2813.dtDemandToMotorDemand(driveDemand); // converts m/s to rpm
            LEFT.set(ControlMode.VELOCITY, demand.getLeft(), feedforward.calculate(driveDemand.getLeft()) / 12);
            RIGHT.set(ControlMode.VELOCITY, demand.getRight(), feedforward.calculate(driveDemand.getRight()) / 12);
        }
        else {
            LEFT.set(driveMode.controlMode, driveDemand.getLeft());
            RIGHT.set(driveMode.controlMode, driveDemand.getRight());
        }
    }

    @Override
    protected void readPeriodicInputs() {
//        double leftDistance = Units2813.motorRevsToWheelRevs(LEFT.getEncoderPosition()) * WHEEL_CIRCUMFERENCE;
//        double rightDistance = Units2813.motorRevsToWheelRevs(RIGHT.getEncoderPosition()) * WHEEL_CIRCUMFERENCE;
//        robotPosition = odometry.update(Rotation2d.fromDegrees(pigeon.getHeading()), leftDistance, rightDistance);
    }

    public synchronized void setBrakeMode(boolean brake) {
        NeutralMode mode = !brake ? NeutralMode.Brake : NeutralMode.Coast;
        RIGHT.setNeutralMode(mode);
        LEFT.setNeutralMode(mode);
        System.out.println("Setting Brake Mode:" + brake);
    }

    public void setDemand(DriveDemand demand) {
        this.driveDemand = demand;
    }

    public enum DriveMode {
        OPEN_LOOP(ControlMode.DUTY_CYCLE),
        MOTION_MAGIC(ControlMode.MOTION_MAGIC),
        VELOCITY(ControlMode.VELOCITY);

        ControlMode controlMode;

        DriveMode(ControlMode controlMode) {
            this.controlMode = controlMode;
        }
    }
}
