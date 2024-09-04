import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerComponent extends JPanel implements AudioPlayer.TrackChangeListener {
    File directory = new File("D:\\Dev\\Java\\pomodoro_app\\test_resources\\");
    AudioPlayer player;

    JButton playButton, nextButton, prevButton, chooseDirButton;
    JSlider audioTimeline;
    Timer timer;
    AtomicBoolean isProgrammaticallySettingSlider;
    public PlayerComponent() {
        player = new AudioPlayer(directory);
        setLayout(new GridLayout(2, 1));
        JPanel playerControls = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Media Controls
        playButton = new JButton("▶️");
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

        nextButton = new JButton("⏭️");
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                player.playNextTrack();
                playButton.setText("⏸️");
            }
        });

        prevButton = new JButton("⏮️");
        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                player.playPreviousTrack();
                playButton.setText("⏸️");
            }
        });

        // File Chooser
        chooseDirButton = new JButton("📁");
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
                        player.setTrackChangeListener(PlayerComponent.this);
                        playButton.setText("▶️");
                    }
                }
            }
        });

        // Timeline
        audioTimeline = new JSlider(0, 1, 0);
        isProgrammaticallySettingSlider = new AtomicBoolean(false);
        timer = new Timer(10, _ -> {
            isProgrammaticallySettingSlider.set(true);
            audioTimeline.setValue(player.getMilliSecondPosition());
            isProgrammaticallySettingSlider.set(false);
        });

        audioTimeline.addChangeListener(_ -> {
            if (isProgrammaticallySettingSlider.get()) {
                return;
            }
            if (audioTimeline.getValueIsAdjusting()) {
                timer.stop();
            } else if (!audioTimeline.getValueIsAdjusting()) {
                player.setMilliSecondLength(audioTimeline.getValue());
                timer.start();
            }
        });

        player.setTrackChangeListener(this);



        playerControls.add(playButton);
        playerControls.add(prevButton);
        playerControls.add(nextButton);
        playerControls.add(chooseDirButton);

        this.add(playerControls);
        this.add(audioTimeline);
    }

    @Override
    public void onTrackChange(int trackMilliSecondLength) {
        isProgrammaticallySettingSlider.set(true);
        audioTimeline.setValue(0);
        audioTimeline.setMaximum(trackMilliSecondLength);
        isProgrammaticallySettingSlider.set(false);
        timer.restart();
    }

    @Override
    public void onTrackPause() {
        timer.stop();
    }

    @Override
    public void onTrackPlay() {
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        content.add(new PlayerComponent());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
