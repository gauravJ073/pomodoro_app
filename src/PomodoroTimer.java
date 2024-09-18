import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Thread.sleep;

public class PomodoroTimer extends TimerCircle{
    private int focusTime;
    private int breakTime;
    public enum PomodoroStates {
        FOCUS,
        BREAK
    } // can be either focus or break
    private PomodoroStates state=PomodoroStates.FOCUS;
    private AlarmPlayer alarmPlayer;
    private boolean alarmPlaying=false;

    PomodoroTimer(int focusTime, int breakTime, String alarmFilePath){
        super(focusTime);
        this.focusTime=focusTime;
        this.breakTime=breakTime;
        this.alarmPlayer=new AlarmPlayer(alarmFilePath);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    skipTime();

                }else{
                    if(alarmPlaying){
                        stopAlarm();
                    }
                    toggleRunningState();
                    updateTextOnHover();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to pointer
                updateTextOnHover();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateTextOnExit();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Reset cursor to default
            }
        });
    }


    private void skipTime() {
        this.isRunning=false;
        this.setCurrTime(this.getTime());
        this.setString("TIME'S UP");
        switchStates();
    }
    private void updateTextOnHover() {
        if (isRunning) {
            this.setString("PAUSE");
        } else {
            if(!started){
                this.setString("START");
            }
            else{
                this.setString("CONTINUE");
            }
        }
    }
    private void updateTextOnExit() {
        if(started){
            this.setString(getTimeString(getCurrTime()));
        }
        else{
            if(state==PomodoroStates.FOCUS){
                this.setString("FOCUS TIME");
            }
            else{
                this.setString("BREAK TIME");
            }
        }
    }
    private void toggleRunningState() {
        isRunning = !isRunning;
        started=true;

    }

    void startTimer() throws InterruptedException {
        if(!started){
            this.setString("START");
        }
        else{
            this.setString(this.getTimeString(getCurrTime()));
        }
        this.setStringPainted(true);
        for(;getCurrTime()<=this.getTime();){
            if(isRunning){
                String time=this.getTimeString(getCurrTime());
                if(!this.getString().equals("PAUSE")){
                    this.setString(time);
                }
                this.setValue(getCurrTime());
                this.setCurrTime(getCurrTime()+1);
                sleep(1000);
            }
            else{
                sleep(100);
            }

            if(this.getCurrTime()>=this.getTime()){
                switchStates();
            }
        }
    }

    public void switchStates(){
        if(state==PomodoroStates.FOCUS){
            state=PomodoroStates.BREAK;
            this.setTime(breakTime);
            this.setMaximum(breakTime);
            this.setCurrTime(0);
            this.setValue(0);
            this.setString("BREAK TIME");
            this.setForeground(new Color(255, 102, 102));//light green
        }
        else{
            state=PomodoroStates.FOCUS;
            this.setTime(focusTime);
            this.setMaximum(focusTime);
            this.setCurrTime(0);
            this.setValue(0);
            this.setString("FOCUS TIME");
            this.setForeground(new Color(102, 255, 102));//light green
        }
        isRunning=false;
        started=false;
        playAlarm();
    }

    public String getState(){
        if(state==PomodoroStates.FOCUS){
            return "FOCUS";
        }
        else{
            return "BREAK";
        }
    }
    private void playAlarm(){
        alarmPlayer.playTrack();
        alarmPlaying=true;
    }
    public void stopAlarm(){
        alarmPlayer.stopAlarm();
        alarmPlaying=false;
    }
}
