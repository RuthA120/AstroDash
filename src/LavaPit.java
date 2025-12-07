import processing.core.*;

public class LavaPit extends Impediment{

    private PImage lavaPitImage;

    /*Constructor*/
    public LavaPit(int xLocation, int yLocation, int width, int height)
    {
        super(xLocation, yLocation, width, height);
    }
    public LavaPit()
    {
        super(0, 0, 0, 0);
    }

    /*No Getters and Setters*/

    public void runTests()
    {
        super.runTests();
    }

    public void draw(PApplet p)
    {
        p.image(lavaPitImage, super.getXLocation(), super.getYLocation(), super.getWidth(), super.getHeight());
    }

    public void loadMedia(PApplet p){
        lavaPitImage = p.loadImage(AstroDashUI.fileFolder + "LavaPit.jpeg");

    }


    /*Testing Functions*/
    public String toString()
    {
        return "Lava Pit: \n" + "Coordinates: " + getXLocation() + ", " + getYLocation() + "\n";
    }


}
