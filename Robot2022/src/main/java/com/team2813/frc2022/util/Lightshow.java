package com.team2813.frc2022.util;

import com.ctre.phoenix.CANifier;

/*
ChannelA is green
ChannelB is red
ChannelC is blue
 */
public class Lightshow {
    private CANifier canifier;
    private Light light;

    public Lightshow(CANifier canifier) {
        this.canifier = canifier;
        setLight(Light.DEFAULT);
    }

    public void setLight(int r, int g, int b) {
        canifier.setLEDOutput(r / 255.0, CANifier.LEDChannel.LEDChannelB);
        canifier.setLEDOutput(g / 255.0, CANifier.LEDChannel.LEDChannelA);
        canifier.setLEDOutput(b / 255.0, CANifier.LEDChannel.LEDChannelC);
    }

    public void setLight(Light light) {
        setLight(light.r, light.g, light.b);
    }


    public enum Light {
        DEFAULT(255, 255, 255),
        ENABLED(0, 255, 0),
        DISABLED(255, 0, 0),
        AUTONOMOUS(255, 0, 255),
        READY_TO_SHOOT(0, 0, 255),
        SPOOLING(255, 255, 0),
        CLIMBING(0, 0, 128);

        int r;
        int g;
        int b;

        Light(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }
}
