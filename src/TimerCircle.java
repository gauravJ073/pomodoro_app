import javax.swing.*;


import java.awt.*;

import static java.lang.Thread.sleep;

public class TimerCircle extends JProgressBar{
    private int seconds;
    private int currTime;
    protected boolean isRunning;
    protected boolean started;

    TimerCircle(int seconds){
        super(0, seconds);
        this.seconds=seconds;
        this.isRunning=false;
        this.started=false;
        this.currTime=0;
        this.setUI(new ProgressCircleUI());
        this.setBorderPainted(true);
        this.setForeground(new Color(102, 255, 102));//light green
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

    void startTimer() throws InterruptedException {
        this.setString(this.getTimeString(currTime));
        this.setStringPainted(true);
        for(;currTime<=this.seconds;){
            if(isRunning){
                String time=this.getTimeString(currTime);
                if(!this.getString().equals("Pause")){
                    this.setString(time);
                }
                this.setValue(currTime);
                currTime+=1;
                sleep(1000);
            }
            else{
                sleep(100);
            }

        }
    }
}
