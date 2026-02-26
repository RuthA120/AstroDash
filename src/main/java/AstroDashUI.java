package main.java;
import processing.core.*;

public class AstroDashUI extends PApplet{
    Game theGame = new Game();
    PFont myFont;
    int BASE_W = 820;
    int BASE_H = 790;
    float scale;
    float offsetX, offsetY;

    static final String fileFolder = "ruthMedia" + System.getProperty("file.separator");

    public void settings() {
        size(BASE_W, min(displayHeight, BASE_H));
    }


    public void setup()
    {
        textMode(SHAPE);
        loadMedia();
        myFont = createFont("ByteBounce.ttf", 42);
    }

    public void loadMedia(){
        theGame.loadMedia(this);
    }

    public void draw() {
        scale = min(
                (float) width / BASE_W,
                (float) height / BASE_H
        );
        scale = min(scale, 1.0f);

        offsetX = (width - BASE_W * scale) / 2;
        offsetY = (height - BASE_H * scale) / 2;

        background(0);

        pushMatrix();
        translate(offsetX, offsetY);
        scale(scale);
        theGame.draw(this, myFont, BASE_W, BASE_H);
        popMatrix();
    }

    public void keyPressed(){
        theGame.keyPressed(this, key);
    }

    public void keyReleased(){
        theGame.keyReleased(this, key);
    }

    public void mousePressed() {
        int scaledMouseX = (int)((mouseX - offsetX) / scale);
        int scaledMouseY = (int)((mouseY - offsetY) / scale);

        if (scaledMouseX < 0 || scaledMouseX > BASE_W || scaledMouseY < 0 || scaledMouseY > BASE_H) return;

        theGame.mousePressed(this, scaledMouseX, scaledMouseY);
    }



    public static void main(String[] args){
        PApplet.main("main.java.AstroDashUI");
    }


}
