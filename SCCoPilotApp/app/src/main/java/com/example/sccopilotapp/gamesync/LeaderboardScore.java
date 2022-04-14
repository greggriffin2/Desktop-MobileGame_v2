package com.example.sccopilotapp.gamesync;

/**
 * Single Scoreboard record
 */
public class LeaderboardScore {
    public final String name;
    public final int score;

    public LeaderboardScore(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
