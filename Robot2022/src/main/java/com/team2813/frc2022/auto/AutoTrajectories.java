package com.team2813.frc2022.auto;

import com.team2813.lib.auto.*;

import java.util.List;

public enum AutoTrajectories {
    THREE_BALL_TERMINAL(List.of(
            new PauseTrajectory(1, 0), // Deploy and start intake
            new GeneratedTrajectory("ThreeBall(Term-Tarmac) Intake Tarmac",false,1),
            new PauseTrajectory(1,2), // Stop intake, shoot two balls
            new GeneratedTrajectory("ThreeBall(Term-Tarmac) Tarmac to Terminal",false,3),
            new PauseTrajectory(1,4), // Intake one ball
            new GeneratedTrajectory("ThreeBall(Term-Tarmac) Terminal to Tarmac",false,5),
            new PauseTrajectory(1,6) // Shoot one ball
    )),
    THREE_BALL_TARMAC(List.of(
            new PauseTrajectory(1, 0), // Deploy and start intake
            new GeneratedTrajectory("ThreeBall Intake Tarmac",false,1),
            new PauseTrajectory(1,2), // stop intake, shoot two balls, start intake
            new GeneratedTrajectory("ThreeBall Tarmac Intake Tarmac", false, 3),
            new PauseTrajectory(1,4) // stop intake, shoot ball
    )),
    FOUR_BALL(List.of(
            new PauseTrajectory(1, 0), // Deploy and start intake
            new GeneratedTrajectory("FourBall Intake Shoot", false, 1),
            new PauseTrajectory(1,2), // Stop intake, shoot two balls
            new GeneratedTrajectory("FourBall Tarmac to Terminal",false,3),
            new PauseTrajectory(1,4), // Intake one ball in front of Terminal (keep intake running afterwards)
            new GeneratedTrajectory("FourBall Terminal to Tarmac", false, 5),
            new PauseTrajectory(1,6) // Stop intake, shoot two balls
    )),
    FOUR_BALL_ALT(List.of(
            new GeneratedTrajectory("FourBall(Alt) Tarmac Intake", false, 0),
            new PauseTrajectory(1, 1), // Intake ball
            new GeneratedTrajectory("FourBall(Alt) Intake to Shoot", false, 2),
            new PauseTrajectory(1, 3), // Shoot two balls
            new GeneratedTrajectory("FourBall(Alt) Tarmac to Terminal", false, 4),
            new PauseTrajectory(1, 5), // Intake ball (keep intake running afterwards)
            new GeneratedTrajectory("FourBall(Alt) Terminal to Shoot (Near)", false, 6) // Stop intake, shoot two balls
    )),
    FOUR_BALL_ALT_SQUARED(List.of(
            new GeneratedTrajectory("FourBall(Alt) Tarmac Intake", false, 0),
            new PauseTrajectory(1, 1), // Intake ball
            new GeneratedTrajectory("FourBall(Alt) Intake to Shoot", false, 2),
            new PauseTrajectory(1, 3), // Shoot two balls
            new GeneratedTrajectory("FourBall(Alt) Tarmac to Terminal", false, 4),
            new PauseTrajectory(1, 5), // Intake ball (keep intake running afterwards)
            new GeneratedTrajectory("FourBall(Alt) Terminal to Shoot (Far)", false, 6) // Stop intake, shoot two balls
    )),
    FIVE_BALL(List.of(
            new PauseTrajectory(1, 0), // Deploy and start intake
            new GeneratedTrajectory("FiveBall Tarmac to Launch Pad", false, 0),
            new PauseTrajectory(1,1), // Stop intake, shoot two balls from Launch Pad
            new GeneratedTrajectory("FiveBall Launch Pad to Terminal",false,2),
            new PauseTrajectory(1,3), // Intake terminal ball (keep intake running afterwards)
            new GeneratedTrajectory("FiveBall Terminal to Tarmac",false,4),
            new PauseTrajectory(1,5), // Shoot two balls
            new GeneratedTrajectory("FiveBall Tarmac Intake Tarmac",false,6),
            new PauseTrajectory(1,7) // Stop intake, shoot last ball
    )),
    TWO_BALL_SIMPLE(List.of(
            new GeneratedTrajectory("ZeroBall",false,0),
            new PauseTrajectory(1,1), // Intake ball
            new RotateTrajectory(180, 2),
            new PauseTrajectory(1, 3) // Shoot two balls
    )),
    TWO_BALL(List.of(
            new PauseTrajectory(1, 0), // Deploy and start intake
            new GeneratedTrajectory("TwoBall",false,1),
            new PauseTrajectory(1,2) // Stop intake, shoot two balls
    )),
    ONE_BALL(List.of(
            new GeneratedTrajectory("ZeroBall", true, 0),
            new PauseTrajectory(1, 1) // Shoot ball
    )),
    ZERO_BALL(List.of(
            new GeneratedTrajectory("ZeroBall",false,0)
    )),
    ONE_METER_TEST(List.of(
            new PauseTrajectory(1, 0),
            new GeneratedTrajectory("ZeroBall", true, 1)
    ));
    
    private RamseteTrajectory trajectory;

    AutoTrajectories(List<AutoTrajectory> trajectory) {
        this.trajectory = new RamseteTrajectory(trajectory);
    }

    public RamseteTrajectory getTrajectory() {
        return trajectory;
    }
}
