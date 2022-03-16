package com.example.sccopilotapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private ToggleButton toggleButton;
    private TextView settingsText;
    private TextView titleText;
    //private ActionBar actionBar = getActionBar();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsText = findViewById(R.id.settingsText);
        toggleButton = findViewById(R.id.toggleButton);
        titleText = findViewById(R.id.titleText);

        //actionBar.setDisplayHomeAsUpEnabled(true);

        // this might not work, implement onToggleClick code
        toggleButton.setOnClickListener(view -> onToggleClick(toggleButton));

    }
    public void onStart(){
        super.onStart();
    }



    /**
     * Upon a click listener's call, changes the checkStatus of the
     * ToggleButton, and sends the required settings status change
     * to the appropriate Activity
     *
     * preconditions: SettingsActivity page is loaded by the user
     * postconditions: ToggleButton's checkStatus is changed
     * class invariants: View is the same
     * @param view
     */
    public void onToggleClick(View view) {
        changeShipColor();
    }

    /**
     * Change the in-app ship color
     *
     * preconditions: onToggleClick() must be called for this method to be called
     * postconditions: ship color attribute in UserClass is changed
     * class invariants: View is the same
     */
    public void changeShipColor() {}

    /**
     * On click, sends user back to page where they were
     */
//    public boolean onOptionsItemSelected(MenuItem item){
//        Intent myIntent = new Intent(getApplicationContext(), MainGameActivity.class);
//        startActivityForResult(myIntent, 0);
//        return true;
//    }



}
