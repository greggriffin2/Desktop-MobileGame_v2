package com.example.sccopilotapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.example.sccopilotapp.gamesync.ButtonPressedEvent;
import com.example.sccopilotapp.gamesync.DataObject;
import com.example.sccopilotapp.gamesync.EnemyKilled;
import com.example.sccopilotapp.gamesync.JoinRoomEvent;
import com.example.sccopilotapp.gamesync.PowerUpStatusEvent;
import com.example.sccopilotapp.gamesync.UserJoined;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;


public class SerializationTests {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void JoinRoomEventSerialization() {
        DataObject toSerialize = new JoinRoomEvent("word");
        String shouldSerializeTo = "{\"JoinRoom\":\"word\"}";
        String serializedVersion = "";
        try {
            serializedVersion = objectMapper.writeValueAsString(toSerialize);
        } catch (JsonProcessingException e) {
            fail("Message failed to serialize!");
        }
        assertEquals(shouldSerializeTo, serializedVersion);
    }

    @Test
    public void PowerUpStatusEventSerialization() {
        DataObject toSerialize = new PowerUpStatusEvent(PowerUpStatusEvent.PowerUpStatusEnum.None, -1);
        String shouldSerializeTo = "{\"status\":\"None\",\"duration\":-1}";
        String serializedVersion = "";
        try {
            serializedVersion = objectMapper.writeValueAsString(toSerialize);
        } catch (JsonProcessingException e) {
            fail("Message failed to serialize!");
        }
        assertEquals(shouldSerializeTo, serializedVersion);
    }

    @Test
    public void ButtonPressedEventSerialization() {
        DataObject toSerialize = new ButtonPressedEvent(5);
        String shouldSerializeTo = "{\"timesPressed\":5}";
        String serializedVersion = "";
        try {
            serializedVersion = objectMapper.writeValueAsString(toSerialize);
        } catch (JsonProcessingException e) {
            fail("Message failed to serialize!");
        }
        assertEquals(shouldSerializeTo, serializedVersion);
    }

    @Test
    public void EnemyKilledSerialization() {
        DataObject toSerialize = new EnemyKilled("Alien");
        String shouldSerializeTo = "{\"enemyType\":\"Alien\"}";
        String serializedVersion = "";
        try {
            serializedVersion = objectMapper.writeValueAsString(toSerialize);
        } catch (JsonProcessingException e) {
            fail("Message failed to serialize!");
        }
        assertEquals(shouldSerializeTo, serializedVersion);
    }

    @Test
    public void UserJoinedSerialization() {
        DataObject toSerialize = new UserJoined("127.0.0.1");
        String shouldSerializeTo = "{\"ip_joined\":\"127.0.0.1\"}";
        String serializedVersion = "";
        try {
            serializedVersion = objectMapper.writeValueAsString(toSerialize);
        } catch (JsonProcessingException e) {
            fail("Message failed to serialize!");
        }
        assertEquals(shouldSerializeTo, serializedVersion);
    }

    @Test
    public void JoinRoomEventDeSerialization() {
        String toDeserialize = "{\"JoinRoom\":\"Test\"}";
        DataObject shouldDeserializeTo = new JoinRoomEvent("Test");
        DataObject deserializedVersion = null;
        try {
            deserializedVersion = objectMapper.readValue(toDeserialize, DataObject.class);
        } catch (JsonProcessingException e) {
            fail("Failed to (de)Serialize message!");
        }
        assertEquals(shouldDeserializeTo, deserializedVersion);
    }

    @Test
    public void PowerUpStatusEventDeSerialization() {
        String toDeserialize = "{\"status\":\"None\",\"duration\":-1}";
        DataObject shouldDeserializeTo = new PowerUpStatusEvent(PowerUpStatusEvent.PowerUpStatusEnum.None, -1);
        DataObject deserializedVersion = null;
        try {
            deserializedVersion = objectMapper.readValue(toDeserialize, DataObject.class);
        } catch (JsonProcessingException e) {
            fail("Failed to (de)Serialize message!");
        }
        assertEquals(shouldDeserializeTo, deserializedVersion);
    }

    @Test
    public void ButtonPressedEventDeSerialization() {
        String toDeserialize = "{\"timesPressed\":1}";
        DataObject shouldDeserializeTo = new ButtonPressedEvent(1);
        DataObject deserializedVersion = null;
        try {
            deserializedVersion = objectMapper.readValue(toDeserialize, DataObject.class);
        } catch (JsonProcessingException e) {
            fail("Failed to (de)Serialize message!");
        }
        assertEquals(shouldDeserializeTo, deserializedVersion);
    }

    @Test
    public void EnemyKilledDeSerialization() {
        String toDeserialize = "{\"enemyType\":\"Aliens\"}";
        DataObject shouldDeserializeTo = new EnemyKilled("Aliens");
        DataObject deserializedVersion = null;
        try {
            deserializedVersion = objectMapper.readValue(toDeserialize, DataObject.class);
        } catch (JsonProcessingException e) {
            fail("Failed to (de)Serialize message!");
        }
        assertEquals(shouldDeserializeTo, deserializedVersion);
    }

    @Test
    public void UserJoinedDeSerialization() {
        String toDeserialize = "{\"ip_joined\":\"127.0.0.1\"}";
        DataObject shouldDeserializeTo = new UserJoined("127.0.0.1");
        DataObject deserializedVersion = null;
        try {
            deserializedVersion = objectMapper.readValue(toDeserialize, DataObject.class);
        } catch (JsonProcessingException e) {
            fail("Failed to (de)Serialize message!");
        }
        assertEquals(shouldDeserializeTo, deserializedVersion);
    }
}