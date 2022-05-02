package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Objects;

public class UserJoined extends DataObject {

    public UserJoined(String ip) {
        this.ip = ip;
    }

    public UserJoined() {

    }

    String ip;

    @JsonProperty("ip_joined")
    public String getIp() {
        return ip;
    }

    @JsonProperty("ip_joined")
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJoined that = (UserJoined) o;
        return ip.equals(that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }

}
