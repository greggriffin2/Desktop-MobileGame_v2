package com.example.sccopilotapp.gamesync;

import android.content.Context;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


public class SynchronizationFacade {

    private static GameSyncSingleton sync;

    public SynchronizationFacade(String remoteAddress, Context context) {
        if (sync == null) {
            sync = new GameSyncSingleton(context);
            GameSyncSingleton.setRemoteAddress(remoteAddress);
        }
        if (!GameSyncSingleton.getRemoteAddress().equals(remoteAddress)) {
            GameSyncSingleton.setRemoteAddress(remoteAddress);
        }
    }

    /**
     * Updates the Signaling server address
     *
     * @param remoteAddress address for the Signaling Server
     */
    public static void updateSignalingAddress(String remoteAddress) {
        GameSyncSingleton.setRemoteAddress(remoteAddress);
    }

    /**
     * Attempts to connect to a game using the provided PairingCode
     *
     * @param pairingCode that pairs to a particular (currently running) game
     */
    public static void connect(String pairingCode) {
        GameSyncSingleton.connectSignaling(pairingCode);
    }


    /**
     * Adds an event to be fired when something changes with a PowerUp
     *
     * @param listener to be fired
     */
    public static void addPowerUpEvent(PropertyChangeListener listener) {
        GameSyncSingleton.addListener("PowerUpStatus", listener);
    }

    /**
     * Adds an event to be fired when an enemy is killed
     *
     * @param listener to be fired
     */
    public static void addEnemyKilledEvent(PropertyChangeListener listener) {
        GameSyncSingleton.addListener("EnemyKilled", listener);
    }

    /**
     * Adds an event to be fired when the game is paused
     *
     * @param listener to be fired
     */
    public static void addPausedEvent(PropertyChangeListener listener) {
        GameSyncSingleton.addListener("GamePausedEvent", listener);
    }

    public static void addDisconnectedEvent(PropertyChangeListener listener) {
        GameSyncSingleton.addListener("GameDisconnected", listener);
    }

    public static void addArbitraryListener(String eventName, PropertyChangeListener l) {
        GameSyncSingleton.addListener(eventName, l);
    }

    public static void addUserConnectedEvent(PropertyChangeListener listener) {
        GameSyncSingleton.addListener("UserConnected", listener);
    }

    public static void addUserDisconnectedEvent(PropertyChangeListener listener) {
        GameSyncSingleton.addListener("UserDisconnected", listener);
    }

    /**
     * Called when a button is pressed in the UI
     *
     * @param ButtonPress number of times the button is pressed
     */
    public static void fireButtonPressed(int ButtonPress) {
        ButtonPressedEvent p = new ButtonPressedEvent(ButtonPress);
        GameSyncSingleton.sendEvent(p);
    }

    /**
     * Fires an activatePowerUp event with the given ID
     *
     * @param powerupID
     */
    public static void fireActivatePowerup(PowerUpStatusEvent.PowerUpStatusEnum powerupID) {
        PowerUpStatusEvent event = new PowerUpStatusEvent(powerupID, 0);
        GameSyncSingleton.sendEvent(event);
    }

    /**
     * Returns the current network connection status
     *
     * @return Connection Status
     */
    public static GameSyncSingleton.GameSyncStatus getConnectionStatus() {
        return GameSyncSingleton.getConnectionStatus();
    }

    /**
     * Returns a range of record scores
     *
     * @param startRange nth place to start getting leaderboard scores from. Starts from 1 and goes down
     * @param stopRange  last score to retrieve
     * @return a list of scores in order from highest score to lowest
     */
    public static Vector<LeaderboardScore> getScores(int startRange, int stopRange) throws IOException {
        Vector<LeaderboardScore> testLeaderboard = new Vector<>(5);
        for (int i = 0; i < 5; i++) {
            testLeaderboard.add(new LeaderboardScore("Joe", new Info("5" + i)));
        }
        return testLeaderboard;
    }
}
