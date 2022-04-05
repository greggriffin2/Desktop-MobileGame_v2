package com.example.sccopilotapp.gamesync;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

enum ConnectionStatus {
    CONNECTED,
    DISCONNECTED,
    UNKNOWN
}

public class SynchronizationFacade {

    private static GameSyncSingleton sync;

    public SynchronizationFacade(String remoteAddress, Context context) {
        if (sync != null) {
        } else {
            sync = new GameSyncSingleton(context);
            GameSyncSingleton.setRemoteAddress(remoteAddress);
        }
        if (!GameSyncSingleton.getRemoteAddress().equals(remoteAddress)) {
            GameSyncSingleton.setRemoteAddress(remoteAddress);
        }
    }

    /**
     * Updates the Signaling and Scoreboard server address
     *
     * @param remoteAddress
     */
    public static void updateAddress(String remoteAddress) {

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
    public void addPowerUpEvent(PropertyChangeListener listener) {

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
    public void addPausedEvent(PropertyChangeListener listener) {

    }

    /**
     * Adds an event to be fired when the game is resumed
     *
     * @param listener
     */
    public void addUnpausedEvent(PropertyChangeListener listener) {

    }

    /**
     * Called when a button is pressed in the UI
     *
     * @param ButtonPress
     */
    public static void fireButtonPressed(int ButtonPress) {
        ButtonPressed p = new ButtonPressed(1);
        GameSyncSingleton.sendEvent(p);
    }

    /**
     * Returns the current network connection status
     *
     * @return Connection Status
     */
    public static ConnectionStatus getConnectionStatus() {
        return ConnectionStatus.UNKNOWN;
    }

    /**
     * Returns a range of record scores
     *
     * @param startRange nth place to start getting leaderboard scores from. Starts from 1 and goes down
     * @param stopRange  last score to retrieve
     * @return a list of scores in order from highest score to lowest
     */
    public static List<LeaderboardScore> getScores(int startRange, int stopRange) {
        ArrayList<LeaderboardScore> testLeaderboard = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            testLeaderboard.add(new LeaderboardScore("Joe", (i + 1) * 2));
        }
        return testLeaderboard;
    }


}
