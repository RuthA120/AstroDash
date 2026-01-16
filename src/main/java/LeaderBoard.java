package main.java;

import processing.core.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import processing.data.JSONObject;
import processing.data.JSONArray;

public class LeaderBoard {
    /* Attributes */
    private HashMap<String, Integer> topPoints;
    private HashMap<String, Integer> topTimes;
    private HashMap<String, Integer> top10Points;
    private HashMap<String, Integer> top10Times;
    private int xLocation, yLocation;

    private String apiKey = "ruths-astrodash-secret-key";
    private String baseUrl = "http://astrodash.us-east-2.elasticbeanstalk.com";

    /* Constructors */
    public LeaderBoard(int xLocation, int yLocation) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        initMaps();
        fetchLeaderboards();
    }

    public LeaderBoard() {
        initMaps();
        fetchLeaderboards();
        System.out.println("API Key: " + apiKey);
    }

    private void initMaps() {
        topPoints = new HashMap<>();
        topTimes = new HashMap<>();
        top10Points = new HashMap<>();
        top10Times = new HashMap<>();
    }

    public void runTests() {
        assert topPoints != null;
        assert topTimes != null;
    }

    public void draw(PApplet p, String type) {
        int leaderTracker = 1;
        HashMap<String, Integer> displayMap = type.equals("Coins") ? top10Points : top10Times;

        for (HashMap.Entry<String, Integer> entry : displayMap.entrySet()) {
            p.fill(0);
            p.textSize(30);

            boolean longName = entry.getKey().length() >= 8;
            String text = longName
                    ? entry.getKey() + ", " + entry.getValue() + (type.equals("Coins") ? " coins " : " s ")
                    : entry.getKey() + ", " + entry.getValue() + (type.equals("Coins") ? " coins " : " s ");

            int x, y;
            if (leaderTracker <= 5) {
                x = 113;
                y = 120 + (leaderTracker * 120);
            } else {
                x = 503;
                y = -483 + (leaderTracker * 120);
            }

            p.text(text, x, y);
            leaderTracker++;
        }
    }


    private void fetchLeaderboards() {
        fetchTopPoints();
        fetchTopTimes();
        sortLeaderboard();
        setTop10Points();
        setTop10Times();
    }

    private void fetchTopPoints() {
        try {
            JSONArray arr = fetchJsonArray("/leaderboard/coins/top?limit=50");
            topPoints.clear();
            for (int i = 0; i < arr.size(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                topPoints.put(obj.getString("playerName"), obj.getInt("coins"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching top points: " + e.getMessage());
        }
    }

    private void fetchTopTimes() {
        try {
            JSONArray arr = fetchJsonArray("/leaderboard/time/top?limit=50");
            topTimes.clear();
            for (int i = 0; i < arr.size(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                topTimes.put(obj.getString("playerName"), obj.getInt("time"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching top times: " + e.getMessage());
        }
    }

    private JSONArray fetchJsonArray(String endpointPath) throws IOException {
        URL url = new URL(baseUrl + endpointPath);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-API-KEY", apiKey);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP GET failed with code: " + responseCode);
        }

        // read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) content.append(line);
        in.close();
        con.disconnect();

        // use a Reader instead of string for JSONArray
        return new JSONArray(new StringReader(content.toString()));
    }


    public void addPlayerToBoard(String playerName, int coins, int time) {
        try {
            Integer existingCoins = topPoints.get(playerName);
            if (existingCoins == null || coins > existingCoins) {
                postScore("coins", playerName, coins);
            }

            Integer existingTime = topTimes.get(playerName);
            if (existingTime == null || time < existingTime) {
                postScore("time", playerName, time);
            }

            fetchLeaderboards();
        } catch (Exception e) {
            System.out.println("Error submitting score: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private void postScore(String type, String playerName, int value) throws IOException {
        URL url = new URL(baseUrl + "/leaderboard/" + type);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("X-API-KEY", apiKey);
        con.setDoOutput(true);

        String jsonInput = "";
        if (type.equals("coins")) {
            jsonInput = "{\"playerName\":\"" + playerName + "\",\"coins\":" + value + "}";
        } else if (type.equals("time")) {
            jsonInput = "{\"playerName\":\"" + playerName + "\",\"time\":" + value + "}";
        }

        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInput.getBytes("utf-8"));
        }

        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_NO_CONTENT) {
            System.out.println("POST failed: " + responseCode);
        }
        con.disconnect();
    }




    public void sortLeaderboard() {
        topPoints = topPoints.entrySet().stream() // sort leaderboard by points (desc)
                .sorted(HashMap.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

        topTimes = topTimes.entrySet().stream() // sort leaderboard by times (asc)
                .sorted(HashMap.Entry.comparingByValue())
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    public void setTop10Points() { // retrieve first 10
        top10Points = topPoints.entrySet().stream().limit(10)
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    public void setTop10Times() { // retrieve first 10
        top10Times = topTimes.entrySet().stream().limit(10)
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    public int getPointsLeaderboardPositon(String name) {
        int rank = 1;
        for (String player : topPoints.keySet()) {
            if (player.equals(name)) return rank;
            rank++;
        }
        return -1;
    }

    public int getTimesLeaderboardPositon(String name) {
        int rank = 1;
        for (String player : topTimes.keySet()) {
            if (player.equals(name)) return rank;
            rank++;
        }
        return -1;
    }

    public String toString() {
        String s = "Leaderboard \nTop Times: \n";
        for (HashMap.Entry<String, Integer> player : topTimes.entrySet()) {
            s += player.getKey() + ": " + player.getValue() + "\n";
        }

        s += "Top Points: \n";
        for (HashMap.Entry<String, Integer> player : topPoints.entrySet()) {
            s += player.getKey() + ": " + player.getValue() + "\n";
        }
        return s + "\n";
    }
}
