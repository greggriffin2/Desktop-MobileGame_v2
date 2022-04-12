package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, include = JsonTypeInfo.As.PROPERTY)
public class ScoreboardScore {
    String username;
    String dateTime;
    InfoField field;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public InfoField getField() {
        return field;
    }

    public void setField(InfoField field) {
        this.field = field;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class InfoField {
        int HighScore;

        public int getHighScore() {
            return HighScore;
        }

        public void setHighScore(int highScore) {
            HighScore = highScore;
        }
    }
}
