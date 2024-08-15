import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.sound.sampled.*;

public class AudioPlayer {
    // current playing track index
    private int currentTrack = 0;
    private final List<File> files = new ArrayList<>();
    private Clip clip;
    private playerState currentState = playerState.UNINITIALIZED;
    private enum playerState {
        UNINITIALIZED,
        PlAYING,
        PAUSED,
        SKIPPING
    }

    public AudioPlayer(String dirPath) {
        // Getting `.wav` files from directory
        File directory = new File(dirPath);
        if (!directory.isDirectory()) {
            System.err.println("No such directory found.");
        }

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().endsWith(".wav")) {
                files.add(file);
            }
        }

        if (files.isEmpty()) {
            System.err.println("No audio files found in the directory.");
        } else {
            for (File file : files) {
                System.out.print(file.getName() + "\t");
            }
            System.out.println();
        }
    }

    // Method to play the audio
    public void playTrack() {
        if (currentState == playerState.PAUSED) {
            clip.start();
            currentState = playerState.PlAYING;
            return;
        }

        currentTrack = currentTrack % files.size();
        if (currentTrack < 0) {
            currentTrack = files.size() - 1;
        }

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
            clip.addLineListener(event -> {
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
        currentTrack++;
        currentState = playerState.SKIPPING;
        playTrack();
    }

    public void playPreviousTrack() {
        currentTrack--;
        currentState = playerState.SKIPPING;
        playTrack();
    }

    public void pauseTrack() {
        if (clip != null && clip.isRunning()) {
            currentState = playerState.PAUSED;
            clip.stop();
        }
    }
}
