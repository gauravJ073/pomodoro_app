import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int seconds=10;

        TimerCircle timer=new TimerCircle(seconds);

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