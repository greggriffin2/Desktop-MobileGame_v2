package com.example.sccopilotapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.sccopilotapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainGameActivity";
    private EditText codeText;
    private Button playButton;


    //    private ActivityMainBinding binding;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeText = findViewById(R.id.inputCode);
        playButton = findViewById(R.id.playButton);
    }

    public void onStart(){
        super.onStart();
    }

    /**This methods purpose is to validate that what is being put into
     * the EditText is a valid entry (all letters, no numbers or symbols)
     * as well as verify with the database that the session code is correct.
     * @param validationCode
     * Return: True or False
     */
    public boolean validateConnectionCode(String validationCode){
        String correctCode = "ABCD";
        if (validationCode.equals(correctCode)) {
            return true;
        }else {
            return false;
        }
    }

    /**This methods purpose is to react to the play button
     * being pushed. This action check validation of the user code.
     * @param view
     */
    public void onClickPlay(View view){
        String validatedCode = codeText.getText().toString();
        if (validateConnectionCode(validatedCode)) {
            Log.d(TAG, "matched:success");
            Intent intent = new Intent(this, MainGameActivity.class);
            startActivity(intent);
        }else {
            Log.d(TAG,"matched:failure");
            Toast.makeText(MainActivity.this, "Connection Failed: code incorrect",
                    Toast.LENGTH_LONG).show();
        }
    }
}