package com.team2813.frc2022.subsystems;

import com.team2813.frc2022.util.Units2813;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Axis;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.interfaces.ControlMode;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Magezine extends Subsystems {
    // physical constants
    // motor controllers
    private final TalonFXWrapper MOTOR; 

    private final Button PIVOT_BUTTON = SubsystemControlsConfig.getPivotButton();
    private final Axis DRIVE_X = SubsystemControlsConfig.getDriveX();
    private final Axis DRIVE_Y = SubsystemControlsConfig.getDriveY();
    private final Axis DRIVE_FORWARD = SubsystemControlsConfig.getDriveForward();
    private final Axis DRIVE_REVERSE = SubsystemControlsConfig.getDriveReverse();

    // private Demand demand;

    public int ammo = 0;
    public boolean triggered = false;
    public boolean ballDetected = false;




    //motor wrappers
    //controllers
    
    //yaml file

}
