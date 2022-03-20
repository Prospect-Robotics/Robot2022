package com.team2813.frc2022.subsystems;

import com.team2813.lib.controls.Axis;
import com.team2813.lib.controls.Button;
import com.team2813.lib.controls.Controller;

/**
 * Stores all of the controllers and their buttons and axes.
 * Controllers are private as they should never be accessed,
 * to make it easier to move a Button or Axis to a different
 * controller.
 *
 * To add a button or axis: construct a private Button or Axis
 * using controller.button(id). Then add a package-private
 * getter.
 */
public class SubsystemControlsConfig {
    // driver
	private static Controller driveJoystick = new Controller(0);
	private static Button pivotButton = driveJoystick.button(1);
	private static Axis driveX = driveJoystick.axis(0);
	private static Axis driveY = driveJoystick.axis(3);
	private static Axis driveSteer = driveJoystick.axis(0);
	private static Axis driveForward = driveJoystick.axis(3);
	private static Axis driveReverse = driveJoystick.axis(2);
    private static Button shooterButton = driveJoystick.button(4);
    private static Button manualShootButton = driveJoystick.button(2);
    private static Button lowShootButton = driveJoystick.button(3);

	// operator
	private static Controller operatorJoystick = new Controller(1);
	private static Button intakeInButton = operatorJoystick.button(6);
	private static Button intakeOutButton = operatorJoystick.button(5);
    private static Button spoolButton = operatorJoystick.button(7);
    private static Button extendButton = operatorJoystick.button(1);
    private static Button midClimbButton = operatorJoystick.button(2);
    private static Button riseButton = operatorJoystick.button(3);

    static Button getPivotButton() {
      return pivotButton;
    }

    static Axis getDriveX() {
      return driveX;
    }

    static Axis getDriveY() {
      return driveY;
    }

    static Axis getDriveSteer() {
      return driveSteer;
    }

    static Axis getDriveForward() {
      return driveForward;
    }

    static Axis getDriveReverse() {
      return driveReverse;
    }

    static Button getIntakeInButton() {
      return intakeInButton;
    }

    static Button getIntakeOutButton() {
      return intakeOutButton;
    }

    static Button getShooterButton() {
        return shooterButton;
    }

    static Button getManualShootButton() {
        return manualShootButton;
    }

    static Button getSpoolButton() {
        return spoolButton;
    }

    static Button getLowShootButton() {
        return lowShootButton;
    }

    static Button getMidClimbButton() {
        return midClimbButton;
    }

    static Button getRiseButton() {
        return riseButton;
    }

    static Button getExtendButton() {
        return extendButton;
    }
}
