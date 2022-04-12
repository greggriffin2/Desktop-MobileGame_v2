package com.example.sccopilotapp.gamesync;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.webrtc.DataChannel;
import org.webrtc.PeerConnectionFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class GameSyncSingleton {
    static private ObjectMapper objectMapper;
    static private String remoteAddress;
    static private PropertyChangeSupport eventHelper;
    static private PeerConnectionFactory peerFactory;
    static private DataChannel channel;
    static private WebSocket ws;
    static private OkHttpClient client;
    static private GameSyncStatus status;

    public GameSyncSingleton(Context context) {
        status = GameSyncStatus.DISCONNECTED;
        objectMapper = new ObjectMapper();
        eventHelper = new PropertyChangeSupport(this);
        // Apparently we need to initialize the PeerConnectionFactory with context about the android app its running in
        PeerConnectionFactory.InitializationOptions initializationOptions = PeerConnectionFactory
                .InitializationOptions
                .builder(context)
                .createInitializationOptions();

        client = new OkHttpClient();

        // TODO: This shouldn't be a single server, nor should it really be nextcloud's server.
//        PeerConnection.IceServer ice = PeerConnection.IceServer.builder("stun.nextcloud.com:443").createIceServer();
//        List<PeerConnection.IceServer> iceServerList = new ArrayList<>();
//        iceServerList.add(ice);
//        PeerConnectionFactory.initialize(initializationOptions);
//        peerFactory = PeerConnectionFactory.builder().createPeerConnectionFactory();
//        PeerConnection p = peerFactory.createPeerConnection(iceServerList, );


    }

    public static GameSyncStatus getConnectionStatus() {
        return status;
    }

    /**
     * Connects to the signaling server via a remote relay
     *
     * @param joinCode the passcode/secret to join a specific room
     */
    public static void connectSignaling(String joinCode) {
        status = GameSyncStatus.CONNECTING;
        Request request = new Request.Builder().url(remoteAddress).build();
        // TODO: Figure out what exceptions this needs to be throwing
        ws = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                Log.d(TAG, "onClosed: Code:" + code + " Reason: " + reason);
                status = GameSyncStatus.DISCONNECTED;
                eventHelper.firePropertyChange("ConnectionClosed", null, reason);
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                Log.d(TAG, "onClosing: Code :" + code + " Reason: " + reason);
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                Log.d(TAG, "onFailure: Failure " + t);
                if (response != null) {
                    Log.d(TAG, "onFailure: Failure " + response);
                }
                status = GameSyncStatus.UNKNOWN;
                eventHelper.firePropertyChange("Error", t, response);
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                Log.d(TAG, "onMessage: Text: " + text);
                DataObject d = null;
                try {
                    d = objectMapper.readValue(text, DataObject.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                messageHandler(d);
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
                Log.d(TAG, "onMessage: Bytes: " + bytes);
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                status = GameSyncStatus.CONNECTED;
                Log.d(TAG, "onOpen: Response: " + response);
                JoinRoomEvent room = new JoinRoomEvent(joinCode);
                try {
                    String msg = objectMapper.writeValueAsString(room);
                    Log.d(TAG, "onOpen: SendingMessage:" + msg);
                    webSocket.send(msg);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                super.onOpen(webSocket, response);
            }
        });
    }

    /**
     * Distributes deserialized messages to their appropriate events
     *
     * @param o data events are notified of
     */
    private static void messageHandler(DataObject o) {
        Log.d(TAG, "messageHandler: Handling object" + o);
        if (o instanceof ButtonPressedEvent) {
            Log.d(TAG, "messageHandler: found " + o);
            eventHelper.firePropertyChange("ButtonEvent", null, o);
        } else if (o instanceof PowerUpStatusEvent) {
            Log.d(TAG, "messageHandler: found " + o);
            eventHelper.firePropertyChange("PowerUpStatus", null, o);
        } else if (o instanceof JoinRoomEvent) {
            eventHelper.firePropertyChange("Connected", null, o);
        }
    }

    /**
     * Returns the current remote address
     *
     * @return remote address
     */
    public static String getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * Sets the remote address
     *
     * @param remoteAddress of the Signaling Server
     */
    public static void setRemoteAddress(String remoteAddress) {
        // TODO: Reinitialize connection if remote changes while a connection is active
        GameSyncSingleton.remoteAddress = remoteAddress;
    }

    /**
     * Adds a generic event listener for sync events
     *
     * @param eventName     to be listened to
     * @param eventListener that fires when the eventName is called
     */
    public static void addListener(String eventName, PropertyChangeListener eventListener) {
        eventHelper.addPropertyChangeListener(eventName, eventListener);
    }

    /**
     * Pushes an event to the game
     *
     * @param e Event to be sent to the game
     */
    public static void sendEvent(DataObject e) {
        try {
            String msg = objectMapper.writeValueAsString(e);
            Log.d(TAG, "sendEvent: sending message " + msg);
            ws.send(msg);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
    }

    enum GameSyncStatus {
        UNKNOWN,
        CONNECTING,
        CONNECTED,
        DISCONNECTED
    }
}
