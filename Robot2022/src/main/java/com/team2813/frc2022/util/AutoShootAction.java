package com.team2813.frc2022.util;

import com.team2813.lib.actions.*;
import com.team2813.lib.drive.DriveDemand;
import edu.wpi.first.wpilibj.Timer;

import static com.team2813.frc2022.subsystems.Subsystems.*;

public class AutoShootAction extends SeriesAction {
    private static Limelight limelight = Limelight.getInstance();
    private static boolean isAiming = false;
    private static double aimingTime = 0;
    private static double aimStart = 0;

    public AutoShootAction() {
        super(
               new LockAction(() -> {
                   if (!isAiming) {
                       isAiming = true;
                       limelight.setLights(true);
                       aimingTime = (0.022 * Math.abs(limelight.getValues().getTx())) + 0.5;
                       aimStart = Timer.getFPGATimestamp();
                   }

                   double dt = Timer.getFPGATimestamp() - aimStart;
                   DRIVE.setDemand(DRIVE.curvatureDrive.getDemand(0, 0, limelight.getSteer(), true));
                   return dt >= aimingTime;
               }, true),
               new FunctionAction(() -> DRIVE.setDemand(new DriveDemand(0, 0)), true),
               new LockFunctionAction(() -> SHOOTER.setShooter(limelight.getShooterDemand()), SHOOTER::isFlywheelReady,  true),
               new WaitAction(0.5),
               new FunctionAction(() -> MAGAZINE.autoShoot(true), true),
               new WaitAction(2),
               new FunctionAction(() -> MAGAZINE.autoShoot(false), true)
        );
    }
}
