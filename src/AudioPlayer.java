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

    // to store current position
    Long currentFrame;
    Clip clip;

    AudioInputStream audioInputStream;
    String filePath;

    // constructor to initialize streams and clip
    public AudioPlayer(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.filePath = filePath;
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Method to play the audio
    public void play() {
        //start the clip
        System.out.println(clip.getFramePosition());
        clip.start();

        paused = false;
    }

    // Method to pause the audio
    public void pause() {
        if (paused) {
            System.out.println("audio is already paused");
            return;
        }
        currentFrame = clip.getMicrosecondPosition();
        clip.stop();
        paused = true;
    }

    // Method to resume the audio
    public void resume() {
        if (!paused) {
            System.out.println("Audio is already " + "being played");
            return;
        }
        clip.close();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    // Method to restart the audio
    public void restart() {
        currentFrame = 0L;
        clip.setMicrosecondPosition(currentFrame);
        if (paused) {
            this.play();
        }
    }

    // Method to stop the audio
    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    // Method to jump over a specific part
    public void jump(long second) {
        if (second > 0 && second < clip.getMicrosecondLength()) {
            clip.stop();
            clip.close();
            currentFrame = second;
            clip.setMicrosecondPosition(second);
            this.play();
        }
    }

}
