package com.example.sccopilotapp.gamesync;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

enum PowerUpStatus {
    None
}

enum ConnectionStatus {
    CONNECTED,
    DISCONNECTED,
    UNKNOWN
}

public class SynchronizationFacade {

    private static GameSyncSingleton sync;

    SynchronizationFacade(String remoteAddress, int remotePort) {
        if (sync != null) {
            if (!sync.getRemoteAddress().equals(remoteAddress) || sync.getRemotePort() != remotePort) {

            }
        }
    }

    /**
     * Updates the Signaling and Scoreboard server address
     *
     * @param remoteAddress
     * @param remotePort
     */
    public static void updateAddress(String remoteAddress, int remotePort) {

    }

    /**
     * Attempts to connect to a game using the provided PairingCode
     *
     * @param pairingCode that pairs to a particular (currently running) game
     */
    void connect(String pairingCode) {

    }


    /**
     * Adds an event to be fired when something changes with a PowerUp
     *
     * @param listener to be fired
     */
    void addPowerUpEvent(PropertyChangeListener listener) {

    }

    /**
     * Adds an event to be fired when the game is paused
     *
     * @param listener to be fired
     */
    void addPausedEvent(PropertyChangeListener listener) {

    }

    /**
     * Adds an event to be fired when the game is resumed
     *
     * @param listener
     */
    void addUnpausedEvent(PropertyChangeListener listener) {

    }

    /**
     * Called when a button is pressed in the UI
     *
     * @param ButtonPress
     */
    void fireButtonPressed(int ButtonPress) {

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
        return new ArrayList<LeaderboardScore>();
    }


}
