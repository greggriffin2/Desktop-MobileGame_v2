package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonTypeName("JoinRoom")
/**
 * JoinRoom object is a request to join a room that already exists.
 */
public class JoinRoom extends DataObject {
    String roomName;

    @JsonProperty("JoinRoom")
    public String getRoomName() {
        return roomName;
    }

    @JsonProperty("JoinRoom")
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    // Empty constructor for serialization
    public JoinRoom() {
    }

    public JoinRoom(String roomName) {
        this.roomName = roomName;
    }

}
