import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
    // current status of the player
    boolean paused = false;
    // current playing track index
    int currentTrack = 0;
    // current position on `currentTrack`
    Long currentMicrosecondPosition = 0L;

    // list of audio files to play from
    File[] files;

    Clip clip;
    AudioInputStream audioInputStream;

    public AudioPlayer(String dirPath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // Getting `.wav` files from directory
        File directory = new File(dirPath);
        files = directory.listFiles(pathname -> pathname.getPath().endsWith(".wav"));

        if (files != null) {
            audioInputStream = AudioSystem.getAudioInputStream(files[currentTrack]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream); // open audioInputStream to the clip
        }
    }

    private void getPlayer(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        clip.close();
        audioInputStream = AudioSystem.getAudioInputStream(file);
        clip.open(audioInputStream); // open audioInputStream to the clip
    }

    // Method to play the audio
    public void play() {
        currentMicrosecondPosition = 0L;
        clip.start();
        paused = false;
    }

    // Method to pause the audio
    public void pause() {
        if (paused) {
            System.err.println("audio is already paused");
            return;
        }
        currentMicrosecondPosition = clip.getMicrosecondPosition();
        clip.stop();
        paused = true;
    }

    // Method to resume the audio
    public void resume() {
        if (!paused) {
            System.err.println("audio is already being played");
            return;
        }
        clip.setMicrosecondPosition(currentMicrosecondPosition);
        this.play();
    }

    // Method to restart the audio
    public void restart() {
        currentMicrosecondPosition = 0L;
        clip.setMicrosecondPosition(currentMicrosecondPosition);
        if (paused) {
            this.play();
        }
    }

    // Method to stop the audio
    public void stop() {
        currentMicrosecondPosition = 0L;
        clip.stop();
        clip.close();
    }

    // Method to jump over a specific part
    public void jump(long second) {
        if (second >= 0 && second < clip.getMicrosecondLength()) {
            clip.stop();
            clip.close();
            currentMicrosecondPosition = second * 1000000;
            clip.setMicrosecondPosition(second);
            this.play();
        }
    }

    // Method to move to next track
    public void next() {
        currentTrack = (currentTrack+1) % files.length;
        try {
            getPlayer(files[currentTrack]);
            this.play();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void previous() {
        currentTrack = (currentTrack - 1) % files.length;
        if (currentTrack < 0) currentTrack = 0;
        try {
            getPlayer(files[currentTrack]);
            this.play();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
