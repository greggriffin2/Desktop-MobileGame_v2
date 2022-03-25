package com.example.sccopilotapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private ToggleButton toggleButton;
    private TextView settingsText;
    private TextView titleText;
    private int gifNumber = 0;
    //private ActionBar actionBar = getActionBar();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsText = findViewById(R.id.settingsText);
        toggleButton = findViewById(R.id.toggleButton);
        titleText = findViewById(R.id.titleText);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // this might not work, implement onToggleClick code
        toggleButton.setOnClickListener(view -> onToggleClick(toggleButton));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onStart() {
        super.onStart();
    }


    /**
     * Upon a click listener's call, changes the checkStatus of the
     * ToggleButton, and sends the required settings status change
     * to the appropriate Activity
     * <p>
     * preconditions: SettingsActivity page is loaded by the user
     * postconditions: ToggleButton's checkStatus is changed
     * class invariants: View is the same
     *
     * @param view
     */
    public void onToggleClick(View view) {
        changeBackgroundColor();
    }

    /**
     * Change the in-app ship color
     * <p>
     * preconditions: onToggleClick() must be called for this method to be called
     * postconditions: ship color attribute in UserClass is changed
     * class invariants: View is the same
     */
    public void changeBackgroundColor() {
        if (MainGameActivity.getGifNum() == 0) {
            MainGameActivity.setGifNum(1);
        } else {
            MainGameActivity.setGifNum(0);
        }
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
