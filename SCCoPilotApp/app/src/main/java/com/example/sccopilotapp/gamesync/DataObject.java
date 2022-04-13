package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base DataObject for any (de)serialized objects sent or received from the game
 * Doesn't have any native data, but all Game Data objects derive from this base class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JoinRoomEvent.class, name = "JoinRoom"),
        @JsonSubTypes.Type(value = PowerUpStatusEvent.class, name = "PowerUpStatus"),
        @JsonSubTypes.Type(value = ButtonPressedEvent.class, name = "PowerButtonPressed")
})
public abstract class DataObject {
}
