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
    boolean requestFailed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        try {
            populateScores();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    requestFailed = false;
                    final String myResponse = (Objects.requireNonNull(response.body())).toString();

                    // This only works in the activity that it is is running in. Why I put it in here
                    // instead of SyncFacade
                    LeaderboardActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: replace this with method call to parse JSON into ArrayList
                            // may want to use JSONObject instead of a String
                            jsonTestBox.setText(myResponse);
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
    public List<LeaderboardScore> getLeaderboard() throws Exception {
        if (requestFailed) {
            // return dummy data
            return SynchronizationFacade.getScores(0, 5);
        } else { // TODO: implement JSON parsing into ArrayList<LeaderboardScore> here
            return new ArrayList<LeaderboardScore>(5);
        }
    }

    /**
     * Instantiates local leaderboard with getLeaderboard(). Populates custom ListView with custom
     * ArrayAdapter
     * preconditions: There must be a valid Object returned from SynchronizationFacade.getScores()
     * postconditions: Screen will have scores updated
     * class invariants: Only LB will be changed
     */
    public void populateScores() throws Exception {
        ArrayList<LeaderboardScore> LB = (ArrayList<LeaderboardScore>) this.getLeaderboard();
        LeaderboardListAdapter adapter = new LeaderboardListAdapter(this, LB);
        leaderboard = findViewById(R.id.list);
        leaderboard.setAdapter(adapter);
    }
}