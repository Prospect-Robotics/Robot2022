package com.team2813.frc2022.subsystems;

import com.team2813.frc2022.Robot.RobotMode;
import com.team2813.frc2022.loops.Loop;
import com.team2813.frc2022.loops.Looper;

import java.util.List;

/**
 * Stores a list of all subsystems, initializes them, and
 * runs a loop to put things on the SmartDashboard.
 */
public class Subsystems {

    public static List<Subsystem> allSubsystems;
    public static Drive DRIVE;
    public static Intake INTAKE;
    public static Shooter SHOOTER;
    public static Magazine MAGAZINE;
    public static Climber CLIMBER;

    public static final Looper LOOPER = new Looper(RobotMode.DISABLED);

    private static final class SmartDashboardLoop implements Loop {
        int currentSubsystem = 0;

        @Override
        public void onAnyLoop(double timestamp) {
            if (allSubsystems.size() == 0) return;
            if (currentSubsystem >= allSubsystems.size()) currentSubsystem = 0;
            allSubsystems.get(currentSubsystem).outputTelemetry();
            currentSubsystem++;
        }
    }

    public static void initializeSubsystems() {
        DRIVE = new Drive();
        INTAKE = new Intake();
        SHOOTER = new Shooter();
        MAGAZINE = new Magazine();
        CLIMBER = new Climber();
        allSubsystems = List.of(DRIVE, INTAKE, SHOOTER, MAGAZINE, CLIMBER);
        LOOPER.addLoop(new SmartDashboardLoop());
    }

    /**
     * Calls each subsystem's teleopControls()
     */
    public static void teleopControls() {
        for (Subsystem subsystem : allSubsystems) {
            subsystem.teleopControls();
        }
    }

    public static void outputTelemetry() {
        for (Subsystem subsystem : allSubsystems) {
            subsystem.outputTelemetry();
        }
    }
}
