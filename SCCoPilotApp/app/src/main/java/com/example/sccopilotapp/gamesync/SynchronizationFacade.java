package com.example.sccopilotapp.gamesync;

import java.beans.PropertyChangeListener;

enum PowerUpStatus {
    None
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


}
