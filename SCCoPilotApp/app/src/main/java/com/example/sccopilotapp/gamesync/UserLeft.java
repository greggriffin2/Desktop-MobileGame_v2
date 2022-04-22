package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLeft {
    String ip;

    @JsonProperty("ip_left")
    public String getIp() {
        return ip;
    }

    @JsonProperty("ip_left")
    public void setIp(String ip) {
        this.ip = ip;
    }
}
