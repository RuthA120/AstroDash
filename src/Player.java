import processing.core.*;
import processing.sound.*;

import java.util.ArrayList;

public class Player {
    /*Attributes*/
    private int life;
    private int healthBar;
    private int xLocation;
    private int yLocation;
    private int coinTracker;
    private String name;
    private FireBall fireBall;
    private boolean left, up, right, down, jump;
    private float velocityY = 0;
    private final float gravityForce = 0.5f;
    private final float jumpStrength = -8.5f;
    private boolean onGround = false;
    private PImage playerImage;
    private SoundFile coinSound;
    private SoundFile enemySound;
    private SoundFile lavaSound;

    /*Constructors*/
    public Player()
    {
        this.name = "Player";
        this.life = 3;
        this.healthBar = 3;
        this.coinTracker = 0;
        this.fireBall = null;
    }
    public Player(String name)
    {
        this.name = name;
        this.life = 3;
        this.healthBar = 3;
        this.coinTracker = 0;
        this.fireBall = new FireBall(xLocation, yLocation, "Player");
        this.xLocation = 50;
        this.yLocation = 655;
    }
    public Player(String name, int xLocation, int yLocation)
    {
        this.name = name;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.life = 3;
        this.healthBar = 3;
        this.coinTracker = 0;
        this.fireBall = null;
    }

    public void loadMedia(PApplet p){
        playerImage = p.loadImage(AstroDashUI.fileFolder + "Avatar.png");
        playerImage.resize(40, 40);
        if (fireBall != null) {
            fireBall.loadMedia(p);
        }
        coinSound = new SoundFile(p,AstroDashUI.fileFolder + "Coin-Receive.mp3");
        enemySound = new SoundFile(p,AstroDashUI.fileFolder + "Enemy-Damage.mp3");
        lavaSound = new SoundFile(p,AstroDashUI.fileFolder + "Lava-Sizzle.mp3");
    }

    public void draw(PApplet p){
        p.image(playerImage, xLocation, yLocation);
    }

    public void keyPressed(PApplet p, char c, ArrayList<Block> blocks){
        if(((c == 'A' || c == 'a') || (c == p.CODED && p.keyCode == p.LEFT)))
            left = true;
        if(((c == 'D' || c == 'd') || (c == p.CODED && p.keyCode == p.RIGHT)))
            right  = true;
        if((c == 'W' || c == 'w') || (c == p.CODED && p.keyCode == p.UP)) {
            up = true;
        }
    }


    public void keyReleased(PApplet p, char c){
        if(c == 'A' || c == 'a' || p.keyCode == p.LEFT)
            left = false;
        if(c == 'D' || c == 'd' || p.keyCode == p.RIGHT)
            right = false;
        if(c == 'W' || c == 'w' || p.keyCode == p.UP){
            up = false;
        }
        if(c == 'S' || c == 's' || p.keyCode == p.DOWN) {
            down = false;
        }
    }

    public void updatePos(ArrayList<Block> blocks, int xLimit){
        int newX = xLocation;
        float newY = yLocation;
        int playerWidth = 40;
        int playerHeight = 40;


        float moveSpeed = onGround ? 3 : 5f;
        if (left) newX -= moveSpeed;
        if (right) newX += moveSpeed;

        if(up && onGround){
            velocityY = jumpStrength;
            onGround = false;
        }

        velocityY += gravityForce;
        newY += velocityY;


        // Checking for horizontal collision
        for(Block block : blocks){
            if(checkBlockCollision(newX, yLocation, block)){
                // Moving right into block
                if(newX > xLocation){
                    newX = block.getxLocation() - playerWidth;
                }
                // Moving left into block
                else if(newX < xLocation){
                    newX = block.getxLocation() + block.getblockWidth();
                }
            }
        }

        // Checking for vertical collision
        onGround = false;
        for(Block block : blocks){
            if(checkBlockCollision(newX, (int)newY, block)){
                // Falling down onto block
                if(velocityY > 0 && (yLocation + playerHeight) <= block.getyLocation()){
                    newY = block.getyLocation() - playerHeight;
                    velocityY = 0;
                    onGround = true;
                }
                // Hitting head on block
                else if(velocityY < 0 && yLocation >= block.getyLocation() + block.getblockHeight()){
                    newY = block.getyLocation() + block.getblockHeight();
                    velocityY = 0;
                }
            }
        }

        xLocation = newX;
        yLocation = (int)newY;

        // Check for boundaries
        if(xLocation < 0){
            xLocation = 0;
        }
        if(xLocation + playerWidth > xLimit){
            xLocation = xLimit - playerWidth;
        }
        if(yLocation < 0){
            yLocation = 0;
        }
        if(yLocation + playerHeight > 790){
            yLocation = 790 - playerHeight;
        }

    }


    public void updateCoinCount(ArrayList<Coin> coins){
        Coin caughtCoin = null;
        for (Coin coin : coins) {
            if (checkCoinCollision(coin)) {
                caughtCoin = coin;
                break;
            }
        }

        if (caughtCoin != null) {
            coinTracker++;
            coins.remove(caughtCoin);
            if (coinSound != null) {
                coinSound.amp(0.25f);
                coinSound.play();
            }
        }
    }

    public void resetLevel(){
        xLocation = 50;
        yLocation = 685;
    }

    public void updatePlayerHealth(ArrayList<Enemy> enemies, ArrayList<LavaPit> lavaPits, FireBall fireBall, int currentMap){
        for (LavaPit pit : lavaPits) {
            if (checkLavaPitCollision(pit)) {
                if (lavaSound != null) {
                    lavaSound.amp(0.25f);
                    lavaSound.play();
                }
                healthBar = 0;
                if(life == 1){
                    life -= 1;
                }
                else{
                    life -= 1;
                }
                return;
            }
        }

        if (fireBall != null && !fireBall.getHasCollided() && currentMap == 5) {
            if (checkFireBallCollision(fireBall)) {
                fireBall.setHasCollided(true);
                if (lavaSound != null) {
                    lavaSound.amp(0.25f);
                    lavaSound.play();
                }
                healthBar -= 1;
                if (healthBar == 0) {
                    life -= 1;
                }
            }
        }

        Enemy toRemove = null;
        for(Enemy enemy : enemies){
            if(checkEnemyCollision(enemy)){
                toRemove = enemy;
                healthBar -= 1;

                if (enemySound != null) {
                    enemySound.amp(0.25f);
                    enemySound.play();
                }

                if (healthBar == 0)
                    life -= 1;
                break;
            }
        }
        if (toRemove != null) enemies.remove(toRemove);
    }

    private boolean checkLavaPitCollision(LavaPit pit){
        int padding = 8;
        int playerWidth = 40;
        int playerHeight = 40;

        int pitX = pit.getXLocation() + padding;
        int pitY = pit.getYLocation() + padding;
        int pitWidth = pit.getWidth() - padding * 2;
        int pitHeight = pit.getHeight() - padding * 2;

        boolean overlapX = xLocation < pitX + pitWidth && xLocation + playerWidth > pitX;
        boolean overlapY = yLocation < pitY + pitHeight && yLocation + playerHeight > pitY;

        return overlapX && overlapY;
    }

    public boolean checkBlockCollision(int newX, int newY, Block block){
        int playerWidth = 40;
        int playerHeight = 40;

        int blockX = block.getxLocation();
        int blockY = block.getyLocation();
        int blockWidth = block.getblockWidth();
        int blockHeight = block.getblockHeight();

        boolean overlapX = (newX < blockX + blockWidth) && (newX + playerWidth > blockX);
        boolean overlapY = newY < blockY + blockHeight && newY + playerHeight > blockY;

        return overlapX && overlapY;
    }

    public boolean checkCheckPointCollision(Checkpoint check){
        int playerWidth = 40;
        int playerHeight = 40;

        int checkX = check.getxLocation();
        int checkY = check.getyLocation();
        int checkWidth = check.getCheckpointWidth();
        int checkHeight = check.getCheckpointHeight();

        boolean overlapX = xLocation < checkX + checkWidth && xLocation + playerWidth > checkX;
        boolean overlapY = yLocation < checkY + checkHeight && yLocation + playerHeight > checkY;

        return overlapX && overlapY;
    }

    public boolean checkCoinCollision(Coin coin){
        int playerWidth = 40;
        int playerHeight = 40;

        int coinX = coin.getxLocation();
        int coinY = coin.getyLocation();
        int coinWidth = 25;
        int coinHeight = 25;

        boolean overlapX = xLocation < coinX + coinWidth && xLocation + playerWidth > coinX;
        boolean overlapY = yLocation < coinY + coinHeight && yLocation + playerHeight > coinY;

        return overlapX && overlapY;
    }

    public boolean checkEnemyCollision(Enemy enemy){
        int padding = 10;
        int playerWidth = 40;
        int playerHeight = 40;

        int enemyX = enemy.getXLocation();
        int enemyY = enemy.getYLocation();
        int enemyWidth = enemy.getWidth();
        int enemyHeight = enemy.getHeight();

        boolean overlapX =
                (xLocation + padding) < (enemyX + enemyWidth - padding) &&
                        (xLocation + playerWidth - padding) > (enemyX + padding);

        boolean overlapY =
                (yLocation + padding) < (enemyY + enemyHeight - padding) &&
                        (yLocation + playerHeight - padding) > (enemyY + padding);

        return overlapX && overlapY;
    }

    public boolean checkFireBallCollision(FireBall fireBall){
        int playerWidth = 40;
        int playerHeight = 40;

        int fireBallX = fireBall.getXLocation();
        int fireBallY = fireBall.getYLocation();
        int fireBallWidth = 20;
        int fireBallHeight = 20;

        boolean overlapX = xLocation < fireBallX + fireBallWidth && xLocation + playerWidth > fireBallX;
        boolean overlapY = yLocation < fireBallY + fireBallHeight && yLocation + playerHeight > fireBallY;

        return overlapX && overlapY;
    }


    public void mousePressed(PApplet p, int x, int y){
        fireBall.setXLocation(xLocation);
        fireBall.setYLocation(yLocation);
        fireBall.setHasCollided(false);
    }


    public void runTests(){ // Check if negative
        assert xLocation >= 0;
        assert yLocation >= 0;

        assert name != null;
        assert !name.equals("");
        assert name.matches(".*[a-zA-Z]+.*"); // has at least 1 letter

        assert life >= 0;
        assert life <= 3;
        assert healthBar >= 0;
        assert healthBar <= 3;
        assert coinTracker >= 0;

        assert fireBall != null;
        fireBall.runTests();
    }

    /*Getters and Setters*/
    public String getName()
    {
        return name;
    }
    public int getLife()
    {
        return life;
    }
    public int getHealthBar()
    {
        return healthBar;
    }
    public int getXLocation()
    {
        return xLocation;
    }
    public int getYLocation()
    {
        return yLocation;
    }
    public int getCoinTracker()
    {
        return coinTracker;
    }
    public FireBall getFireBall(){return fireBall;}
    public void setName(String name){this.name = name;}
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
        this.xLocation = xLocation;
    }
    public void setYLocation(int yLocation)
    {
        this.yLocation = yLocation;
    }
    public void setCoinTracker(int coinTracker)
    {
        this.coinTracker = coinTracker;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }



    /*Testing Methods*/
    public String toString()
    {
        return "Name: " + name + "\n" + "Current Lives: " + life + "\n" + "Current Health: " + healthBar + "\n"
                + "Coordinates: " + xLocation + ", " + yLocation + "\n" + "Coin Tracker: " + coinTracker + "\n"
                + fireBall + "\n";
    }

}
