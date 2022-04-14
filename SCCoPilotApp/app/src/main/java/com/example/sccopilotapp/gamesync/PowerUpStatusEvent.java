package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("PowerUpStatus")
public
class PowerUpStatusEvent extends DataObject {
    PowerUpStatusEnum status;
    int duration;

    // Empty constructor for serialization
    public PowerUpStatusEvent() {

    }

    PowerUpStatusEvent(PowerUpStatusEnum status, int duration) {
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

    enum PowerUpStatusEnum {
        None,
        SpeedUp,
        LaserBoost
    }
}