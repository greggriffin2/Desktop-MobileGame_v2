package com.example.sccopilotapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private ToggleButton toggleButton;
    private TextView settingsText;
    private TextView titleText;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsText = findViewById(R.id.settingsText);
        toggleButton = findViewById(R.id.toggleButton);
        titleText = findViewById(R.id.titleText);
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
     * class invariants: Everything other than the checkStatus is the same
     * @param view
     */
    public void onToggleClick(View view){ }

}
