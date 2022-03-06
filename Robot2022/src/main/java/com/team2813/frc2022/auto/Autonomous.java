package com.team2813.frc2022.auto;

import com.team2813.frc2022.Robot;
import com.team2813.frc2022.subsystems.Subsystems;
import com.team2813.frc2022.util.ShuffleboardData;
import com.team2813.lib.auto.RamseteAuto;
import com.team2813.lib.drive.DriveDemand;

import edu.wpi.first.wpilibj.Timer;

/**
 * Class that contains methosd to run autonomous in {@link Robot} autonomousPeriodic()
 */
public class Autonomous {

    private RamseteAuto ramseteAuto;
    private DriveDemand prevDemand = new DriveDemand(0, 0);
    private DriveDemand halfForwardDemand = new DriveDemand(0.5, 0.5);
    private DriveDemand halfBackwardDemand = new DriveDemand(-0.5, -0.5);
    private DriveDemand stopDemand = new DriveDemand(0.0, 0.0);
    Timer matchTimer = new Timer();

    public void periodic() {
        // Testing auto - just drive forward, half speed
        DriveDemand demand = halfForwardDemand; // ramseteAuto.getDemand(Subsystems.DRIVE.robotPosition);
//        DriveDemand demand = ramseteAuto.getDemand(Subsystems.DRIVE.robotPosition);   // FLAG - debug only, uncomment when Odometry and Pigeon gyro are turned on
//        if (!demand.equals(prevDemand)) {
//            Subsystems.DRIVE.setDemand(demand);
//        }
//        prevDemand = demand;

        // Drive forward for 2 seconds
        // Each time through the control loop (20x/second):
        //  if (not end time)
        //     drive forward
        //  else
        //     stop


        double curTimeInMatch = matchTimer.get();
        double moveTimeIncrment = 2.0;
        double pauseTime = 1.0;
        double forwardToTime = moveTimeIncrment;   // Drive for 2 seconds
        double pauseToTime = forwardToTime + pauseTime;   // Drive for 2 seconds        double stopTime = 2.0;   // Drive for 2 seconds
        double backwardToTime = pauseToTime + moveTimeIncrment;   // Drive for 2 seconds        double stopTime = 2.0;   // Drive for 2 seconds
        double stopTime = backwardToTime;

        System.out.println("Got to Autonomous.periodic(" + curTimeInMatch + ", " + matchTimer.getFPGATimestamp() + ", " + matchTimer.hasElapsed((2.0) ) + ")");

        if (curTimeInMatch >= stopTime) {
            Subsystems.DRIVE.setDemand(stopDemand);
        } else if (curTimeInMatch < forwardToTime) {
            Subsystems.DRIVE.setDemand(halfForwardDemand);
        } else if (curTimeInMatch < pauseToTime) {
            Subsystems.DRIVE.setDemand(stopDemand);
        } else if (curTimeInMatch < backwardToTime) {
            Subsystems.DRIVE.setDemand(halfBackwardDemand);
        } else {
            Subsystems.DRIVE.setDemand(stopDemand);
        }
    }

    public void run() {
//        AutoRoutine routine = ShuffleboardData.routineChooser.getSelected();
//        ramseteAuto = new RamseteAuto(Subsystems.DRIVE.kinematics, routine.getTrajectory());
//
//        Subsystems.DRIVE.initAutonomous(ramseteAuto.initialPose());
//        Subsystems.LOOPER.addAction(routine.getAction());
        System.out.println("Got to Autonomous.run()");
        matchTimer.start();
    }

    public static void addRoutines() {
        for (AutoRoutine routine : AutoRoutine.values()) {
            ShuffleboardData.routineChooser.addOption(routine.name, routine);
        }
    }
}
