import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int seconds=70;

        TimerCircle timer=new PomodoroTimer(seconds, 10);

        JFrame frame= new JFrame("Slider");
        Container contentPane=frame.getContentPane();

        JPanel panel= new JPanel();

        panel.add(timer);

        contentPane.add(panel);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        timer.startTimer();
        System.out.print("Hello and welcome!");
    }
}