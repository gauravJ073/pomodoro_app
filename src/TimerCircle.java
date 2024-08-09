import javax.swing.*;

import static java.lang.Thread.sleep;

public class TimerCircle {
    private int seconds;
    private final JProgressBar timer_component;

    TimerCircle(int seconds){
        this.seconds=seconds;
        this.timer_component = new JProgressBar(0, this.seconds);
        this.timer_component.setUI(new ProgressCircleUI());
        this.timer_component.setBorderPainted(false);
    }

    void setTime(int seconds){
        this.seconds=seconds;
    }

    int getTime(){
        return this.seconds;
    }

    JProgressBar getTimerComponent(){
        return this.timer_component;
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
            this.timer_component.setString(time);
            this.timer_component.setStringPainted(true);
            this.timer_component.setValue(i);
        }
    }
}
