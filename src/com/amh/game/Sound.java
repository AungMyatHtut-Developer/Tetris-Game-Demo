package com.amh.game;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private Clip[] clips = new Clip[6];
    private URL[] urls = new URL[6];

    public Sound() {
        // Initialize the URLs for the sound files
        urls[0] = getClass().getResource("/sounds/drop2.wav");
        urls[1] = getClass().getResource("/sounds/change.wav");
        urls[2] = getClass().getResource("/sounds/clear.wav");
        urls[3] = getClass().getResource("/sounds/intro.wav");
        urls[4] = getClass().getResource("/sounds/backtrack.wav");
        urls[5] = getClass().getResource("/sounds/paused.wav");

        // Load each sound file into a clip
        for (int i = 0; i < urls.length; i++) {
            try {
                if (urls[i] != null) {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(urls[i]);
                    clips[i] = AudioSystem.getClip();
                    clips[i].open(audioInputStream);
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public void play(int index) {
        if (clips[index] != null) {
            if (clips[index].isRunning()) {
                clips[index].stop();  // Stop the clip if it’s already playing
            }
            clips[index].setFramePosition(0); // Rewind to the beginning
            clips[index].start(); // Start playing
        }
    }

    public void loop(int index) {
        if (clips[index] != null) {
            if (clips[index].isRunning()) {
                clips[index].stop();  // Stop the clip if it’s already playing
            }
            clips[index].setFramePosition(0); // Rewind to the beginning
            clips[index].loop(Clip.LOOP_CONTINUOUSLY); // Loop continuously
        }
    }

    public void stop(int index) {
        if (clips[index] != null) {
            clips[index].stop(); // Stop the clip
            clips[index].setFramePosition(0); // Rewind to the beginning
        }
    }
}
