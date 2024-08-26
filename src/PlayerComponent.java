import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerComponent extends JPanel {
    public PlayerComponent() {
        String directory = "D:\\Dev\\Java\\pomodoro_app\\test_resources\\";
        AudioPlayer player = new AudioPlayer(directory);
        setLayout(new GridLayout(2, 1));
        JPanel playerControls = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Media Controls
        JButton playButton = new JButton("▶️");
        playButton.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (player.getState() == AudioPlayer.playerState.UNINITIALIZED
                        || player.getState() == AudioPlayer.playerState.PAUSED) {
                    player.playTrack();
                    playButton.setText("⏸️");
                } else if (player.getState() == AudioPlayer.playerState.PlAYING) {
                    player.pauseTrack();
                    playButton.setText("▶️");
                }
            }
        });

        JButton nextButton = new JButton("⏭️");
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                player.playNextTrack();
            }
        });

        JButton prevButton = new JButton("⏮️");
        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                player.playPreviousTrack();
            }
        });

        // File Chooser
        JButton chooseDirButton = new JButton("📁");

        // Timeline
        JSlider audioTimeline = new JSlider();


        playerControls.add(playButton);
        playerControls.add(prevButton);
        playerControls.add(nextButton);
        playerControls.add(chooseDirButton);

        this.add(playerControls);
        this.add(audioTimeline);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        content.add(new PlayerComponent());
        frame.pack();
        frame.setVisible(true);
    }
}
