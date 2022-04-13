package com.example.sccopilotapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sccopilotapp.gamesync.LeaderboardScore;
import com.example.sccopilotapp.gamesync.SynchronizationFacade;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInput;
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
    boolean responseFailed = true;

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

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseFailed = false;
                    String json = (Objects.requireNonNull(response.body())).string();
                    try {
                        populateScores(json);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // This only works in the activity that it is is running in. Why I put it in here
                    // instead of SyncFacade
                    LeaderboardActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // may want to use JSONObject instead of a String
                            jsonTestBox.setText(json);
//                            try {
//                                populateScores();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                        }
                    });
                }
            }
        });
        if (responseFailed){
            try {
                populateScores(generateDummyData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    public ArrayList<LeaderboardScore> parseJSON(String json) throws Exception {
         // TODO: implement JSON parsing into ArrayList<LeaderboardScore> here
            ObjectMapper mapper = new ObjectMapper();
            // TODO: this does not parse correctly
            LeaderboardScore parsedArray = mapper.readValue(json, LeaderboardScore.class);
            ArrayList<LeaderboardScore> result = new ArrayList<>(5);
            result.add(parsedArray);
            return result;

    }

    /**
     * Instantiates local leaderboard with parseJSON(). Populates custom ListView with custom
     * ArrayAdapter
     * preconditions: HTTPS response must have succeeded
     * postconditions: Screen will have scores updated
     * class invariants: Only LB will be changed
     */
    public void populateScores(String json) throws Exception {
        ArrayList<LeaderboardScore> LB = this.parseJSON(json);
        LeaderboardListAdapter adapter = new LeaderboardListAdapter(this, LB);
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

    public ArrayList<LeaderboardScore> generateDummyData() throws IOException {
        return SynchronizationFacade.getScores(0, 5);
    }
}