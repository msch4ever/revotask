package com.los.revotask;

import javax.sound.sampled.*;
import java.net.URL;

public class EasterEgg {

    private Clip clip;
    private AudioInputStream audioInputStream;

    public EasterEgg() {
        try {
            URL music = getClass().getResource("/EasterEgg.wav");
            audioInputStream = AudioSystem.getAudioInputStream(music);
            clip = AudioSystem.getClip();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void play() {
        try {
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void stop() {
        clip.stop();
        clip.close();
    }
}