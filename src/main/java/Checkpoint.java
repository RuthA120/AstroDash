package main.java;
import processing.core.*;

public class Checkpoint {

    private int xLocation;
    private int yLocation;
    private int checkpointWidth;
    private int checkpointHeight;
    private PImage checkpointImage;

    public Checkpoint(int xLocation, int yLocation, int checkpointWidth, int checkpointHeight)
    {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.checkpointWidth = checkpointWidth;
        this.checkpointHeight = checkpointHeight;
    }

    public Checkpoint()
    {
        this.xLocation = -20;
        this.yLocation = -20;
        this.checkpointWidth = 0;
        this.checkpointHeight = 0;
    }

    public int getxLocation() { return xLocation; }
    public int getyLocation() { return yLocation; }
    public int getCheckpointWidth() { return checkpointWidth; }
    public int getCheckpointHeight() { return checkpointHeight; }
    public void setxLocation(int xLocation) { this.xLocation = xLocation; }
    public void setyLocation(int yLocation) { this.yLocation = yLocation; }
    public void setCheckpointWidth(int checkpointWidth) { this.checkpointWidth = checkpointWidth; }
    public void setCheckpointHeight(int blockHeight) { this.checkpointHeight = blockHeight; }

    public void loadMedia(PApplet p){
        checkpointImage = p.loadImage("Checkpoint-Star.png");
        checkpointImage.resize(40, 40);
    }

    public void draw(PApplet p)
    {
        p.image(checkpointImage, xLocation, yLocation);
    }



    public String toString(){
        String str = "Checkpoint: \n";
        str += "x-Location: " + xLocation + "\n";
        str += "y-Location: " + yLocation + "\n";
        str += "Width: " + checkpointWidth + "\n";
        str += "Height: " + checkpointHeight + "\n";
        return str;
    }

}

