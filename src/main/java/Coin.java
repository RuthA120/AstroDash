package main.java;
import processing.core.*;

public class Coin {

    /*Attributes*/
    private int xLocation;
    private int yLocation;
    private boolean visible = true;
    private PImage coinImage;

    /*Constructors*/
    public Coin(int xLocation, int yLocation)
    {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }
    public Coin()
    {
        this.xLocation = 0;
        this.yLocation = 0;
    }

    public void runTests(){ // Check if negative
        assert xLocation >= 0;
        assert yLocation >= 0;
    }

    public void draw(PApplet p)
    {
        if (visible && coinImage != null){
            p.image(coinImage, xLocation, yLocation);
        }
    }

    /*Getters and Setters*/
    public int getxLocation()
    {
        return xLocation;
    }

    public int getyLocation()
    {
        return yLocation;
    }

    public void setxLocation(int xLocation)
    {
        this.xLocation = xLocation;
    }

    public void setyLocation(int yLocation)
    {
        this.yLocation = yLocation;
    }

    /*Game Functions*/
    public void loadMedia(PApplet p){
        coinImage = p.loadImage("PixelCoin.png");
        coinImage.resize(30, 30);
    }

    /*Testing Methods*/
    public String toString(){
        return "Coin: " + xLocation + ", " + yLocation + "\n";
    }

}
