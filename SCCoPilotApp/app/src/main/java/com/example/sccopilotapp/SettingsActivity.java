package com.example.sccopilotapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private ToggleButton toggleButton;
    private TextView settingsText;
    private TextView titleText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsText = findViewById(R.id.settingsText);
        toggleButton = findViewById(R.id.toggleButton);
        titleText = findViewById(R.id.titleText);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Settings");
        toggleButton.setOnClickListener(view -> onToggleClick(toggleButton));
    }

    /**
     * Adds back button functionality
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onStart() {
        super.onStart();
    }


    /**
     * Changes the checkStatus of the
     * ToggleButton, and sends the required settings status change
     * to the appropriate Activity
     * preconditions: SettingsActivity page is loaded by the user
     * postconditions: ToggleButton's checkStatus is changed
     * class invariants: View is the same
     *
     * @param view
     */
    public void onToggleClick(View view) {
        MainGameActivity.changeBackground();
        int currInt = MainGameActivity.selectedBackground;
        String currString;
        if (currInt == MainGameActivity.background_1){
            currString = "WHIRL";
        } else {
            currString = "WARP";
        }
        Toast.makeText(this,
                "Background changed to " + currString,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * On click, sends user back to page where they were
     */
//    public boolean onOptionsItemSelected(MenuItem item){
//        Intent myIntent = new Intent(getApplicationContext(), MainGameActivity.class);
//        startActivityForResult(myIntent, 0);
//        return true;
//    }


}
