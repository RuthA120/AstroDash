import processing.core.*;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

public class LeaderBoard {
    /*Attributes*/
    private HashMap<String, Integer> topPoints;
    private HashMap<String, Integer> topTimes;
    private int xLocation, yLocation;
    private HashMap<String, Integer> top10Points;
    private HashMap<String, Integer> top10Times;


    /*Constructor*/
    public LeaderBoard(int xLocation, int yLocation)
    {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        topPoints = new HashMap<String, Integer>();
        topTimes = new HashMap<String, Integer>();
        top10Points = new HashMap<String, Integer>();
        top10Times = new HashMap<String, Integer>();
    }
    public LeaderBoard()
    {
        topPoints = new HashMap<String, Integer>();
        topTimes = new HashMap<String, Integer>();
        top10Points = new HashMap<String, Integer>();
        top10Times = new HashMap<String, Integer>();
        readLeaderboardData("leaderboard_data/coins_leaderboard.txt");
        readLeaderboardData("leaderboard_data/time_leaderboard.txt");
        sortLeaderboard();
        setTop10Points();
        setTop10Times();
    }

    public void runTests(){ // Check if negative
        assert topPoints != null;
        assert topTimes != null;
    }

    public void draw(PApplet p, String type)
    {
        if(type.equals("Coins")){
            int leaderTracker = 1;
            for(Map.Entry<String, Integer> entry : top10Points.entrySet()){
                if(leaderTracker <= 5){
                    p.fill(0);
                    p.textSize(30);
                    if(entry.getKey().length() >= 8){
                        p.text( entry.getKey() + ", \n" +  entry.getValue() + " coins ", 113, 120 + (leaderTracker*120));
                    }
                    else{
                        p.text( entry.getKey() + ", " +  entry.getValue() + " coins ", 113, 120 + (leaderTracker*120));
                    }
                }
                else {
                    p.fill(0);
                    p.textSize(30);
                    if(entry.getKey().length() >= 8){
                        p.text( entry.getKey() + ", \n" +  entry.getValue() + " coins ", 503, -483 + (leaderTracker*120));
                    }
                    else{
                        p.text( entry.getKey() + ", " +  entry.getValue() + " coins ", 503, -483 + (leaderTracker*120));
                    }
                }
                leaderTracker++;
            }

        }
        else if(type.equals("Time")){
            int leaderTracker = 1;
            for(Map.Entry<String, Integer> entry : top10Times.entrySet()){
                if(leaderTracker <= 5){
                    p.fill(0);
                    p.textSize(30);
                    if(entry.getKey().length() >= 8){
                        p.text( entry.getKey() + ", \n" +  entry.getValue() + " s ", 113, 120 + (leaderTracker*120));
                    }
                    else{
                        p.text( entry.getKey() + ", " +  entry.getValue() + " s ", 113, 120 + (leaderTracker*120));
                    }
                }
                else {
                    p.fill(0);
                    p.textSize(30);
                    if(entry.getKey().length() >= 8){
                        p.text( entry.getKey() + ", \n" +  entry.getValue() + " s ", 503, -483 + (leaderTracker*120));
                    }
                    else{
                        p.text( entry.getKey() + ", " +  entry.getValue() + " s ", 503, -483 + (leaderTracker*120));
                    }
                }
                leaderTracker++;
            }
        }
    }

    /*Getters and Setters*/
    public int getxLocation()
    {
        return xLocation;
    }
    public int getyLocation()
    {
        return yLocation;
    }
    public void setxLocation(int xLocation)
    {
        this.xLocation = xLocation;
    }
    public void setyLocation(int yLocation)
    {
        this.yLocation = yLocation;
    }
    public void setTop10Points() {
        top10Points = topPoints.entrySet()
                .stream()
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public void setTop10Times() {
        top10Times = topTimes.entrySet()
                .stream()
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


    /*Game Functions*/

    public void sortLeaderboard() {
        // Sort points descending
        topPoints = topPoints.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        // Sort times ascending
        topTimes = topTimes.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public int getPointsLeaderboardPositon(String name) {
        int rank = 1;
        for (Map.Entry<String, Integer> entry : topPoints.entrySet()) {
            if (entry.getKey().equals(name)) {
                return rank;
            }
            rank++;
        }
        return -1;
    }

    public int getTimesLeaderboardPositon(String name) {
        int rank = 1;
        for (Map.Entry<String, Integer> entry : topTimes.entrySet()) {
            if (entry.getKey().equals(name)) {
                return rank;
            }
            rank++;
        }
        return -1;
    }


    public void readLeaderboardData(String filename)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String type = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");

                if(parts.length != 2){
                    System.out.println("Invalid block line: " + line);
                    return;
                }
                else if(type.equals("Coins")){
                    topPoints.put(parts[0], Integer.parseInt(parts[1]));
                }
                else if(type.equals("Time")){
                    topTimes.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading leaderboard data: " + e.getMessage());
        }
    }

    public String showTopPoints()
    {
        return "";
    }

    public String showTopTimes()
    {
        return "";
    }

    public void addPlayerToBoard(String playerName, int score, int time)
    {
        boolean newCoinsPlayer = !topPoints.containsKey(playerName);
        boolean newTimePlayer  = !topTimes.containsKey(playerName);

        boolean coinsImproved = false;
        boolean timeImproved = false;

        if (newCoinsPlayer) {
            topPoints.put(playerName, score);
            appendToFile("leaderboard_data/coins_leaderboard.txt", playerName + "," + score);
        }
        else {
            int oldScore = topPoints.get(playerName);
            if (score > oldScore) {
                topPoints.put(playerName, score);
                coinsImproved = true;
            }
        }

        if (newTimePlayer) {
            topTimes.put(playerName, time);
            appendToFile("leaderboard_data/time_leaderboard.txt", playerName + "," + time);
        }
        else {
            int oldTime = topTimes.get(playerName);
            if (time < oldTime) {
                topTimes.put(playerName, time);
                timeImproved = true;
            }
        }

        if (coinsImproved) {
            writeLeaderboardFile("leaderboard_data/coins_leaderboard.txt", "Coins", topPoints);
        }

        if (timeImproved) {
            writeLeaderboardFile("leaderboard_data/time_leaderboard.txt", "Time", topTimes);
        }

        sortLeaderboard();
        setTop10Points();
        setTop10Times();
    }


    private void writeLeaderboardFile(String filename, String type, Map<String, Integer> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(type + "\n");
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private void appendToFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(content + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    /*Testing Methods*/
    public String toString(){
        String s = "Leaderboard \nTop Times: \n";
        for (Map.Entry<String, Integer> player : topTimes.entrySet()) {
            String name = player.getKey();
            Integer time = player.getValue();
            s += name + ": " + time + "\n";
        }

        s += "Top Points: \n";
        for (Map.Entry<String, Integer> player : topPoints.entrySet()) {
            String name = player.getKey();
            Integer time = player.getValue();
            s += name + ": " + time + "\n";
        }

        s += "\n";
        return s;
    }

}
