import java.util.ArrayList;
import processing.core.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import processing.sound.*;

public class Game {
    /*Attributes*/
    // PImage Array --> game background
    private int mapCount;
    private int currentMap;
    private GameTimer timer;
    private LeaderBoard leaderBoard;
    private ArrayList<Map> maps;
    private Player player;
    private String playerName;
    private boolean gameOver = false;
    private boolean gameStarted = false;
    private boolean showGameScreen = false;
    private boolean gameWon = false;
    private boolean showCoinsLeaderBoard = false;
    private boolean showTimesLeaderBoard = false;
    private boolean showInstructions1 = false;
    private boolean showInstructions2 = false;
    private boolean showInstructions3 = false;
    private boolean showInstructions4 = false;
    private boolean sameUser = false;
    private int finalTime = -1;
    private boolean skipLevel = false;
    private boolean gamePlayStarted = false;
    private PImage backgroundImage;
    private ArrayList<PImage> mapImages;
    private ArrayList<PImage> screenImages;
    private PImage heartImage;
    private ArrayList<PImage> healthImages;
    private ArrayList<PImage> endGameImages;
    private ArrayList<PImage> leaderBoardImages;
    private SoundFile soundGameWon;
    private SoundFile soundGameLost;
    private boolean lostSoundPlayed = false;
    private boolean winSoundPlayed = false;
    private SoundFile gameMusic;
    private SoundFile checkPointSound;

    /*Constructor*/
    public Game(){
        mapCount = 0;
        currentMap = 1;

        timer = new GameTimer();
        leaderBoard = new LeaderBoard();

        // Reading real map data
        maps = new ArrayList<>(5);
        read_all_map_data("map_data/maps_data.txt");

        for(int i=1; i <= maps.size(); i++){
            String fileName = "map_data/map" + i + "_xydata.txt";
            read_specific_map_data(fileName, maps.get(i-1));
        }

        playerName = "";
        player = new Player(playerName);
        mapImages = new ArrayList<>(5);
        screenImages = new ArrayList<>(6);
        healthImages = new ArrayList<>(4);
        endGameImages = new ArrayList<>(2);
        leaderBoardImages = new ArrayList<>(2);
    }

    public Game(int mapCount){
        this.mapCount = mapCount;
        currentMap = 1;
        timer = new GameTimer();
        leaderBoard = new LeaderBoard();
        maps = new ArrayList<>();
        for(int i = 1; i <= mapCount; i++){
            maps.add(new Map(i, 3, 3, 10, 10));
        }
        playerName = "";
        player = new Player(playerName);
    }

    public void loadMedia(PApplet p){
        mapImages.clear();
        screenImages.clear();
        healthImages.clear();
        endGameImages.clear();
        leaderBoardImages.clear();

        for(int i=0; i<maps.size(); i++){
            PImage image = p.loadImage(AstroDashUI.fileFolder + "gamebackground" + (i+1) + ".png");
            image.resize(p.width, p.height);
            mapImages.add(image);
        }

        for(int i=0; i<6; i++){
            PImage image = p.loadImage(AstroDashUI.fileFolder + "StartPage" + (i+1) + ".png");
            image.resize(p.width, p.height);
            screenImages.add(image);
        }

        for(int i=0; i<maps.size(); i++){
            maps.get(i).loadMedia(p);
        }

        heartImage = p.loadImage(AstroDashUI.fileFolder + "Heart.png");
        heartImage.resize(60, 60);

        for(int i=0; i<4; i++){
            PImage image = p.loadImage(AstroDashUI.fileFolder + "Health" + i + ".png");
            image.resize(300, 36);
            healthImages.add(image);
        }

        for(int i=0; i<2; i++){
            PImage image = p.loadImage(AstroDashUI.fileFolder + "EndGame" + (i+1) + ".png");
            image.resize(p.width, p.height);
            endGameImages.add(image);
        }

        for(int i=0; i<2; i++){
            PImage image = p.loadImage(AstroDashUI.fileFolder + "Leaderboard" + (i+1) + ".png");
            image.resize(p.width, p.height);
            leaderBoardImages.add(image);
        }

        player.loadMedia(p);
        soundGameWon = new SoundFile(p,AstroDashUI.fileFolder + "Game-Win.mp3");
        soundGameLost = new SoundFile(p,AstroDashUI.fileFolder + "Game-Over.mp3");
        gameMusic = new SoundFile(p,AstroDashUI.fileFolder + "Space-Music.mp3");
        checkPointSound = new SoundFile(p, AstroDashUI.fileFolder + "Checkpoint-Sound.mp3");
    }

    public void draw(PApplet p, PFont myFont)
    {
        if(!gameStarted && !sameUser){
            backgroundImage = screenImages.get(0);
            p.background(backgroundImage);
            gameOver = false;
            gameWon = false;
        }
        else if(showGameScreen){
            backgroundImage = screenImages.get(1);
            p.background(backgroundImage);
            p.textFont(myFont);
            p.fill(0);
            p.textSize(60);
            p.text(playerName,195,400);
        }
        else if(showCoinsLeaderBoard){
            backgroundImage = leaderBoardImages.get(0);
            p.background(backgroundImage);
            leaderBoard.draw(p, "Coins");
        }
        else if(showTimesLeaderBoard){
            backgroundImage = leaderBoardImages.get(1);
            p.background(backgroundImage);
            leaderBoard.draw(p, "Time");
        }

        else if(showInstructions1){
            backgroundImage = screenImages.get(2);
            p.background(backgroundImage);
        }

        else if(showInstructions2){
            backgroundImage = screenImages.get(3);
            p.background(backgroundImage);
        }

        else if(showInstructions3){
            backgroundImage = screenImages.get(4);
            p.background(backgroundImage);
        }

        else if(showInstructions4){
            backgroundImage = screenImages.get(5);
            p.background(backgroundImage);
        }

        else{
            gamePlayStarted = true;
            if (!gameMusic.isPlaying()) {
                gameMusic.loop();
            }
            if(gameOver && !gameWon && !showCoinsLeaderBoard && !showTimesLeaderBoard){
                gameMusic.stop();
                backgroundImage = endGameImages.get(0);
                p.background(backgroundImage);
                if (!lostSoundPlayed) {
                    soundGameLost.play();
                    lostSoundPlayed = true;
                }
            }
            else if(gameWon && gameOver){
                gameMusic.stop();
                backgroundImage = endGameImages.get(1);
                p.background(backgroundImage);
                if (gameWon && gameOver && finalTime == -1) {
                    finalTime = timer.getCountDown();
                    leaderBoard.addPlayerToBoard(player.getName(), player.getCoinTracker(), finalTime);
                }
                p.fill(255);
                String str5 = " " + leaderBoard.getPointsLeaderboardPositon(player.getName());
                p.textSize(50);
                p.text(str5, 535, 360);

                p.fill(255);
                String str6 = " " + leaderBoard.getTimesLeaderboardPositon(player.getName());
                p.textSize(50);
                p.text(str6, 535, 465);

                if (!winSoundPlayed) {
                    soundGameWon.play();
                    winSoundPlayed = true;
                }
            }
            else{
                if(currentMap == 1){
                    backgroundImage = mapImages.get(0);
                }
                else if (currentMap == 2){
                    backgroundImage = mapImages.get(1);
                }
                else if (currentMap == 3){
                    backgroundImage = mapImages.get(2);
                }
                else if (currentMap == 4){
                    backgroundImage = mapImages.get(3);
                }
                else if (currentMap == 5){
                    backgroundImage = mapImages.get(4);
                }

                p.background(backgroundImage);
                soundGameLost.stop();
                soundGameWon.stop();
                if(player.getLife() == 3){
                    p.image(heartImage, 5,0);
                    p.image(heartImage, 70,0);
                    p.image(heartImage, 135,0);
                }
                else if(player.getLife() == 2){
                    p.image(heartImage, 5,0);
                    p.image(heartImage, 70,0);
                }
                else if(player.getLife() == 1){
                    p.image(heartImage, 5,0);
                }

                if(player.getHealthBar() == 0){
                    p.image(healthImages.get(0), -35, 60);
                }
                else if(player.getHealthBar() == 1){
                    p.image(healthImages.get(1), -35, 60);
                }
                else if(player.getHealthBar() == 2){
                    p.image(healthImages.get(2), -35, 60);
                }
                else if(player.getHealthBar() == 3){
                    p.image(healthImages.get(3), -35, 60);
                }


                maps.get(currentMap - 1).draw(p);
                player.draw(p);
                timer.draw(p);


                if(currentMap == 5){
                    player.updatePos(maps.get(currentMap - 1).getBlocks(), 410);
                }
                else{
                    player.updatePos(maps.get(currentMap - 1).getBlocks(), 820);
                }

                player.updateCoinCount(maps.get(currentMap - 1).getCoins());
                player.updatePlayerHealth(maps.get(currentMap - 1).getEnemies(), maps.get(currentMap - 1).getLavaPits(), maps.get(currentMap - 1).getFinalBoss().getFireBall(), currentMap);

                if(player.getLife() == 0){
                    gameOver = true;
                    timer = new GameTimer();
                    player.setLife(4);
                    player.setCoinTracker(0);
                    player.setHealthBar(3);
                }
                else if(player.getHealthBar() == 0){
                    player.resetLevel();
                    player.setHealthBar(3);
                }

                if(currentMap == 5){
                    if(player.getFireBall() != null){
                        player.getFireBall().draw(p);
                        maps.get(currentMap - 1).getFinalBoss().checkFireBallCollision(player.getFireBall());
                        if(maps.get(currentMap - 1).getFinalBoss().isAlive() == false){
                            gameOver = true;
                            gameWon = true;
                        }
                    }
                }
                updateCurrentMap();
            }
        }

    }

    public void keyPressed(PApplet p, char c){
        if(showGameScreen){
            if(c != p.CODED) {
                if(c == 8 && playerName.length() > 0)
                    playerName = playerName.substring(0,playerName.length()-1);
                else if(playerName.length() < 13)
                    playerName += c;
            }
        }


        if(gamePlayStarted){
            player.keyPressed(p, c, maps.get(currentMap - 1).getBlocks());
            timer.keyPressed(p, c);
        }
    }

    public void keyReleased(PApplet p, char c){
        player.keyReleased(p, c);
    }

    public void mousePressed(PApplet p, int x, int y)
    {
        if(!gameStarted && !sameUser && x > 290 && x < 530 && y > 400 && y < 480){
            gameStarted = true;
            showGameScreen = true;
            sameUser = true;
            gameOver = false;
            return;
        }

        else if(!gameStarted && sameUser){
            gameOver = false;
            gameStarted = true;
            return;
        }

        else if(showGameScreen){
            if(x <= 200 && x >= 30 && y <= 105 && y >= 40){
                showCoinsLeaderBoard = true;
                showGameScreen = false;
            }
            else if(x >= 600 && x <= 780 && y <= 105 && y >= 40){
                showTimesLeaderBoard = true;
                showGameScreen = false;
            }

            else if(playerName.length() >= 3 && playerName.length() < 13 && x > 330 && x < 490 && y > 475 && y < 525){
                player.setName(playerName);
                showGameScreen = false;
                showInstructions1 = true;
            }

        }

        else if(showCoinsLeaderBoard | showTimesLeaderBoard){
            if(x <= 200 && x >= 30 && y <= 105 && y >= 40){
                showGameScreen = true;
                showCoinsLeaderBoard = false;
                showTimesLeaderBoard = false;
            }
        }

        else if(showInstructions1){
            if(x <= 470 && x >= 365 && y <= 565 && y >= 515){
                showInstructions1 = false;
                showInstructions2 = true;
            }
        }

        else if(showInstructions2){
            if(x <= 470 && x >= 365 && y <= 565 && y >= 515){
                showInstructions2 = false;
                showInstructions3 = true;
            }
        }

        else if(showInstructions3){
            if(x <= 470 && x >= 365 && y <= 565 && y >= 515){
                showInstructions3 = false;
                showInstructions4 = true;
            }
        }

        else if(showInstructions4){
            if(x <= 470 && x >= 365 && y <= 565 && y >= 515){
                showInstructions4 = false;
            }
        }

        if(gameStarted && !gameOver){
            if(x > 645 && x < 720 && y > 5 && y < 40 && !skipLevel && currentMap != 5) {
                currentMap += 1;
                player.setLife(player.getLife() - 1);
                player.setXLocation(50);
                player.setYLocation(685);
                timer.setTimerValue(timer.getTimerValue() + 30);
                skipLevel = true;
            }
            else if(x > 740 && x < 805 && y > 5 && y < 40){
                gameOver = true;
                gameWon = false;
            }
        }

        if(gameOver && gameStarted){
            if(x <= 215 && x >= 45 && y <= 125 && y >= 60){
                sameUser = false;
                gameStarted = false;
                gameWon = false;
                playerName = "";
                resetGame(p);
            }
            else if(x >= 615 && x <= 795 && y <= 125 && y >= 60){
                gameStarted = false;
                gameWon = false;
                gameOver = false;
                gameStarted = true;
                resetGame(p);
            }

            return;
        }

        if(currentMap == 5) {
            player.mousePressed(p, x, y);
        }
    }

    public void resetGame(PApplet p){
        if(gameWon){
            leaderBoard.addPlayerToBoard(player.getName(), player.getCoinTracker(), timer.getTimerValue());
        }
        mapCount = 0;
        currentMap = 1;
        gameOver = false;
        gameStarted = false;
        finalTime = -1;
        gamePlayStarted = false;
        skipLevel = false;
        lostSoundPlayed = false;
        winSoundPlayed = false;
        gameMusic.stop();
        timer = new GameTimer();

        maps = new ArrayList<>(5);
        read_all_map_data("map_data/maps_data.txt");

        for(int i = 1; i <= maps.size(); i++){
            String fileName = "map_data/map" + i + "_xydata.txt";
            read_specific_map_data(fileName, maps.get(i - 1));
        }

        player = new Player(playerName);
        loadMedia(p);
    }


    public void runTests(){
        assert mapCount >= 0;
        assert currentMap >= 0;

        assert timer != null;
        assert leaderBoard != null;
        assert player != null;
        /*timer.runTests();*/
        leaderBoard.runTests();
        player.runTests();

        for(Map map : maps){
            assert map != null;
        }

    }

    /*Getters and Setters*/
    public int getMapCount(){
        return mapCount;
    }
    public void setMapCount(int mapCount){
        this.mapCount = mapCount;
    }

    public int getCurrentMap(){
        return currentMap;
    }
    public void setCurrentMap(int currentMap){
        this.currentMap = currentMap;
    }


    /*Game Functions*/
    public void updateCurrentMap(){
        if (player.checkCheckPointCollision(maps.get(currentMap - 1).getCheckpoint())) {
            currentMap++;
            player.setXLocation(50);
            player.setYLocation(685);
            if (checkPointSound != null) {
                checkPointSound.play();
            }
        }
    }


    public void read_all_map_data(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");

                if (parts.length != 5) {
                    System.out.println("Invalid line: " + line);
                    continue;
                }

                int mapNumber = Integer.parseInt(parts[0]);
                int numEnemies = Integer.parseInt(parts[1]);
                int numLavaPits = Integer.parseInt(parts[2]);
                int numCoins = Integer.parseInt(parts[3]);
                int numBlocks = Integer.parseInt(parts[4]);

                maps.add(new Map(mapNumber, numEnemies, numLavaPits, numCoins, numBlocks));
                mapCount++;
            }
        }
        catch (IOException e)
        {
            System.out.println("Error reading map data: " + e.getMessage());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid number format in map data: " + e.getMessage());
        }
    }


    public void read_specific_map_data(String filename, Map map) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String section = "";

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                switch (line) {
                    case "Blocks":
                    case "Enemies":
                    case "Coins":
                    case "Lava Pits":
                    case "Checkpoint":
                        section = line;
                        continue;
                }

                String[] parts = line.split(",");

                try {
                    switch (section) {
                        case "Blocks":
                            if (parts.length != 4) {
                                System.out.println("Invalid block line: " + line);
                                continue;
                            }
                            map.addBlocks(
                                    Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]),
                                    Integer.parseInt(parts[2]),
                                    Integer.parseInt(parts[3])
                            );
                            break;

                        case "Enemies":
                            if (parts.length != 2) {
                                System.out.println("Invalid enemy line: " + line);
                                continue;
                            }
                            map.addEnemies(
                                    Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1])
                            );
                            break;

                        case "Coins":
                            if (parts.length != 2) {
                                System.out.println("Invalid coin line: " + line);
                                continue;
                            }
                            map.addCoins(
                                    Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1])
                            );
                            break;

                        case "Lava Pits":
                            if (parts.length != 4) {
                                System.out.println("Invalid lava pit line: " + line);
                                continue;
                            }
                            map.addLavaPits(
                                    Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]),
                                    Integer.parseInt(parts[2]),
                                    Integer.parseInt(parts[3])
                            );
                            break;

                        case "Checkpoint":
                            if (parts.length != 4) {
                                System.out.println("Invalid checkpoint line: " + line);
                                continue;
                            }
                            map.addCheckpoint(
                                    Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]),
                                    Integer.parseInt(parts[2]),
                                    Integer.parseInt(parts[3])
                            );
                            break;

                        default:
                            System.out.println("Unknown section or data: " + line);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number in line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading map data: " + e.getMessage());
        }
    }




    /*Testing Methods*/
    public String toString(){
        String s = "Game: \n";
        s += "Total Maps: " + mapCount + "\n";
        s += "Current Map: " + currentMap + "\n";
        s += timer + "\n";
        s += leaderBoard + "\n";
        s += player + "\n";
        s += "\n";
        s += "All Maps: " + "\n";
        for (Map m : maps){
            s += m + "\n";
        }
        return s;
    }



}
