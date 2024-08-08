import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class PlayerManager {
    ArrayList<AudioPlayer> players = new ArrayList<>();

    // constructor to initialize the player list
    public PlayerManager(String dirPath) {
        File directory = new File(dirPath);

        // getting Files in directory that end with `.wav`
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getPath().endsWith(".wav");
            }
        });
        if (files != null) {
            for (File file : files) {
                try {
                    AudioPlayer player = new AudioPlayer(file);
                    players.addLast(player);
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }
}
