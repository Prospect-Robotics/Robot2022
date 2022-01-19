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
        allSubsystems = List.of(DRIVE, INTAKE);
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
