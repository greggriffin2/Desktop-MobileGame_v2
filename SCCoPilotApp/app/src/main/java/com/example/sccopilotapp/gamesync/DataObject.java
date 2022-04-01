package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JoinRoom.class, name = "JoinRoom"),
        @JsonSubTypes.Type(value = PowerUpStatus.class, name = "PowerUpStatus"),
        @JsonSubTypes.Type(value = ButtonPressed.class, name = "PowerButtonPressed")
})
public abstract class DataObject {
}
