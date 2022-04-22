package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserJoined extends DataObject {
    String ip;

    @JsonProperty("ip_joined")
    public String getIp() {
        return ip;
    }

    @JsonProperty("ip_joined")
    public void setIp(String ip) {
        this.ip = ip;
    }
}
