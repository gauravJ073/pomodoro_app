import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.sound.sampled.*;

public class AudioPlayer {
    // current playing track index
    private int currentTrack = 0;

    // list of all .wav audio files in the specified directory
    private final List<File> files = new ArrayList<>();

    private Clip clip;

    // current state of the audio player
    private playerState currentState = playerState.UNINITIALIZED;

    // All possible player states
    public static enum playerState {
        UNINITIALIZED,
        PlAYING,
        PAUSED,
        SKIPPING
    }

    public AudioPlayer(String dirPath) {
        // Getting `.wav` files from directory
        File directory = new File(dirPath);

        // Checking if the specified path is directory
        if (!directory.isDirectory()) {
            System.err.println("No such directory found.");
        }

        // Adding .wav file to the list
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().endsWith(".wav")) {
                files.add(file);
            }
        }

        // Checking if there is a file to play or not
        if (files.isEmpty()) {
            System.err.println("No audio files found in the directory.");
        }
    }

    // Method to play the audio
    public void playTrack() {
        if (currentState == playerState.PlAYING) { // If the player is already PLAYING do nothing
            return;
        } else if (currentState == playerState.PAUSED) { // If the player is PAUSED resume the track
            currentState = playerState.PlAYING;
            clip.start();
            return;
        }

        // Ensuring playlist looping
        if (currentTrack >= files.size()) {
            currentTrack = 0;
        }
        if (currentTrack < 0) {
            currentTrack = files.size() - 1;
        }

        // If clip is Running stop it before playing another track
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            if (clip.isOpen()) {
                clip.close();
            }
        }

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(files.get(currentTrack));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.addLineListener(event -> { // Listens to STOP event on the Line
                // Automatically play next track only if the `clip` object stops while playing (not skipping)
                if (currentState == playerState.PlAYING && event.getType() == LineEvent.Type.STOP) {
                    playNextTrack();
                }
            });
            currentState = playerState.PlAYING;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void playNextTrack() {
        currentTrack = currentTrack + 1;
        currentState = playerState.SKIPPING;
        playTrack();
    }

    public void playPreviousTrack() {
        currentTrack = currentTrack - 1;
        currentState = playerState.SKIPPING;
        playTrack();
    }

    public void pauseTrack() {
        if (clip != null && clip.isRunning()) {
            currentState = playerState.PAUSED;
            clip.stop();
        }
    }

    public int getCurrentTrack() {
        return currentTrack;
    }

    public playerState getState() {
        return this.currentState;
    }
}
