package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("info")

public class LeaderboardScore {
    public String name;
    public int score;

    public LeaderboardScore() {
    }

    public LeaderboardScore(String name, int score) {
        this.name = name;
        this.score = score;
    }
    @JsonProperty("username")
    public void setName(String name){
        this.name = name;
    }
    @JsonProperty("highscore")
    public void setScore(int score){
        this.score = score;
    }
    @JsonProperty("highscore")
    public int getScore(){
        return this.score;
    }
    @JsonProperty("username")
    public String getName(){
        return this.name;
    }
}
