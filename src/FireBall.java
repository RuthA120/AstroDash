import processing.core.*;
import processing.sound.SoundFile;

public class FireBall {

    /*Attributes*/
    private int xLocation;
    private int yLocation;
    private String fireBallName;
    private int direction;
    private boolean hasCollided = false;
    private PImage fireBallImage;
    private SoundFile lavaSound;

    /*Constructors*/
    public FireBall(int xLocation, int yLocation, String fireBallName)
    {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.fireBallName = fireBallName;
        this.direction = 0;
    }
    public FireBall()
    {
        this.xLocation = 0;
        this.yLocation = 0;
    }

    public void loadMedia(PApplet p){
        fireBallImage = p.loadImage(AstroDashUI.fileFolder + "FireBall.png");
        fireBallImage.resize(20, 20);
    }

    public void draw(PApplet p)
    {
        if (hasCollided) {
            return;
        }

        if (fireBallImage == null) {
            fireBallImage = p.loadImage(AstroDashUI.fileFolder + "FireBall.png");
            fireBallImage.resize(20,20);
        }

        if(fireBallName.equals("Player"))
        {
            xLocation += 10;
            p.image(fireBallImage, xLocation+15, yLocation);
        }
        else if(fireBallName.equals("FinalBoss")){
            if(direction == 0){
                yLocation -= 1;
            }
            else if(direction == 1){
                yLocation += 1;
            }
            xLocation -= 10;
            p.image(fireBallImage, xLocation+15, yLocation);
        }
    }

    public void runTests(){ // Check if negative
        assert xLocation >= 0;
        assert yLocation >= 0;
    }

    /*Getters and Setters*/
    public int getXLocation()
    {
        return xLocation;
    }

    public int getYLocation()
    {
        return yLocation;
    }

    public void setXLocation(int xLocation)
    {
        this.xLocation = xLocation;
    }

    public void setYLocation(int yLocation)
    {
        this.yLocation = yLocation;
    }

    public void setDirection(int direction) {this.direction = direction;}

    public int getDirection() {return direction;}

    public boolean getHasCollided() {return hasCollided;}

    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }



    /*Testing Methods*/
    public String toString(){
        return "FireBall: " + xLocation + ", " + yLocation + "\n";
    }

}
