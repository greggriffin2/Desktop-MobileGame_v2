package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("JoinRoom")
public class JoinRoomEvent extends DataObject {
    /**
     * JoinRoom object is a request to join a room that already exists.
     */
    String roomName;

    // Empty constructor for serialization
    public JoinRoomEvent() {
    }

    public JoinRoomEvent(String roomName) {
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
