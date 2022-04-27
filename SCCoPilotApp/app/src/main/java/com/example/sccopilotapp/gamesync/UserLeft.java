package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLeft userLeft = (UserLeft) o;
        return ip.equals(userLeft.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }

}
