package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonTypeName("JoinRoom")
public class JoinRoom extends DataObject {
    String roomName;

    public JoinRoom() {

    }
    public JoinRoom(String roomName) {
        this.roomName = roomName;
    }

    @JsonProperty("JoinRoom")
    public String getRoomName() {
        return roomName;
    }

    @JsonProperty("JoinRoom")
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
