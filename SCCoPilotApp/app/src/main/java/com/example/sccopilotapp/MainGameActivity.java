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
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.sccopilotapp.gamesync.SynchronizationFacade;

public class MainGameActivity extends AppCompatActivity {

    String TAG = "MainGameActivity";
    Button shipButton;
    Button upgradesButton;
    Button leaderboardButton;
    Button exitButton;
    int powerMax = 0;
    ImageView backgroundGIF;
    int selectedBackground;
    static final int BACKGROUND_WHIRL = 0;
    static final int BACKGROUND_WARP = 1;
    private static int gifNum = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        backgroundGIF = findViewById(R.id.background);
        loadBackground();

        shipButton = findViewById(R.id.shipButton);
        upgradesButton = findViewById(R.id.upgradesButton);
        leaderboardButton = findViewById(R.id.leaderboardButton);
        exitButton = findViewById(R.id.exitButton);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // handle button activities
    }

    public void loadBackground(){
        if (gifNum == BACKGROUND_WHIRL) {
            selectedBackground = R.drawable.space_background1;
        } else {
            selectedBackground = R.drawable.space_background2;
        }
        // Loads background GIF
        Glide.with(this)
                .load(selectedBackground)
                .centerCrop()
                .into(backgroundGIF);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mybutton) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method will listen for the clicks on the ship and add
     * to a running total that will eventually be sent to the game
     * to let it know when the power up bar is full.
     *
     * @param view
     */
    public void onClickShip(View view) {
        SynchronizationFacade.fireButtonPressed(1);
        Log.d(TAG, "Click");
        if (powerMax < 100) {
            powerMax += 1;
        } else {
            //Send info to game
            Toast.makeText(MainGameActivity.this, "Fully Powered!",
                    Toast.LENGTH_SHORT).show();
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

    }

    /**
     * This method will wait for the user to click the button and once clicked, will open
     * the settings menu
     *
     * @param view
     */
    public void onClickSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public static void setGifNum(int num) {
        gifNum = num;
    }

    public static int getGifNum() {
        return gifNum;
    }

    public void onResume() {
        super.onResume();
        loadBackground();
    }
}
