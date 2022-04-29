package com.example.sccopilotapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.sccopilotapp.gamesync.SynchronizationFacade;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainGameActivity extends AppCompatActivity {

    String TAG = "MainGameActivity";
    //    Button shipButton;
    Button upgradesButton;
    Button leaderboardButton;
    Button exitButton;

    int powerMax = 0;
    ImageView backgroundGIF;
    ImageView shipClick;
    static int background_1 = R.drawable.space_background1;
    static int background_2 = R.drawable.space_background2;
    static int selectedBackground = background_1;

    /**
     * Creates the ActionBar at the top of the screen
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        // TODO: change this to be a listener for when the game disconnects
//        SynchronizationFacade.addUserDisconnectedEvent(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
//                // add actions to be performed on event here
//                disconnectedDialogPopup();
//            }
//        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        backgroundGIF = findViewById(R.id.background);
        loadBackground();

//        shipButton = findViewById(R.id.shipButton);
        upgradesButton = findViewById(R.id.upgradesButton);
        upgradesButton.setVisibility(View.INVISIBLE); // not using this, leaves room for Toast
        leaderboardButton = findViewById(R.id.leaderboardButton);
        exitButton = findViewById(R.id.exitButton);
        shipClick = findViewById(R.id.shipClick);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("Space Cadet Co-Pilot");
        // handle button activities
    }

    public void loadBackground() {
        // Loads background GIF
        Glide.with(this)
                .load(selectedBackground)
                .centerCrop()
                .into(backgroundGIF);
    }

    /**
     * Handles button presses for the ActionBar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mybutton) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.help_button) {
            toolTipPopup();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method sends a buttonPressed signal to gamesync whenever the button is pressed.
     * Also handles UI changes around the button press
     *
     * @param view
     */
    public void onClickShip(View view) {
        SynchronizationFacade.fireButtonPressed(69);
        Log.d(TAG, "Click");
        if (powerMax < 10) { // 10 corresponds with how many clicks Game takes to update health
            powerMax += 1;
            float x = shipClick.getScaleX();
            float y = shipClick.getScaleY();
            shipClick.setScaleX((float) (x + .05));
            shipClick.setScaleY((float) (y + .05));
        } else {
            //Send info to game
            Toast.makeText(MainGameActivity.this, R.string.toast_text, Toast.LENGTH_SHORT).show();
            float x = shipClick.getScaleX();
            float y = shipClick.getScaleY();
            shipClick.setScaleX((float) (x - .5));
            shipClick.setScaleY((float) (y - .5));
            powerMax = 0;
        }

    }

    /**
     * This method will wait for the user to click the button
     * and once it is clicked, it will start the upgrades activity.
     *
     * @param view
     */
    public void onClickUpgrades(View view) {
        Intent intent = new Intent(this, UpgradesActivity.class);
        startActivity(intent);
    }

    /**
     * This method will wait for the user to click the button and once
     * clicked, will launch the leaderboard activity. The leaderboard activity
     * requires data from the database to display updated leaderboards.
     *
     * @param view
     */
    public void onClickLeaderboard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    /**
     * This method will wait for the user to click the button and once clicked,
     * will exit out of the current session back to the landing screen for a new code entry.
     *
     * @param view
     */
    public void onClickExit(View view) {
        exitConfirmationPopup();
    }

    public static void changeBackground() {
        if (selectedBackground == background_1) {
            selectedBackground = background_2;
        } else {
            selectedBackground = background_1;
        }
    }

    public void toolTipPopup() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.MGA_tooltip);
        alertDialogBuilder.setTitle("Need Help?");
        alertDialogBuilder.setPositiveButton("Got it!", (dialogInterface, i) -> {
            Log.d("tooltip", "Closed - MainGameActivity");

        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void exitConfirmationPopup() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.exit_confirmation);
        alertDialogBuilder.setTitle("Are you sure you want to exit?");
        alertDialogBuilder.setPositiveButton("Leave", (dialogInterface, i) -> {
            Log.d("tooltip", "Accepted - Exited MainGameActivity");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        alertDialogBuilder.setNegativeButton("Stay", (dialogInterface, i) -> {
            Log.d("tooltip", "Denied - Remained in session");
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void disconnectedDialogPopup() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.disconnecteddialog);
        alertDialogBuilder.setTitle("Disconnected from Session");
        alertDialogBuilder.setPositiveButton("Ok", (dialogInterface, i) -> {
            Log.d("tooltip", "Accepted - Exited MainGameActivity, DISCONNECTED");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }


    public void onResume() {
        super.onResume();
        loadBackground();
    }
}
