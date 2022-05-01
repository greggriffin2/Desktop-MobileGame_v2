package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

@JsonTypeName("PowerUpStatus")
public
class PowerUpStatusEvent extends DataObject {
    PowerUpStatusEnum status;
    int duration;

    // Empty constructor for serialization
    public PowerUpStatusEvent() {

    }

    public PowerUpStatusEvent(PowerUpStatusEnum status, int duration) {
        this.status = status;
        this.duration = duration;
    }

    public PowerUpStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PowerUpStatusEnum status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public enum PowerUpStatusEnum {
        None,
        SpeedUp,
        LaserBoost
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PowerUpStatusEvent)) return false;
        PowerUpStatusEvent that = (PowerUpStatusEvent) o;
        return duration == that.duration && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, duration);
    }
}