package main.java;
import processing.core.*;
import processing.sound.SoundFile;

public class FinalBoss extends Impediment{

    /*Attributes*/
    private int life;
    private int healthBar;
    private FireBall fireBall;
    private int originalX;
    private int direction;
    private int speed;
    private int range;
    private boolean isAlive;
    private PImage finalBossImage;
    private SoundFile lavaSound;

    /*Constructor*/
    public FinalBoss(int xLocation, int yLocation, int width, int height)
    {
        super(xLocation, yLocation, width, height);
        this.life = 1;
        this.healthBar = 10;
        fireBall = new FireBall();
    }
    public FinalBoss()
    {
        super(650, 690, 60, 140);
        this.life = 1;
        this.healthBar = 10;
        direction = 1;
        speed = 1;
        range = 30;
        originalX = getXLocation();
        fireBall = new FireBall(getXLocation(), getYLocation()+20, "FinalBoss");
        isAlive = true;
    }

    public void draw(PApplet p)
    {
        if(fireBall.getXLocation() <= 0 || fireBall.getHasCollided() == true){
            fireBall = null;
        }

        p.image(finalBossImage, super.getXLocation(), super.getYLocation()-50, super.getWidth(), super.getHeight());
        p.fill(6, 204, 204);
        p.textSize(30);
        p.text("Health: " + healthBar, super.getXLocation()+20, super.getYLocation()-25);
        move();
        if(fireBall == null)
            launchFireBall();
    }

    public void loadMedia(PApplet p){
        fireBall.loadMedia(p);
        finalBossImage = p.loadImage("FinalBoss.png");
        lavaSound = new SoundFile(p,"Lava-Sizzle.mp3");
    }

    public void runTests(){
        super.runTests();
        assert life >= 0;
        assert life <= 1;
        assert healthBar >= 0;
        assert healthBar <= 10;
        assert fireBall != null;
        fireBall.runTests();
    }

    /*Getters and Setters*/
    public int getHealthBar()
    {
        return healthBar;
    }
    public int getLife()
    {
        return life;
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
    public boolean isAlive()
    {
        return isAlive;
    }

    public FireBall getFireBall()
    {
        return fireBall;
    }
    public void setFireBall(FireBall fireBall){
        this.fireBall = fireBall;
    }


    /*Game Functions*/
    public void launchFireBall()
    {
        int randomDirection = (int)(Math.random() * 2);
        if(randomDirection == 0){
            fireBall = new FireBall(getXLocation(), getYLocation() + 20, "FinalBoss");
            fireBall.setDirection(randomDirection);
        }
        else if(randomDirection == 1){
            fireBall = new FireBall(getXLocation(), getYLocation() - 10, "FinalBoss");
            fireBall.setDirection(randomDirection);
        }
    }

    public void move(){
        super.setXLocation(super.getXLocation() + (speed * direction));
        if (super.getXLocation() > originalX + range) {
            direction = -1;
        }
        if (super.getXLocation() < originalX - range) {
            direction = 1;
        }
    }

    public void checkFireBallCollision(FireBall fireBall){
        if (fireBall.getHasCollided()) return;
        int finalBossWidth = 60;
        int finalBossHeight = 140;

        int fireBallX = fireBall.getXLocation();
        int fireBallY = fireBall.getYLocation();
        int fireBallWidth = 20;
        int fireBallHeight = 20;

        boolean overlapX = getXLocation() < fireBallX + fireBallWidth && getXLocation() + finalBossWidth > fireBallX;
        boolean overlapY = getYLocation() < fireBallY + fireBallHeight && getYLocation() + finalBossHeight > fireBallY;


        if(overlapX && overlapY){
            healthBar--;
            fireBall.setHasCollided(true);
            if (lavaSound != null) {
                lavaSound.amp(0.25f);
                lavaSound.play();
            }
        }
        if(healthBar == 0){
            isAlive = false;
        }
    }

    /*Testing Methods*/
    public String toString()
    {
        return "Final Boss: \n" + "Current Lives: " + life + "\n" + "Current Health: " + healthBar + "\n"
                + "Coordinates: " + getXLocation() + ", " + getYLocation() + "\n" + fireBall + "\n";
    }

}
