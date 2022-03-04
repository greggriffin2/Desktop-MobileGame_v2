package com.example.sccopilotapp.gamesync;

import android.content.Context;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.webrtc.DataChannel;
import org.webrtc.NativePeerConnectionFactory;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;

public class GameSyncSingleton {
    static private String remoteAddress;
    static private int remotePort;
    static private PropertyChangeSupport eventHelper;
    static private PeerConnectionFactory peerFactory;

    GameSyncSingleton(Context context) {

        // Apparently we need to initialize the PeerConnectionFactory with context about the android app its running in
        PeerConnectionFactory.InitializationOptions initializationOptions = PeerConnectionFactory
                .InitializationOptions
                .builder(context)
                .createInitializationOptions();
        PeerConnectionFactory.initialize(initializationOptions);
    }

    public static String getRemoteAddress() {
        return remoteAddress;
    }

    public static int getRemotePort() {
        return remotePort;
    }

    private static void addListener(String eventName, PropertyChangeListener eventListener) {
        eventHelper.addPropertyChangeListener(eventName, eventListener);
    }
}
