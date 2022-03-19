package com.team2813.frc2022.auto;

import com.team2813.frc2022.util.AutoShootAction;
import com.team2813.lib.actions.*;
import com.team2813.lib.auto.GeneratedTrajectory;
import com.team2813.lib.auto.PauseTrajectory;
import com.team2813.lib.auto.RamseteTrajectory;
import com.team2813.lib.auto.RotateTrajectory;

import java.util.List;

import static com.team2813.frc2022.subsystems.Subsystems.INTAKE;

public enum AutoRoutine {
/*
 *  AutoRoutine action to wait for movement programmed by AutoTrajectories
 *
 * Example code from Robot2020:
 *  AutoTrajectory example:
 *      new GeneratedTrajectory("2-ball", true, 1),     //  Move from Pose 1 to Pose 2 in the Path called "2-ball"
 *      new PauseTrajectory(1,2),                       //  Wait at Pose 2 for some other action to happen in AutoRoutine
 *  AutoRoutine example:
 *      new LockAction(() -> AutoTrajectories.FIVE_BALL_ENEMY.getTrajectory().isCurrentTrajectory(2), true),  // Wait until robot reaches Pose 2
 *      new FunctionAction(() -> INTAKE.autoIntake(true), true),
 */

/*
 *  Our SeriesAction from the version of Auto we used at Ventura, called directly from Autonomous
 *  We need to make a new trajectory matching this for new auto paths, but we don't have exactly this path
 *  in the PathWeaver list in AutoTrajectories
 */
//    TWO_BALL_SIMPLE("Two Ball Auto Simple",
//            new SeriesAction(
//                new FunctionAction(() -> Subsystems.INTAKE.autoIntake(true), true),
//                new LockFunctionAction(() -> Subsystems.DRIVE.setDemand(forwardDemand), () -> distanceTraveled > 1.5, true),
//                new FunctionAction(() -> Subsystems.DRIVE.setDemand(stopDemand), true),
//                new WaitAction(1),
//                new FunctionAction(() -> Subsystems.INTAKE.autoIntake(false), true),
//                new LockFunctionAction(() -> Subsystems.DRIVE.setDemand(rotateDemand), () -> degreesRotated > 160, true),
//                new FunctionAction(() -> System.out.println("Finished rotating"), true),
//                new FunctionAction(() -> Subsystems.DRIVE.setDemand(stopDemand), true),
//                new LockFunctionAction(() -> Subsystems.DRIVE.setDemand(forwardDemand), () -> distanceTraveled < 1, true),
//                new AutoShootAction(),
//                new FunctionAction(() -> Subsystems.DRIVE.setDemand(stopDemand), true),
//                new FunctionAction(() -> Subsystems.SHOOTER.setShooter(0), true)
//            ), AutoTrajectories.TWO_BALL_SIMPLE),


 /*
  *  Trajectory from AutoTrajectories, generated by PathWeaver
  *
  *  Testing integrating AutoTrajectories and AutoRoutine into the Autonomous code
  *  This path doesn't do much, but should demonstrate that we know how to use the Trajectory and Routine code
  */
//    TWO_BALL_SIMPLE(List.of(
//            new GeneratedTrajectory("ZeroBall",false,0),      /*  Move the robot on the "zero ball" trajectory */
//            new PauseTrajectory(1,1), // Intake ball          /* when you arrive, intake the ball */
//            new RotateTrajectory(180, 2),                     /* then rotate */
//            new PauseTrajectory(1, 3) // Shoot two balls      /* then shoot */
//    )),


    TWO_BALL_SIMPLE("Two Ball Simple",
        new SeriesAction(
                new LockAction(() -> AutoTrajectories.TWO_BALL_SIMPLE.getTrajectory().isCurrentTrajectory(1), true), /*  wait until we arrive at Pose 1 from Trajectory */
                new FunctionAction(() -> INTAKE.autoIntake(true), true),    /* turn on intake, wait, turn off intake */
                new WaitAction(1),
                new FunctionAction(() -> INTAKE.autoIntake(false), true),
                new LockAction(() -> AutoTrajectories.TWO_BALL_SIMPLE.getTrajectory().isCurrentTrajectory(3), true),    /* wait for the turn to complete, Pose 3 */
                new AutoShootAction()       /* AutoShootAction spins up the shooter and Mag and then shoots - does it also turn off the shooter after? */
        ), AutoTrajectories.TWO_BALL_SIMPLE);


    private Action action;
    private RamseteTrajectory trajectory;
    public String name;

    AutoRoutine(String name, Action action, AutoTrajectories trajectory) {
        this.action = action;
        this.trajectory = trajectory.getTrajectory();
        this.name = name;
    }

    public Action getAction() {
        return action;
    }

    public RamseteTrajectory getTrajectory() {
        return trajectory;
    }
}
