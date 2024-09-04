import javax.swing.*;


import java.awt.*;

import static java.lang.Thread.sleep;

public class TimerCircle extends JProgressBar{
    private int seconds;
    private int currTime;
    protected boolean isRunning;
    protected boolean started;
    private Color textColor = Color.BLACK;

    TimerCircle(int seconds){
        super(0, seconds);
        this.seconds=seconds;
        this.isRunning=false;
        this.started=false;
        this.currTime=0;
        this.setUI(new ProgressCircleUI());
        this.setBorderPainted(true);
        this.setForeground(new Color(102, 255, 102));//light green
        this.setStringPainted(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        // Turn off string painting for the super call
        boolean oldStringPainted = isStringPainted();
        setStringPainted(false);

        // Call the super method without string painting
        super.paintComponent(g);

        // Restore the original string painted state
        setStringPainted(oldStringPainted);

        // Custom string drawing with the progress bar's foreground color
        if (oldStringPainted) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground()); // Set the color for the string to match the progress bar's color

            FontMetrics fm = g2.getFontMetrics();
            String string = getString();
            int x = (getWidth() - fm.stringWidth(string)) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - fm.getDescent();

            g2.drawString(string, x, y);
            g2.dispose(); // Dispose of the graphics object to avoid memory leaks
        }
    }
    void setTime(int seconds){
        this.seconds=seconds;
    }

    int getTime(){
        return this.seconds;
    }

    int getCurrTime(){
        return this.currTime;
    }

    void setCurrTime(int time){
        this.currTime=time;
    }

    String getTimeString(int sec){
        int min=sec/60;
        int secs=sec%60;
        String time;
        if(min==0){
            time=Integer.toString(secs);
        }
        else{
            time=min+" : "+secs;
        }
        return time;
    }

}
