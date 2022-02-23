package com.team2813.frc2022.auto;

import com.team2813.lib.auto.*;

import javax.annotation.processing.Generated;
import java.security.PublicKey;
import java.util.List;

public enum AutoTrajectories {
    Three_Ball_TerminalToTarmac(List.of(
            new GeneratedTrajectory("ThreeBall(Term-Tarmac) Intake Tarmac",false,0),
            new PauseTrajectory(1,1), // Shoot two balls
            new GeneratedTrajectory("ThreeBall(Term-Tarmac) Tarmac to Terminal",false,2),
            new PauseTrajectory(1,3), // Intake one Ball
            new GeneratedTrajectory("ThreeBall(Term-Tarmac) Terminal to Tarmac",false,4),
            new PauseTrajectory(1,5) // Shoot One Ball
    )),
    Three_Ball_Tarmac(List.of(
            new GeneratedTrajectory("ThreeBall Intake Tarmac",false,0),
            new PauseTrajectory(1,1), // Shoot two balls
            new GeneratedTrajectory("ThreeBall Tarmac Intake Tarmac", false, 2),
            new PauseTrajectory(1,3) // Shoot One Balls
    )),
    Four_Ball_Normal(List.of(
            new GeneratedTrajectory("FourBall Intake Shoot", false, 0),
            new PauseTrajectory(1,1), // Shoot two balls
            new GeneratedTrajectory("FourBall Tarmac to Terminal",false,2),
            new PauseTrajectory(1,3), // Intake One Ball in Front of Terminal
            new GeneratedTrajectory("FourBall Terminal to Tarmac", false, 4),
            new PauseTrajectory(1,5) // Shoot Two Balls
    )),
    FIVE_BALL(List.of(
            new GeneratedTrajectory("FiveBall Tarmac to Launch Pad", false, 0),
            new PauseTrajectory(1,1), // Shoot Two Balls from Launch Pad
            new GeneratedTrajectory("FiveBall Launch Pad to Terminal",false,2),
            new PauseTrajectory(1,3), // Intake Terminal Ball
            new GeneratedTrajectory("FiveBall Terminal to Tarmac",false,4),
            new PauseTrajectory(1,5), // Shoot Two Balls
            new GeneratedTrajectory("FiveBall Tarmac Intake Tarmac",false,6),
            new PauseTrajectory(1,7) // Shoot Last Ball
    )),
    ONE_BALL(List.of(
            new GeneratedTrajectory("OneBall High",false,0),
            new PauseTrajectory(1,1) //Intake Ball/Shoot
    )),
    ONE_BALL_MID(List.of(
            new GeneratedTrajectory("OneBall Mid",false,0),
            new PauseTrajectory(1,1)
    )),
    TWO_BALL(List.of(
            new GeneratedTrajectory("TwoBall Low",false,0),
            new PauseTrajectory(1,1) // Shoot Two Balls
    )),
    ZERO_BALL(List.of(
            new GeneratedTrajectory("ZeroBall High",false,0)
    ));
    
    private RamseteTrajectory trajectory;

    AutoTrajectories(List<AutoTrajectory> trajectory) {
        this.trajectory = new RamseteTrajectory(trajectory);
    }

    public RamseteTrajectory getTrajectory() {
        return trajectory;
    }
}
