package main.java;

import processing.core.*;

import java.util.ArrayList;

public class Map {
    /*Attributes*/
    private int mapNumber;
    private int numEnemies;
    private int numLavaPits;
    private int numCoins;
    private int numBlocks;
    private ArrayList<Enemy> enemies;
    private ArrayList<LavaPit> lavaPits;
    private FinalBoss finalBoss;
    private ArrayList<Coin> coins;
    private ArrayList<Block> blocks;
    private Checkpoint checkpoint;

    /*Constructor*/
    public Map()
    {
        mapNumber = 0;

        enemies = new ArrayList<>();
        numEnemies = 0;
        for(int i = 0; i < numEnemies; i++){
            enemies.add(new Enemy());
        }

        lavaPits = new ArrayList<>();
        numLavaPits = 0;
        for(int i = 0; i < numLavaPits; i++){
            lavaPits.add(new LavaPit());
        }

        finalBoss = new FinalBoss();

        coins = new ArrayList<>();
        numCoins = 0;
        for(int i = 0; i < numCoins; i++){
            coins.add(new Coin());
        }

        blocks = new ArrayList<>();
        for(int i = 0; i < numBlocks; i++){
            blocks.add(new Block());
        }

        checkpoint = new Checkpoint();

    }

    public Map(int mapNumber, int numEnemies, int numLavaPits, int numCoins, int numBlocks)
    {
        this.mapNumber = mapNumber;
        enemies = new ArrayList<>();
        this.numEnemies = numEnemies;
        for(int i = 0; i < numEnemies; i++){
            enemies.add(new Enemy());
        }

        lavaPits = new ArrayList<>();
        this.numLavaPits = numLavaPits;
        for(int i = 0; i < numLavaPits; i++){
            lavaPits.add(new LavaPit());
        }

        finalBoss = new FinalBoss();

        coins = new ArrayList<>();
        this.numCoins = numCoins;
        for(int i = 0; i < numCoins; i++){
            coins.add(new Coin());
        }

        blocks = new ArrayList<>();
        for(int i = 0; i < numBlocks; i++){
            blocks.add(new Block());
        }

        checkpoint = new Checkpoint();
    }

    public ArrayList<Block> getBlocks()
    {
        return blocks;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<LavaPit> getLavaPits() {
        return lavaPits;
    }

    public ArrayList<Coin> getCoins()
    {
        return coins;
    }

    public FinalBoss getFinalBoss() {
        return finalBoss;
    }

    public void loadMedia(PApplet p)
    {
        for(int i = 0; i < numCoins; i++){
            coins.get(i).loadMedia(p);
        }
        for(int i=0; i< numEnemies; i++){
            enemies.get(i).loadMedia(p);
        }
        for (Block block : blocks) {
            block.loadMedia(p);
        }
        for(LavaPit lavaPit : lavaPits) {
            lavaPit.loadMedia(p);
        }

        if (finalBoss != null && finalBoss.getFireBall() != null){
            finalBoss.loadMedia(p);
        }

        checkpoint.loadMedia(p);
    }


    public void draw(PApplet p){
        for(Block block: blocks){
            block.draw(p);
        }
        for(LavaPit lavaPit: lavaPits){
            lavaPit.draw(p);
        }
        for(Enemy enemy : enemies){
            enemy.move();
            enemy.draw(p);
        }
        for(Coin coin : coins){
            coin.draw(p);
        }
        checkpoint.draw(p);

        if(mapNumber == 5){
            finalBoss.draw(p);
            if(finalBoss.getFireBall() != null){
                finalBoss.getFireBall().draw(p);
            }
        }
    }


    public void runTests(){ // Check if negative
        assert mapNumber >= 0;

        assert numEnemies >= 0;
        assert numEnemies <= 8;

        assert numLavaPits >= 0;
        assert numLavaPits <= 5;

        assert numCoins >= 0;
        assert numCoins <= 20;

        assert finalBoss != null;
        finalBoss.runTests();

        for(Enemy e: enemies){ // add
            assert e != null;
        }
        for (Enemy e: enemies){
            e.runTests();
        }

        for(LavaPit p: lavaPits){
            assert p != null;
        }
        for (LavaPit p: lavaPits){
            p.runTests();
        }

        for(Coin c: coins){
            assert c != null;
        }
        for (Coin c: coins){
            c.runTests();
        }
    }

    /*Getters and Setters*/
    public int getMapNumber()
    {
        return mapNumber;
    }
    public void setMapNumber(int mapNumber)
    {
        this.mapNumber = mapNumber;
    }
    public Checkpoint getCheckpoint()
    {
        return checkpoint;
    }

    /*main.java.Game Functions*/
    public void addCheckpoint(int xLocation, int yLocation, int width, int height){
        checkpoint.setxLocation(xLocation);
        checkpoint.setyLocation(yLocation);
        checkpoint.setCheckpointHeight(height);
        checkpoint.setCheckpointWidth(width);
    }


    public void addCoins(int xLocation, int yLocation)
    {
        for (Coin coin : coins)
        {
            if (coin.getxLocation() == 0 && coin.getyLocation() == 0)
            {
                coin.setxLocation(xLocation);
                coin.setyLocation(yLocation);
                break;
            }
        }
    }

    public void addEnemies(int xLocation, int yLocation)
    {
        for (Enemy enemy : enemies)
        {
            if (enemy.getXLocation() == 0 && enemy.getYLocation() == 0)
            {
                enemy.setXLocation(xLocation);
                enemy.setYLocation(yLocation);
                break;
            }
        }
    }

    public void addLavaPits(int xLocation, int yLocation, int width, int height)
    {
        for (LavaPit lavaPit : lavaPits)
        {
            if (lavaPit.getXLocation() == 0 && lavaPit.getYLocation() == 0 &&
                    lavaPit.getWidth() == 0 && lavaPit.getHeight() == 0)
            {
                lavaPit.setXLocation(xLocation);
                lavaPit.setYLocation(yLocation);
                lavaPit.setWidth(width);
                lavaPit.setHeight(height);
                break;
            }
        }
    }

    public void addBlocks(int xLocation, int yLocation, int width, int height)
    {
        for (Block block : blocks)
        {
            if (block.getxLocation() == 0 && block.getyLocation() == 0 &&
                    block.getblockWidth() == 0 && block.getblockHeight() == 0)
            {
                block.setxLocation(xLocation);
                block.setyLocation(yLocation);
                block.setblockWidth(width);
                block.setblockHeight(height);
                break;
            }
        }
    }

    /*Testing Methods*/
    public String toString()
    {
        String str = "main.java.Map " + mapNumber + "\n";

        str += "Number of enemies: " + enemies.size() + "\n";
        for(Enemy e : enemies){
            str += e.toString();
        }
        str += "\n";

        str += "Number of lava pits: " + lavaPits.size() + "\n";
        for(LavaPit p : lavaPits){
            str += p.toString();
        }
        str += "\n";

        str += "Number of coins: " + coins.size() + "\n";
        for(Coin coin : coins){
            str += coin.toString();
        }

        str += "Number of blocks: " + blocks.size() + "\n";
        for(Block block : blocks){
            str += block.toString();
        }

        str += "\n" + finalBoss + "\n";

        return str;
    }

}
