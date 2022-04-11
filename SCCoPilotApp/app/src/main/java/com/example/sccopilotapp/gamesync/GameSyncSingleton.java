package com.example.sccopilotapp.gamesync;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;

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

    public static GameSyncStatus getConnectionStatus() {
        return status;
    }

    enum GameSyncStatus {
        UNKNOWN,
        CONNECTING,
        CONNECTED,
        DISCONNECTED
    }

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

    /**
     * Connects to the signaling server via a remote relay
     *
     * @param joinCode the passcode/secret to join a specific room
     */
    public static void connectSignaling(String joinCode) {

        Request request = new Request.Builder().url(remoteAddress).build();
        // TODO: Figure out what exceptions this needs to be throwing
        ws = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                Log.d(TAG, "onClosed: Code:" + code + " Reason: " + reason);
                status = GameSyncStatus.DISCONNECTED;
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                Log.d(TAG, "onClosing: Code :" + code + " Reason: " + reason);
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                Log.d(TAG, "onFailure: Failure " + t.toString());
                if (response != null) {
                    Log.d(TAG, "onFailure: Failure " + response.toString());
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
                Log.d(TAG, "onMessage: Bytes: " + bytes.toString());
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                status = GameSyncStatus.CONNECTED;
                Log.d(TAG, "onOpen: Response: " + response.toString());
                JoinRoom room = new JoinRoom(joinCode);
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

    private static void messageHandler(DataObject o) {
        Log.d(TAG, "messageHandler: Handling object" + o);
        if (o instanceof ButtonPressed) {
            Log.d(TAG, "messageHandler: found " + (o.toString()));
            eventHelper.firePropertyChange("ButtonEvent", null, o);
        } else if (o instanceof PowerUpStatus) {
            Log.d(TAG, "messageHandler: found " + (o.toString()));
            eventHelper.firePropertyChange("PowerUpStatus", null, o);
        } else if (o instanceof JoinRoom) {
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
     * Adds a generic event listener for sync events
     *
     * @param eventName     to be listened to
     * @param eventListener that fires when the eventName is called
     */
    public static void addListener(String eventName, PropertyChangeListener eventListener) {
        eventHelper.addPropertyChangeListener(eventName, eventListener);
    }


    @Deprecated
    // TODO: This shouldn't be used externally but is done for testing
    public static void pushWS(String string) {
        ws.send(string);
    }

    /**
     * Sets the remote address
     *
     * @param remoteAddress
     */
    public static void setRemoteAddress(String remoteAddress) {
        // TODO: Reinitialize connection if remote changes while a connection is active
        GameSyncSingleton.remoteAddress = remoteAddress;
    }

    /**
     * pushes an event
     *
     * @param e
     */
    public static void sendEvent(DataObject e) {
        String msg = null;
        try {
            msg = objectMapper.writeValueAsString(e);
            Log.d(TAG, "sendEvent: sending message " + msg);
            ws.send(msg);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
    }
}
