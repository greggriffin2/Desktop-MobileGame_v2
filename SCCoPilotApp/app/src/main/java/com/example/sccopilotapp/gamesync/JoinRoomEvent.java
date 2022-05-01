package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinRoomEvent that = (JoinRoomEvent) o;
        return roomName.equals(that.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomName);
    }

}
