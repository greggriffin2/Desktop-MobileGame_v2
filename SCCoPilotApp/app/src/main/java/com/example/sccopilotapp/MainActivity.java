package com.example.sccopilotapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sccopilotapp.gamesync.GameSyncSingleton;
import com.example.sccopilotapp.gamesync.JoinRoom;
import com.example.sccopilotapp.gamesync.SynchronizationFacade;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainGameActivity";
    private EditText codeText;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SynchronizationFacade syncFacade = new SynchronizationFacade("wss://pedanticmonkey.space/rooms", getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeText = findViewById(R.id.inputCode);
        playButton = findViewById(R.id.playButton);
        /**
         * Make Home Action Bar Red (shipRed in colors.xml)
         */
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#BF2B16"));
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void onStart() {
        super.onStart();
    }

    /**
     * This methods purpose is to validate that what is being put into
     * the EditText is a valid entry (all letters, no numbers or symbols)
     * as well as verify with the database that the session code is correct.
     *
     * @param validationCode Return: True or False
     */
    public boolean validateConnectionCode(String validationCode) {
        //String correctCode = "a";
        if (validationCode.length() > 18) {
            Toast.makeText(MainActivity.this, "Code must be less than 18 characters!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (validationCode.matches(".*\\d.*")){
            Toast.makeText(MainActivity.this, "Code must not contain any numbers!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (validationCode.trim().length() == 0){
            Toast.makeText(MainActivity.this, "Code must not be empty!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (validationCode.trim().length() != 0){
            for(int i = 0; i < validationCode.length(); i++){
                if (validationCode.contains(" ")){
                    Toast.makeText(MainActivity.this, "Code cannot contain spaces!", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
            return true;
        }
        else{
            return true;
        }
    }

    /**
     * This methods purpose is to react to the play button
     * being pushed. This action check validation of the user code.
     *
     * @param view
     */
    public void onClickPlay(View view) {
        Log.d(TAG, "onClickPlay: Creating sync singleton");
        SynchronizationFacade.connect(codeText.getText().toString());
        String validatedCode = codeText.getText().toString();
        if (validateConnectionCode(validatedCode) == true) {
            Log.d(TAG, "matched:success");
            Intent intent = new Intent(this, MainGameActivity.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "matched:failure");
            Toast.makeText(MainActivity.this, "Connection Failed: code incorrect",
                    Toast.LENGTH_LONG).show();
        }
    }

}