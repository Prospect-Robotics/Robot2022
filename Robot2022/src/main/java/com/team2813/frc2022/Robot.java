// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2813.frc2022;

import com.team2813.frc2022.subsystems.Subsystem;
import com.team2813.frc2022.subsystems.Subsystems;
import com.team2813.frc2022.util.Limelight;
import com.team2813.frc2022.util.ShuffleboardData;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.drive.DriveDemand;
import com.team2813.lib.util.CrashTracker;
import com.team2813.lib.util.LimelightValues;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;

import static com.team2813.frc2022.subsystems.Subsystems.*;


/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{

    private static final double MIN_IDLE_VOLTAGE = 11.7;
    private static final double MIN_DISABLED_VOLTAGE = 12;
    private static boolean BATTERY_TOO_LOW = false;
    private final double WHEEL_DIAMETER = 4; // inches

    public final LimelightValues limelightValues = new LimelightValues();
    private Limelight limelight = Limelight.getInstance();

    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit()
    {
        try {
            CrashTracker.logRobotInit();
            MotorConfigs.read();
            System.out.println("Motor Config Successful");
            Subsystems.initializeSubsystems();
            System.out.println("Subsystem Initialization Successful");
            System.out.println("Auto Constructed");
            System.out.println("AutoRoutine Initialization Successful");
            ShuffleboardData.init();

            DriveDemand.circumference = Math.PI * WHEEL_DIAMETER;
            for (Subsystem subsystem : allSubsystems) {
                LOOPER.addLoop(subsystem);
                subsystem.zeroSensors();
            }
            limelight.setLights(false);


        }
        catch (IOException e) {
            System.out.println("Something went wrong while reading config files!");
            CrashTracker.logThrowableCrash(e);
            e.printStackTrace();
            System.out.println("ERROR WHEN READING CONFIG");
            e.printStackTrace();
        }
        catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
    }
    
    
    /**
     * This method is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        boolean disabled = DriverStation.getInstance().isDisabled();
        double voltage = RobotController.getBatteryVoltage();
        SmartDashboard.putBoolean("Replace Battery if Red", disabled ? voltage > MIN_DISABLED_VOLTAGE : voltage > MIN_IDLE_VOLTAGE);

        Subsystems.outputTelemetry();
        BATTERY_TOO_LOW = disabled && voltage > MIN_DISABLED_VOLTAGE;
        SmartDashboard.putBoolean("Replace Battery if Red", disabled ? voltage > MIN_DISABLED_VOLTAGE : voltage > MIN_IDLE_VOLTAGE);

        limelightValues.update();
    }
    
    
    /**
     * This autonomous (along with the chooser code above) shows how to select between different
     * autonomous modes using the dashboard. The sendable chooser code works with the Java
     * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
     * uncomment the getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to the switch structure
     * below with additional strings. If using the SendableChooser make sure to add them to the
     * chooser code above as well.
     */
    @Override
    public void autonomousInit()
    {

    }
    
    
    /** This method is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic()
    {

    }
    
    
    /** This method is called once when teleop is enabled. */
    @Override
    public void teleopInit() {
        try {
            System.out.println("teleopInit");

            CrashTracker.logTeleopInit();
            LOOPER.setMode(RobotMode.ENABLED);
            LOOPER.start();
            limelight.setLights(false);
            limelight.setStream(2);
        }
        catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            try {
                throw t;
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
    
    
    /** This method is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        Subsystems.teleopControls();
    }
    
    
    /** This method is called once when the robot is disabled. */
    @Override
    public void disabledInit() {
        try {
            CrashTracker.logDisabledInit();
            LOOPER.setMode(RobotMode.DISABLED);
            LOOPER.start();
        }
        catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
    }
    
    
    /** This method is called periodically when disabled. */
    @Override
    public void disabledPeriodic() {}
    
    
    /** This method is called once when test mode is enabled. */
    @Override
    public void testInit() {}
    
    
    /** This method is called periodically during test mode. */
    @Override
    public void testPeriodic() {}

    public enum RobotMode {
        DISABLED, ENABLED
    }
}
