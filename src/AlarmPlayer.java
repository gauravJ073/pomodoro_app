import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;

public class AlarmPlayer extends AudioPlayer{
    private File alarmFile;
    private Clip clip;
    AlarmPlayer(String filePath){
        super(new File((filePath)).getParentFile());
        this.alarmFile=new File(filePath);
        if (!alarmFile.exists() || !alarmFile.getName().endsWith(".wav")) {
            throw new IllegalArgumentException("Invalid alarm file: " + filePath);
        }

    }
    @Override
    public void playTrack() {
        if (getState() == playerState.PlAYING) {
            return; // If already playing, do nothing
        }

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(alarmFile);
            this.clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            if (getTrackChangeListener() != null) {
                getTrackChangeListener().onTrackChange((int)clip.getMicrosecondLength()/1000);
                getTrackChangeListener().onTrackPlay();
            }

        } catch (Exception e) {
            System.err.println("Error playing alarm: " + e.getMessage());
        }
    }

    @Override
    public void playNextTrack() {
        // Do nothing, as we only have one track
    }

    @Override
    public void playPreviousTrack() {
        // Do nothing, as we only have one track
    }

    @Override
    public void pauseTrack() {
        // Alarms typically shouldn't be paused, so we'll stop it instead
        stopAlarm();
    }

    @Override
    public void setMilliSecondLength(int milliSecond) {
        // Do nothing, as we don't want to change the alarm's position
    }

    public void stopAlarm() {
        if (getState() == playerState.PlAYING) {
            Clip clip = getClip();
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
            if (getTrackChangeListener() != null) {
                getTrackChangeListener().onTrackPause();
            }
        }
    }
    private Clip getClip() {
        try {
            // Use reflection to access the private 'clip' field from the superclass
            java.lang.reflect.Field clipField = AudioPlayer.class.getDeclaredField("clip");
            clipField.setAccessible(true);
            return (Clip) clipField.get(this);
        } catch (Exception e) {
            System.err.println("Error accessing clip: " + e.getMessage());
            return null;
        }
    }
    private TrackChangeListener getTrackChangeListener() {
        try {
            // Use reflection to access the private 'trackChangeListener' field from the superclass
            java.lang.reflect.Field listenerField = AudioPlayer.class.getDeclaredField("trackChangeListener");
            listenerField.setAccessible(true);
            return (TrackChangeListener) listenerField.get(this);
        } catch (Exception e) {
            System.err.println("Error accessing trackChangeListener: " + e.getMessage());
            return null;
        }
    }

    @Override
    public int getMilliSecondLength() {
        return (int)(clip.getMicrosecondLength()/1000);
    }
}
