import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int focusSeconds= 1500, breakSeconds= 300;

        PomodoroTimer timer=new PomodoroTimer(focusSeconds, breakSeconds, ".\\src\\audios\\alarms\\classic_alarm.wav");

        JFrame frame= new JFrame("Pomodoro App");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        PlayerComponent playerPanel = new PlayerComponent();

        JPanel timerPanel= new JPanel();
        timerPanel.add(timer);


        contentPane.add(timerPanel);
        contentPane.add(playerPanel);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        timer.startTimer();
    }
}