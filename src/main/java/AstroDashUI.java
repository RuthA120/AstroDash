package main.java;
import processing.core.*;

public class AstroDashUI extends PApplet{
    Game theGame = new Game();
    PFont myFont;

    static final String fileFolder = "ruthMedia" + System.getProperty("file.separator");

    public void settings()
    {
        size(820, 790);
    }

    public void setup()
    {
        loadMedia();
        myFont = createFont("ByteBounce.ttf", 42);
    }

    public void loadMedia(){
        theGame.loadMedia(this);
    }

    public void draw()
    {
        background(255);
        theGame.draw(this, myFont);
    }

    public void keyPressed(){
        theGame.keyPressed(this, key);
    }

    public void keyReleased(){
        theGame.keyReleased(this, key);
    }

    public void mousePressed()
    {
        theGame.mousePressed(this, mouseX, mouseY);
    }


    public static void main(String[] args){
        PApplet.main("main.java.AstroDashUI");
    }


}
