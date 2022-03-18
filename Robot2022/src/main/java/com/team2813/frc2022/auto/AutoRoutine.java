package com.team2813.frc2022.auto;

import com.team2813.frc2022.subsystems.Subsystems;
import com.team2813.frc2022.util.AutoShootAction;
import com.team2813.lib.actions.*;
import com.team2813.lib.auto.RamseteTrajectory;

public enum AutoRoutine {
//        TWO_BALL_SIMPLE(List.of(
//            new GeneratedTrajectory("ZeroBall",false,0),
//            new PauseTrajectory(1,1), // Intake ball
//            new RotateTrajectory(180, 2),
//            new PauseTrajectory(1, 3) // Shoot two balls
//
//    AutoRoutine action to wait for movement programmed by AutoTrajectories
//    AT:
    //    new GeneratedTrajectory("2-ball", true, 1),   //  Move from Pose 1 to Pose 1 in the Path called "2-ball"
//    AR:
//      new LockAction(() -> AutoTrajectories.FIVE_BALL_ENEMY.getTrajectory().isCurrentTrajectory(2), true),  // Wait until robot reaches Pose 2

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

    ONE_METER_TEST("One Meter Test",
            new FunctionAction(() -> System.out.println("Testing One Meter"), true),
            AutoTrajectories.ONE_METER_TEST
    );


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
