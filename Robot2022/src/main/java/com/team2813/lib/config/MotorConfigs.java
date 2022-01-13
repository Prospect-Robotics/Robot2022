package com.team2813.lib.config;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.team2813.lib.motors.SparkMaxWrapper;
import com.team2813.lib.motors.TalonFXWrapper;
import com.team2813.lib.motors.TalonSRXWrapper;
import com.team2813.lib.motors.TalonWrapper;
import edu.wpi.first.wpilibj.Filesystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// singleton for motor configs
public class MotorConfigs {
    public static Map<String, SparkMaxWrapper> sparks = new HashMap<>();
    public static Map<String, TalonWrapper> talons = new HashMap<>();

    public static RootConfigs motorConfigs;

    private static List<Integer> ids = new ArrayList<>();

    public static void read() throws IOException {
        File deployDirectory = Filesystem.getDeployDirectory();
        File configFile = new File(deployDirectory.getAbsolutePath() + "/motorConfig.yaml");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        motorConfigs = mapper.readValue(configFile, RootConfigs.class);

        motorConfigs.getTalons().forEach(((s, talonConfig) -> talons.put(s, initializeTalon(talonConfig))));

        // TODO: Restore When Needed
        //motorConfigs.getSparks().forEach(((s, sparkConfig) -> sparks.put(s, initializeSpark(sparkConfig))));

        System.out.println("Successful!");
    }

    private static TalonWrapper initializeTalon(TalonConfig config) {
        for (Integer id : ids)
            if (id == config.getDeviceNumber()) {
                System.err.println("Tried to register talon with already used id");
            }
        ids.add(config.getDeviceNumber());

        System.out.println("Configuring" + config.getSubsystemName());

        TalonWrapper talon;

        if (config.getMotorControllerType() == MotorControllerType.TALON_FX) {
            talon = new TalonFXWrapper(config.getDeviceNumber(), config.getSubsystemName());
        } else {
            talon = new TalonSRXWrapper(config.getDeviceNumber(), config.getSubsystemName());
        }

        talon.setFactoryDefaults();

        talon.setPeakCurrentLimit(config.getPeakCurrentLimit());
        talon.enableVoltageCompensation();

        talon.setClosedLoopRamp(config.getClosedLoopRampRate());

//        talon.setStatusFramePeriod(config.getStatusFrame(), config.getStatusFramePeriod());
//					talon.setSmartMotionMaxVelocity(config.motionCruiseVelocity()); // FIXME: 09/20/2019 need to change parameters/types
//					talon.setSmartMotionMaxAccel(config.motionAcceleration()); // FIXME: 09/20/2019 need to change parameters/types

        talon.setCurrentLimit(config.getContinuousCurrentLimitAmps());// TODO check this is actually continuous limit
        talon.controller.configVoltageCompSaturation(12);

//			for (com.team2813.lib.talon.options.HardLimitSwitch hardLimitSwitch : field.getAnnotationsByType(com.team2813.lib.talon.options.HardLimitSwitch.class)) {
//				System.out.println("\tconfiguring hard limit switch " + hardLimitSwitch.direction());
//				// FIXME remake limit switch stuff differently since it is called differently -- Grady 10/30 I'm not sure this is how it works for Spark Maxs
//			}

//        for (LimitSwitchConfig limitSwitch : config.getLimitSwitches()) {
//            talon.setLimitSwitchSource(limitSwitch.direction, LimitSwitchSource.FeedbackConnector, limitSwitch.polarity.ctre);
//            talon.setClearPositionOnLimit(limitSwitch.direction, limitSwitch.clearOnLimit);
//            talon.enableLimitSwitches();
//        }
//
//        for (SoftLimitConfig softLimit : config.getSoftLimits()) {
//            talon.setSoftLimit(softLimit.direction, softLimit.threshold, softLimit.enable);
//            talon.setClearPositionOnLimit(softLimit.direction, softLimit.clearOnLimit);
//        }

//
//			for (com.team2813.lib.talon.options.SoftLimit softLimit : field.getAnnotationsByType(com.team2813co.lib.talon.options.SoftLimit.class)) {
//				System.out.println("\tconfiguring soft limit " + softLimit.direction());
//
//				//FIXME remake limit switch stuff differently
//			}

        for (
                PIDControllerConfig pidController : config.getPidControllers()) {
            int slotID = config.getPidControllers().indexOf(pidController);

            if (talon instanceof TalonFXWrapper) {
                ((TalonFXWrapper) talon).configEncoder(TalonFXFeedbackDevice.IntegratedSensor, TalonWrapper.PIDProfile.PRIMARY, 10);
            }

            System.out.println("Configuring PID: P=" + pidController.getP() + "I=" + pidController.getI() + "D=" + pidController.getD());

            talon.setPIDF(slotID, pidController.getP(), pidController.getI(),
                    pidController.getD(), pidController.getF());
            talon.setMotionMagicVelocity((int) pidController.getMaxVelocity()); // FIXME: 1/3/2020 Casting because
            // talon uses encoder ticks
            // TODO deal with units issue
            talon.setMotionMagicAcceleration((int) pidController.getMaxAcceleration()); // FIXME see above
            talon.controller.configAllowableClosedloopError(slotID, (int) pidController.getAllowableClosedLoopError());
            // TODO: 1/3/2020 figure out min velocity with Talons / remove from PID controller so as not to have that attribute
            talon.controller.configAllowableClosedloopError(slotID, (int) pidController.getAllowableClosedLoopError());
        }


        Inverted inverted = config.getInverted();
        if (inverted != null)
            talon.setInverted(inverted.value);

        for (FollowerConfig followerConfig : config.getFollowers()) {
            System.out.println(
                    "\tCreating follower w/ id of " + followerConfig.getId() + " on " + config.getSubsystemName()
            );

            TalonWrapper follower;

            if (followerConfig.getMotorControllerType() == MotorControllerType.TALON_FX) {
                follower = new TalonFXWrapper(followerConfig.getId(), config.getSubsystemName());
            } else {
                follower = new TalonSRXWrapper(followerConfig.getId(), config.getSubsystemName());
            }

            follower.follow(talon);

            switch (followerConfig.getInverted()) {
                case FollowMaster:
                    follower.setInverted(InvertType.FollowMaster);
                    break;
                case OpposeMaster:
                    follower.setInverted(InvertType.OpposeMaster);
                    break;
            }
            talon.slaves.add(follower);
        }
        return talon;
    }

    private static SparkMaxWrapper initializeSpark(SparkConfig config) {
        for (Integer id : ids)
            if (id == config.getDeviceNumber()) {
                System.err.println("Tried to register spark max with already used id");
            }

        ids.add(config.getDeviceNumber());

        System.out.println("Configuring " + config.getSubsystemName());

        SparkMaxWrapper spark = new SparkMaxWrapper(config.getDeviceNumber(), config.getSubsystemName(), config.getType().getValue());

        spark.setFactoryDefaults();
//        spark.setPeakCurrentDuration(config.peakCurrentDuration()); // FIXME: 09/20/2019 for the spark
        spark.setPeakCurrentLimit(config.getPeakCurrentLimit());
        spark.enableVoltageCompensation(config.getCompSaturationVoltage());
        spark.setClosedLoopRampRate(config.getClosedLoopRampRate());
        spark.setPeriodicFrame(config.getStatusFrame().getValue(), config.getStatusFramePeriod());

//					spark.setSmartMotionMaxVelocity(config.motionCruiseVelocity()); // FIXME: 09/20/2019 need to change parameters/types
//					spark.setSmartMotionMaxAccel(config.motionAcceleration()); // FIXME: 09/20/2019 need to change parameters/types

        spark.setSecondaryCurrentLimit(config.getContinuousCurrentLimitAmps());// TODO check this is actually continuous limit

//			for (com.team2813.lib.sparkMax.options.HardLimitSwitch hardLimitSwitch : field.getAnnotationsByType(com.team2813.lib.sparkMax.options.HardLimitSwitch.class)) {
//				System.out.println("\tconfiguring hard limit switch " + hardLimitSwitch.direction());
//				// FIXME remake limit switch stuff differently since it is called differently -- Grady 10/30 I'm not sure this is how it works for Spark Maxs
//			}
//
//			for (com.team2813.lib.sparkMax.options.SoftLimit softLimit : field.getAnnotationsByType(com.team2813.lib.sparkMax.options.SoftLimit.class)) {
//				System.out.println("\tconfiguring soft limit " + softLimit.direction());
//
//				//FIXME remake limit switch stuff differently
//			}

        for (PIDControllerConfig pidController : config.getPidControllers()) {

            int slotID = config.getPidControllers().indexOf(pidController);
            spark.setPIDF(slotID, pidController.getP(), pidController.getI(),
                    pidController.getD(), pidController.getF());
            spark.setMotionMagicVelocity(slotID, pidController.getMaxVelocity());
            spark.setMotionMagicAcceleration(slotID, pidController.getMaxAcceleration());
            spark.setMotionMagicMinOutputVelocity(slotID, pidController.getMinVelocity());
        }


        Inverted inverted = config.getInverted();

        if (inverted != null)
            spark.setInverted(inverted == Inverted.INVERTED);
        else
            spark.setInverted(SparkMaxWrapper.InvertType.NORMAL.inverted);

        for (FollowerConfig followerConfig : config.getFollowers()) {
            boolean isInverted = false;
            if ((followerConfig.getInverted() == InvertType.InvertMotorOutput) ||
                    (followerConfig.getInverted() == InvertType.OpposeMaster)) {
                isInverted = true;
            }

            System.out.println("\tCreating follower w/ id of " + followerConfig.getId() + " on " + config.getSubsystemName() + "and inverted: " + isInverted);
            SparkMaxWrapper follower = new SparkMaxWrapper(followerConfig.getId(), followerConfig.getType().getValue(), spark, isInverted);
            follower.setPeakCurrentLimit(config.getPeakCurrentLimit());
            spark.setPeakCurrentLimit(config.getPeakCurrentLimit());
            spark.setSecondaryCurrentLimit(config.getContinuousCurrentLimitAmps());// TODO check this is actually continuous limit
        }

        return spark;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public static class RootConfigs {
        private Map<String, SparkConfig> sparks = new HashMap<>();
        private Map<String, TalonConfig> talons = new HashMap<>();

        public Map<String, SparkConfig> getSparks() {
            return sparks;
        }

        public void setSparks(Map<String, SparkConfig> sparks) {
            this.sparks = sparks;
        }

        public Map<String, TalonConfig> getTalons() {
            return talons;
        }

        public void setTalons(Map<String, TalonConfig> talons) {
            this.talons = talons;
        }
    }
}
