import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws InterruptedException {
        AudioPlayer player;
        try {
            player = new AudioPlayer("D:\\Dev\\Java\\pomodoro_app\\test_resources\\");
            for (;;) {
                System.out.println("1. Play\n2. Pause\n3. Next\n4. Previous");
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();
                switch(option) {
                    case 1 : player.playTrack();
                        break;
                    case 2 : player.pauseTrack();
                        break;
                    case 3 : player.playNextTrack();
                        break;
                    case 4 : player.playPreviousTrack();
                        break;
                    default:
                        System.exit(0);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}