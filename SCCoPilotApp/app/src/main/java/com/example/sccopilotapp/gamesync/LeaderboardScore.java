package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("LeaderboardScore")
public class LeaderboardScore {
    public String name;
    public Info info;

    public LeaderboardScore() {
    }

    public LeaderboardScore(String name, Info info) {
        this.name = name;
        this.info = info;
    }

    @JsonProperty("username")
    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return this.info.getScore();
    }

    @JsonProperty("username")
    public String getName() {
        return this.name;
    }
}
