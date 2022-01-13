package com.team2813.frc2022.util;

import com.team2813.frc2022.subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShuffleboardData {
    // define your smartdashboard entries here
    public static SendableChooser<Drive.DriveMode> driveModeChooser = new SendableChooser<>();
    public static SendableChooser<Drive.TeleopDriveType> teleopDriveTypeChooser = new SendableChooser<>();

    public static void init() {
        SmartDashboard.putData("Drive Mode", driveModeChooser);
        SmartDashboard.putData("Teleop Drive Type", teleopDriveTypeChooser);
    }
}
