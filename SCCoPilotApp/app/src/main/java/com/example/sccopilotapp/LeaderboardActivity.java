package com.example.sccopilotapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sccopilotapp.gamesync.LeaderboardScore;
import com.example.sccopilotapp.gamesync.SynchronizationFacade;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LeaderboardActivity extends AppCompatActivity {

    ListView leaderboard;
    Vector<LeaderboardScore> LB;
    TextView jsonTestBox;
    String url = "https://coolspacegame.ddns.net/retrieve";
    String filterText;

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
        getMenuInflater().inflate(R.menu.leaderboard_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Leaderboard");
        jsonTestBox = findViewById(R.id.jsonTestBox);

        // JSON request starts here
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Handler mainHandler = new Handler(this.getMainLooper());
        client.newCall(request).enqueue(new Callback() {
            @Override
            // If request fails
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                // TODO: this code never runs. Find how to make it recognize a failure and run this code
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jsonTestBox.setText(R.string.jsonFAILED);
                            populateScores(generateDummyData());
                        } catch (Exception f) {
                            f.printStackTrace();
                        }
                    }
                });
            }

            @Override
            // If request succeeds
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = (Objects.requireNonNull(response.body())).string();
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                jsonTestBox.setVisibility(View.GONE);
                                populateScores(json);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        if (id == R.id.leaderboard_reload){
            reloadActivity();
        }
        if (id == R.id.leaderboard_filter){
            filterPrompt();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets array of names and scores from JSON request and transforms the object into a
     * List of LeaderboardScores
     */
    public Vector<LeaderboardScore> parseJSON(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<Vector<LeaderboardScore>>() {
        });

    }

    /**
     * Instantiates local leaderboard with parseJSON(). Populates custom ListView with custom
     * ArrayAdapter
     * preconditions: HTTPS response must have succeeded
     * postconditions: Screen will have scores updated
     * class invariants: Only LB will be changed
     */
    public void populateScores(String json) throws Exception {
        this.LB = this.parseJSON(json);
        LeaderboardListAdapter adapter = new LeaderboardListAdapter(this, this.LB);
        leaderboard = findViewById(R.id.list);
        leaderboard.setAdapter(adapter);
    }

    /**
     * Builds and displays a prompt to enter a leaderboard filter
     */
    public void filterPrompt(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter by name");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Filter", (dialog, which) -> {
            filterScores(input.getText().toString());


        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });

        builder.create().show();
    }

    /**
     * Taking a copy of the fetched leaderboard, removes items from it that match a String filter
     * given by the user. Refreshes the leaderboard after
     * @param filter
     */
    public void filterScores(String filter){
        Vector<LeaderboardScore> temp = this.LB;
        Iterator<LeaderboardScore> iterator = temp.iterator();
        while (iterator.hasNext()) {
            LeaderboardScore item = iterator.next();

            if (!item.name.equalsIgnoreCase(filter))
                iterator.remove();
        }
        leaderboard.invalidateViews();
        LeaderboardListAdapter adapter = new LeaderboardListAdapter(this, temp);
        leaderboard = findViewById(R.id.list);
        leaderboard.setAdapter(adapter);
    }

    /**
     * Dummy data version of populateScores
     * Populates custom ListView with custom ArrayAdapter
     * preconditions: There must be a valid Object returned from SynchronizationFacade.getScores()
     * postconditions: Screen will have scores updated
     * class invariants: Only LB will be changed
     */
    public void populateScores(Vector<LeaderboardScore> dummyData) throws Exception {
        LeaderboardListAdapter adapter = new LeaderboardListAdapter(this, dummyData);
        leaderboard = findViewById(R.id.list);
        leaderboard.setAdapter(adapter);
    }

    // Dummy data only generates when JSON response fails
    public Vector<LeaderboardScore> generateDummyData() throws IOException {
        return SynchronizationFacade.getScores(0, 5);
    }
    //TODO: send user to MainActivity when game session ends

    public void reloadActivity(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}