import processing.core.*;

import java.util.Random;

public class Enemy extends Impediment{

    /*Attributes*/
    private int life;
    private int healthBar;
    private int direction;
    private int speed;
    private int originalX;
    private int range;
    private PImage enemyImage;
    private boolean visible = true;

    /*Constructor*/
    public Enemy(int xLocation, int yLocation)
    {
        super(xLocation, yLocation, 30, 30);
        life = 1;
        healthBar = 1;
    }
    public Enemy(){
        super(0, 0, 50, 50);
        life = 1;
        healthBar = 1;
        direction = 1;
        speed = 1;
        range = 30;
    }

    public void draw(PApplet p)
    {
        if (visible && enemyImage != null){
            p.image(enemyImage, super.getXLocation(), super.getYLocation());
        }
//        p.fill(130, 10, 212);
//        p.stroke(130, 10, 212);
//        p.rect(super.getXLocation(), super.getYLocation(), super.getWidth(), super.getHeight());
    }

    public void runTests(){
        super.runTests();
        assert life >= 0;
        assert life <= 1;
        assert healthBar >= 0;
        assert healthBar <= 1;
    }

    /*Getters and Setters*/
    public int getLife()
    {
        return life;
    }
    public int getHealthBar()
    {
        return healthBar;
    }
    public void setLife(int life)
    {
        this.life = life;
    }
    public void setHealthBar(int healthBar)
    {
        this.healthBar = healthBar;
    }
    public void setXLocation(int xLocation)
    {
        super.setXLocation(xLocation);
        originalX = xLocation;
    }

    public void loadMedia(PApplet p){
        enemyImage = p.loadImage(AstroDashUI.fileFolder + "Enemy.png");
        enemyImage.resize(50, 50);
    }

    /*Game Functions*/

    public void move(){
        super.setXLocation(super.getXLocation() + (speed * direction));
        if (super.getXLocation() > originalX + range) {
            direction = -1;
        }
        if (super.getXLocation() < originalX - range) {
            direction = 1;
        }
    }

    /*Testing Functions*/
    public String toString()
    {
        return "Enemy: \n" + "Current Lives: " + life + "\n" + "Current Health: " + healthBar + "\n"
                + "Coordinates: " + getXLocation() + ", " + getYLocation() + "\n";
    }


}
