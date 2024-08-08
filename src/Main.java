import javax.swing.*;
import java.awt.*;
//import ProgressCircleUI;


public class Main {
    public static void main(String[] args) {
        JSlider slider=new JSlider(JSlider.HORIZONTAL);
        JProgressBar pb = new JProgressBar();
        pb.setUI(new ProgressCircleUI());
        pb.setValue(55);
        pb.setBorderPainted(false);

        JFrame frame= new JFrame("Slider");
        Container contentPane=frame.getContentPane();

        JPanel panel= new JPanel();
        panel.add(slider);
        panel.add(pb);
        contentPane.add(panel);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        System.out.print("Hello and welcome!");
    }
}