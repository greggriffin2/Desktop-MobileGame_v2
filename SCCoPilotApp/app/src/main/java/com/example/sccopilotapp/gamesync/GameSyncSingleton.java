package com.example.sccopilotapp.gamesync;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameSyncSingleton {
    static private String remoteAddress;
    static private int remotePort;
    static private PropertyChangeSupport eventHelper;

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
