package com.team2813.frc2022.auto;

import com.team2813.frc2022.Robot;
import com.team2813.frc2022.subsystems.Subsystems;
import com.team2813.frc2022.util.AutoShootAction;
import com.team2813.frc2022.util.ShuffleboardData;
import com.team2813.lib.actions.*;
import com.team2813.lib.auto.RamseteAuto;
import com.team2813.lib.drive.DriveDemand;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.team2813.frc2022.subsystems.Subsystems.LOOPER;

/**
 * Class that contains methosd to run autonomous in {@link Robot} autonomousPeriodic()
 */
public class Autonomous {

    private RamseteAuto ramseteAuto;
    private DriveDemand prevDemand = new DriveDemand(0, 0);
//    private DriveDemand forwardDemand = new DriveDemand(0.25, 0.25);        //  %age of full speed (no Ramsete)
    private DriveDemand forwardDemand = new DriveDemand(1, 1);      //  m/s (Ramsete)
    private DriveDemand backwardDemand = new DriveDemand(-0.25, -0.25);
//    private DriveDemand rotateDemand = new DriveDemand(-0.25, 0.25);
    private DriveDemand rotateDemand = new DriveDemand(-1, 1);      // m/s (Ramsete)
    private DriveDemand stopDemand = new DriveDemand(0.0, 0.0);
    private double distanceTraveled = 0;
    private double degreesRotated = 0;
    private Pose2d initialPose = new Pose2d(0, 0, new Rotation2d(0));
//    Timer matchTimer = new Timer();

    public void periodic() {
        // Testing auto - just drive forward, half speed
        // DriveDemand demand = forwardDemand;      // FLAG - replaces getDemand, below, when Odometry is turned off
//        ramseteAuto.getDemand(Subsystems.DRIVE.robotPosition);
//        DriveDemand demand = ramseteAuto.getDemand(Subsystems.DRIVE.robotPosition);   // FLAG - use when Odometry and Pigeon gyro are turned on; comment out if no Odometry
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


//        double curTimeInMatch = matchTimer.get();
//        double moveTimeIncrment = 2.0;
//        double pauseTime = 1.0;
//        double forwardToTime = moveTimeIncrment;   // Drive for 2 seconds
//        double pauseToTime = forwardToTime + pauseTime;   // Drive for 2 seconds        double stopTime = 2.0;   // Drive for 2 seconds
//        double backwardToTime = pauseToTime + moveTimeIncrment;   // Drive for 2 seconds        double stopTime = 2.0;   // Drive for 2 seconds
//        double stopTime = backwardToTime;
//
//        System.out.println("Got to Autonomous.periodic(" + curTimeInMatch + ", " + matchTimer.getFPGATimestamp() + ", " + matchTimer.hasElapsed((2.0) ) + ")");
//
        distanceTraveled = Subsystems.DRIVE.robotPosition.getTranslation().getDistance(initialPose.getTranslation());
        SmartDashboard.putNumber("Distance Traveled", distanceTraveled);
        degreesRotated = Subsystems.DRIVE.robotPosition.getRotation().getDegrees();
//
//        System.out.println("Rotation: " + degreesRotated + " Distance traveled: " + distanceTraveled);

//        if (curTimeInMatch >= stopTime) {
//            Subsystems.DRIVE.setDemand(stopDemand);
//        } else if (curTimeInMatch < forwardToTime) {
//            Subsystems.DRIVE.setDemand(halfForwardDemand);
//        } else if (curTimeInMatch < pauseToTime) {
//            Subsystems.DRIVE.setDemand(stopDemand);
//        } else if (curTimeInMatch < backwardToTime) {
//            Subsystems.DRIVE.setDemand(halfBackwardDemand);
//        } else {
//            Subsystems.DRIVE.setDemand(stopDemand);
//        }
    }

    public void run() {
//        AutoRoutine routine = ShuffleboardData.routineChooser.getSelected();
//        ramseteAuto = new RamseteAuto(Subsystems.DRIVE.kinematics, routine.getTrajectory());
//
//        Subsystems.DRIVE.initAutonomous(ramseteAuto.initialPose());
//        Subsystems.LOOPER.addAction(routine.getAction());
        Subsystems.DRIVE.initAutonomous(initialPose);   // FLAG - Believe this can be removed

        // One ball auto code
//        Action autoAction = new SeriesAction(
//                new LockFunctionAction(() -> Subsystems.DRIVE.setDemand(backwardDemand), () -> distanceTraveled > 1, true),
//                new FunctionAction(() -> Subsystems.DRIVE.setDemand(stopDemand), true),
//                new AutoShootAction(),
//                new FunctionAction(() -> Subsystems.SHOOTER.setShooter(0), true)
//        );
//        LOOPER.addAction(autoAction);

        /*
         *  Below is the 2-ball Auto code we used successfully at Ventura Regional
         *  Working now to integrate this SeriesAction into AutonomousRoutine with a corresponding
         *  Trajectory in AutonomousTrajectories
         */
        // Two ball auto code
        Action autoAction = new SeriesAction(
                new FunctionAction(() -> Subsystems.INTAKE.autoIntake(true), true),
                new LockFunctionAction(() -> Subsystems.DRIVE.setDemand(forwardDemand), () -> distanceTraveled > 1.5, true),
                new FunctionAction(() -> Subsystems.DRIVE.setDemand(stopDemand), true),
                new WaitAction(1),
                new FunctionAction(() -> Subsystems.INTAKE.autoIntake(false), true),
                new LockFunctionAction(() -> Subsystems.DRIVE.setDemand(rotateDemand), () -> degreesRotated > 160, true),
                new FunctionAction(() -> System.out.println("Finished rotating"), true),
                new FunctionAction(() -> Subsystems.DRIVE.setDemand(stopDemand), true),
                new LockFunctionAction(() -> Subsystems.DRIVE.setDemand(forwardDemand), () -> distanceTraveled < 1, true),
                new AutoShootAction(),
                new FunctionAction(() -> Subsystems.DRIVE.setDemand(stopDemand), true),
                new FunctionAction(() -> Subsystems.SHOOTER.setShooter(0), true)
        );
        LOOPER.addAction(autoAction);
//
//        System.out.println("Got to Autonomous.run()");
//        matchTimer.start();
    }

    public static void addRoutines() {
        for (AutoRoutine routine : AutoRoutine.values()) {
            ShuffleboardData.routineChooser.addOption(routine.name, routine);
        }
    }
}
