package main.java;

import processing.core.*;

public class GameTimer {
    /*Attributes*/
    private int xLocation, yLocation;
    private float millisStart, millisNow;
    private int timerValue, secondsElapsed, countDown;
    private boolean started, ended;

    public float getMillisStart(){return millisStart;}
    public float getMillisNow(){return millisNow;}
    public int getTimerValue(){return timerValue;}
    public int getSecondsElapsed(){return secondsElapsed;}
    public int getCountDown(){return countDown;}
    public boolean getStarted(){return started;}
    public boolean getEnded(){return ended;}
    public void setMillisStart(float millisStart){this.millisStart = millisStart;}
    public void setMillisNow(float millisNow){this.millisNow = millisNow;}
    public void setTimerValue(int timerValue){this.timerValue = timerValue;}
    public void setSecondsElapsed(int secondsElapsed){this.secondsElapsed = secondsElapsed;}
    public void setCountDown(int countDown){this.countDown = countDown;}
    public void setStarted(boolean started){this.started = started;}
    public void setEnded(boolean ended){this.ended = ended;}


    /*Constructor*/
    public GameTimer(){
        this.timerValue = 0;
        started = false;
        ended = false;
    }

    public void draw(PApplet p)
    {
        if(started)
        {
            millisNow = p.millis();
            secondsElapsed = (int) (millisNow - millisStart) / 1000;
            countDown = timerValue + secondsElapsed; // If counting up, add instead of subtract

            if(countDown <= 0)
                ended = true;

            // display info
            p.fill(255);
            p.textSize(80);
            p.text(countDown, (p.width / 2)-25, 65);
        }
    }

    public void keyPressed(PApplet p, char c)
    {
        if(!started)
        {
            started = true;
            millisStart = p.millis();
        }

    }


    /*Testing Methods*/
    public String toString(){
        return "Current time: " + countDown + "; Is running?: " + started + "\n";
    }


}
