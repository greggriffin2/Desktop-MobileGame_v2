package com.example.sccopilotapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sccopilotapp.gamesync.LeaderboardScore;
import com.example.sccopilotapp.gamesync.SynchronizationFacade;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LeaderboardActivity extends AppCompatActivity {

    ListView leaderboard;
    TextView jsonTestBox;
    String url = "https://coolspacegame.ddns.net/retrieve";

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
                jsonTestBox.setText(R.string.jsonFAILED);
                try {
                    populateScores(generateDummyData());
                } catch (Exception f) {
                    f.printStackTrace();
                }
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
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets array of names and scores from JSON request and transforms the object into an
     * ArrayList of LeaderboardScores
     */
    public List<LeaderboardScore> parseJSON(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<LeaderboardScore> parsedArray = mapper.readValue(json, new TypeReference<List<LeaderboardScore>>() {
        });
        return parsedArray;

    }

    /**
     * Instantiates local leaderboard with parseJSON(). Populates custom ListView with custom
     * ArrayAdapter
     * preconditions: HTTPS response must have succeeded
     * postconditions: Screen will have scores updated
     * class invariants: Only LB will be changed
     */
    public void populateScores(String json) throws Exception {
        List<LeaderboardScore> LB = this.parseJSON(json);
        LeaderboardListAdapter adapter = new LeaderboardListAdapter(this, (ArrayList<LeaderboardScore>) LB);
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
    public void populateScores(ArrayList<LeaderboardScore> dummyData) throws Exception {
        LeaderboardListAdapter adapter = new LeaderboardListAdapter(this, dummyData);
        leaderboard = findViewById(R.id.list);
        leaderboard.setAdapter(adapter);
    }

    // Dummy data only generates when JSON response fails
    public ArrayList<LeaderboardScore> generateDummyData() throws IOException {
        return SynchronizationFacade.getScores(0, 5);
    }
    //TODO: send user to MainActivity when game session ends
}