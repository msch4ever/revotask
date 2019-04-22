package com.los.revotask;

import javax.sound.sampled.*;
import java.io.File;
import java.util.Scanner;

public class EasterEgg {

    private Clip clip;

    AudioInputStream audioInputStream;

    public EasterEgg() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File( "EasterEgg.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }

    public void runEasterEgg() {
        try {
            EasterEgg audioPlayer = new EasterEgg();
            audioPlayer.play();
            Scanner sc = new Scanner(System.in);
            sc.close();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }

    public void play() {
        clip.start();
    }
}