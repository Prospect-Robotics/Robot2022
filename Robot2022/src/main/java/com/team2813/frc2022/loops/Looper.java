package com.team2813.frc2022.loops;

import com.team2813.frc2022.Robot.RobotMode;
import com.team2813.lib.actions.Action;
import com.team2813.lib.util.CrashTrackingRunnable;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.List;

/**
 * This code runs all of the robot's loops. Loop objects are stored in a List
 * object. They are started when the robot powers up and stopped after the
 * match.
 */
public class Looper {
    public final double period = 0.01; // this is the Looper Dt

    private boolean running;

    public RobotMode mode;

    private final Notifier notifier; // from WPILib
    private final List<Loop> loops;
    private final List<Action> actions;
    private final Object taskRunningLock = new Object();
    private double timestamp = 0;
    private double dt = 0;

    private double worstTime = 0; // the biggest time gap between iterations

    private final CrashTrackingRunnable runnable_ = new CrashTrackingRunnable() {
        @Override
        public void runCrashTracked() {
            synchronized (taskRunningLock) {
                if (running) {
                    double now = Timer.getFPGATimestamp();
                    Action.updateActions(actions, mode, now);

                    // Go through each of the subsystem loops and run appropriate
                    // enabled or disabled loop
                    for (Loop loop : loops) {
                        double start = Timer.getFPGATimestamp();
                        loop.onAnyLoop(timestamp);
                        if(mode == RobotMode.ENABLED) loop.onEnabledLoop_(timestamp);
                        else if(mode == RobotMode.DISABLED) loop.onDisabledLoop(timestamp);
                        double dt = Timer.getFPGATimestamp()-start;
                        if(dt >= worstTime) {
                            System.out.println(loop.getClass().getName()+" loop took "+dt+"Ms");
                            worstTime = dt;
                        }
                    }

                    dt = now - timestamp;
                    timestamp = now;
                }
            }
        }
    };

    public Looper(RobotMode mode) {
        // Using the notifier to tell the robot to run.
        notifier = new Notifier(runnable_);
        running = false;
        loops = new ArrayList<>();
        actions = new ArrayList<>();
        this.mode = mode;
    }

    /**
     * Set robot to a mode ENABLED or DISABLED
     * @param newMode the mode to set
     */
    public void setMode(RobotMode newMode) {
        synchronized (taskRunningLock) {
            timestamp = Timer.getFPGATimestamp();
            // some actions should not continue after disabling. Remove these.
            // Run the stop method of each loop.
            for (Loop loop : loops) {
                if(mode == RobotMode.ENABLED) loop.onEnabledStop(timestamp);
                else if(mode == RobotMode.DISABLED) loop.onDisabledStop(timestamp);
            }

            //Run the start method of each loop.
            for (Loop loop : loops) {
                if(mode == RobotMode.ENABLED) loop.onEnabledStart(timestamp);
                else if(mode == RobotMode.DISABLED) loop.onDisabledStart(timestamp);
            }
            mode = newMode;
        }
    }

    /**
     * @return the mode
     */
    public RobotMode getMode() {
        return mode;
    }

    public synchronized void addLoop(Loop loop) {
        synchronized (taskRunningLock) {
            loops.add(loop);
        }
    }

    public synchronized void addAction(Action action){
        synchronized (taskRunningLock) {
            action.start(timestamp);
            actions.add(action);
        }
    }

    public synchronized void removeAction(Action action) {
        synchronized (taskRunningLock) {
            action.end(timestamp);
            actions.remove(action);
        }
    }

    public synchronized void start() {
        if (!running) {
            notifier.startPeriodic(period);
            running = true;
        }
    }

    public synchronized void stop() {
        if (running) {
            notifier.stop();
            running = false;
        }
    }

    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("looper_dt", dt);
    }
}
