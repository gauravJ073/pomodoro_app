import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class PlayerComponent extends JPanel {
    File directory = new File("D:\\Dev\\Java\\pomodoro_app\\test_resources\\");
    AudioPlayer player;
    public PlayerComponent() {
        player = new AudioPlayer(directory);
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
        chooseDirButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser(directory);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                int option = chooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    if (chooser.getSelectedFile().isDirectory()) {
                        directory = chooser.getSelectedFile();
                        player = player.resetDirectory(directory);
                        playButton.setText("▶️");
                    }
                }
            }
        });

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
