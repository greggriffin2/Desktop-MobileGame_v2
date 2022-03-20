package com.example.sccopilotapp.gamesync;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

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
    static private String remoteAddress;
    static private String remotePort;
    static private PropertyChangeSupport eventHelper;
    static private PeerConnectionFactory peerFactory;
    static private DataChannel channel;
    static private WebSocket ws;
    static private OkHttpClient client;

    public GameSyncSingleton(Context context) {

        // Apparently we need to initialize the PeerConnectionFactory with context about the android app its running in
        PeerConnectionFactory.InitializationOptions initializationOptions = PeerConnectionFactory
                .InitializationOptions
                .builder(context)
                .createInitializationOptions();

        client = new OkHttpClient();

        // TODO: This shouldn't be a single server, nor should it really be nextcloud's server.
        PeerConnection.IceServer ice = PeerConnection.IceServer.builder("stun.nextcloud.com:443").createIceServer();
        List<PeerConnection.IceServer> iceServerList = new ArrayList<>();
        iceServerList.add(ice);
        PeerConnectionFactory.initialize(initializationOptions);
        peerFactory = PeerConnectionFactory.builder().createPeerConnectionFactory();
        PeerConnection p = peerFactory.createPeerConnection(iceServerList, new PeerConnection.Observer() {
            @Override
            public void onSignalingChange(PeerConnection.SignalingState signalingState) {
                Log.d(TAG, "onSignalingChange:" + signalingState);
            }

            @Override
            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                Log.d(TAG, "onIceConnectionChange:" + iceConnectionState);
            }

            @Override
            public void onIceConnectionReceivingChange(boolean b) {
                Log.d(TAG, "onIceConnectionReceivingChange: " + b);
            }

            @Override
            public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
                Log.d(TAG, "onIceGatheringChange: " + iceGatheringState);
            }

            @Override
            public void onIceCandidate(IceCandidate iceCandidate) {
                Log.d(TAG, "onIceCandidate: " + iceCandidate);
            }

            @Override
            public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {
                Log.d(TAG, "onIceCandidatesRemoved: " + iceCandidates.toString());

            }

            @Override
            public void onAddStream(MediaStream mediaStream) {
                Log.d(TAG, "onAddStream: " + mediaStream);
            }

            @Override
            public void onRemoveStream(MediaStream mediaStream) {
                Log.d(TAG, "onRemoveStream: " + mediaStream);
            }

            @Override
            public void onDataChannel(DataChannel dataChannel) {
                Log.d(TAG, "onDataChannel: " + dataChannel);
            }

            @Override
            public void onRenegotiationNeeded() {
                Log.d(TAG, "onRenegotiationNeeded: Arg");
            }
        });
    }

    public static void connectSignaling(String joinCode) {
        Request request = new Request.Builder().url("wss://" + remoteAddress + ":" + remotePort).build();

        ws = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                Log.d(TAG, "onClosed: Code:" + code + " Reason: " + reason);
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
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                Log.d(TAG, "onMessage: Text: " + text);
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
                Log.d(TAG, "onMessage: Bytes: " + bytes.toString());
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                Log.d(TAG, "onOpen: Response: " + response.toString());
                webSocket.send("{\"JoinRoom\":" + "\"" + joinCode + "\"}");
                super.onOpen(webSocket, response);
            }
        });
    }

    public static String getRemoteAddress() {
        return remoteAddress;
    }

    public static String getRemotePort() {
        return remotePort;
    }

    private static void addListener(String eventName, PropertyChangeListener eventListener) {
        eventHelper.addPropertyChangeListener(eventName, eventListener);
    }


    @Deprecated
    // TODO: This shouldn't be used externally but is done for testing
    public static void pushWS(String string) {
        ws.send(string);
    }

    public static void setRemotePort(String remotePort) {
        // TODO: Reinitialize connection if remote changes while a connection is active
        GameSyncSingleton.remotePort = remotePort;
    }

    public static void setRemoteAddress(String remoteAddress) {
        // TODO: Reinitialize connection if remote changes while a connection is active
        GameSyncSingleton.remoteAddress = remoteAddress;
    }
}
