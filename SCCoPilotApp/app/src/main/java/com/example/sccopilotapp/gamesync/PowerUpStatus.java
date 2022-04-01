package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("PowerUpStatus")
class PowerUpStatus extends DataObject {
    PowerUpStatusEnum status;
    int duration;

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

    // Empty constructor for serialization
    public PowerUpStatus() {

    }

    PowerUpStatus(PowerUpStatusEnum status, int duration) {
        this.status = status;
        this.duration = duration;
    }
}