package com.team2813.frc2022.subsystems;

import com.ctre.phoenix.CANifier;
import com.team2813.frc2022.util.Units2813;
import com.team2813.lib.config.MotorConfigs;
import com.team2813.lib.controls.Button;
import com.team2813.lib.motors.TalonFXWrapper;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Magazine extends Subsystem {
    // physical constants: TODO

    // mag should spin forward when shooter is being run, forward when intake is running forward, and backwards when intake is being run backwards.
    // motor controllers
    private final TalonFXWrapper MAGAZINE;

    // controllers
    private final Joystick OPERATOR_JOYSTICK = SubsystemControlsConfig.getOperatorJoystick();
    private final Button START_STOP_BUTTON = SubsystemControlsConfig.getMagButton();
    private final Button FORWARD_BUTTON = SubsystemControlsConfig.getMagForward();
    private final Button REVERSE_BUTTON = SubsystemControlsConfig.getMagReverse();
    private final Button AUTO_BUTTON = SubsystemControlsConfig.getAutoButton();
    private Demand demand;

    public Magazine() {
        MAGAZINE = (TalonFXWrapper) MotorConfigs.talons.get("magazine");
    }

    @Override
    public void outputTelemetry() {
        double magazineVelocity = Units2813.motorRevsToWheelRevs(MAGAZINE.getVelocity());
        // SmartDashboard.putNumber("Magazine Demand", demand);
        SmartDashboard.putNumber("Magazine Velocity", magazineVelocity);
    }

    @Override
    public void teleopControls() {
        // INTAKE_BUTTON.whenPressedReleased(() >= Units2813.wheelRevsToMotorRevs(demand,)
    }

    @Override
    public void onEnabledStart(double timestamp) {

    }

    @Override
    public void onEnabledLoop(double timestamp) {

    }

    @Override
    public void onEnabledStop(double timestamp) {

    }
}
