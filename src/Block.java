import processing.core.*;

public class Block {
    private int xLocation;
    private int yLocation;
    private int blockWidth;
    private int blockHeight;
    private PImage blockImage;

    public Block(int xLocation, int yLocation, int blockWidth, int blockHeight)
    {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
    }

    public Block()
    {
        this.xLocation = 0;
        this.yLocation = 0;
        this.blockWidth = 0;
        this.blockHeight = 0;
    }

    public void loadMedia(PApplet p){
        blockImage = p.loadImage(AstroDashUI.fileFolder + "Block.png");
    }

    public int getxLocation() { return xLocation; }
    public int getyLocation() { return yLocation; }
    public int getblockWidth() { return blockWidth; }
    public int getblockHeight() { return blockHeight; }
    public void setxLocation(int xLocation) { this.xLocation = xLocation; }
    public void setyLocation(int yLocation) { this.yLocation = yLocation; }
    public void setblockWidth(int blockWidth) { this.blockWidth = blockWidth; }
    public void setblockHeight(int blockHeight) { this.blockHeight = blockHeight; }


    public void draw(PApplet p)
    {
          p.image(blockImage, xLocation, yLocation, blockWidth, blockHeight);
    }



    public String toString(){
        String str = "Block: \n";
        str += "x-Location: " + xLocation + "\n";
        str += "y-Location: " + yLocation + "\n";
        str += "block-Width: " + blockWidth + "\n";
        str += "block-Height: " + blockHeight + "\n";
        return str;
    }

}
