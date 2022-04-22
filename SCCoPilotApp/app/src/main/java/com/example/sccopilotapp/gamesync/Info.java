package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Info {
    public String score;

    public Info() {
    }

    public Info(String score) {
        this.score = score;
    }

    @JsonProperty("highscore")
    public String getScore() {
        return score;
    }

    @JsonProperty("highscore")
    public void setScore(String newScore) {
        this.score = newScore;
    }
}
