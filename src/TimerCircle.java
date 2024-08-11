import javax.swing.*;

import static java.lang.Thread.sleep;

public class TimerCircle extends JProgressBar{
    private int seconds;

    TimerCircle(int seconds){
        super(0, seconds);
        this.seconds=seconds;
        this.setUI(new ProgressCircleUI());
        this.setBorderPainted(false);
    }

    void setTime(int seconds){
        this.seconds=seconds;
    }

    int getTime(){
        return this.seconds;
    }

    private String getTimeString(int sec){
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

    void startTimer() throws InterruptedException {
        for(int i =1;i<=this.seconds;i+=1){
            String time=this.getTimeString(i);
            sleep(1000);
            this.setString(time);
            this.setStringPainted(true);
            this.setValue(i);
        }
    }
}
