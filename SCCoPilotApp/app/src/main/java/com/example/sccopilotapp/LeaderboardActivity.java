package com.example.sccopilotapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sccopilotapp.gamesync.LeaderboardScore;
import com.example.sccopilotapp.gamesync.SynchronizationFacade;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    ListView leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        populateScores();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Leaderboard");
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

    /**
     * Gets array of names and scores from facade. Size is hardcoded.
     */
    public List<LeaderboardScore> getLeaderboard() {
        return SynchronizationFacade.getScores(0, 5);
    }

    /**
     * Instantiates local leaderboard with getLeaderboard(). Populates custom ListView with custom
     * ArrayAdapter
     * preconditions: There must be a valid Object returned from SynchronizationFacade.getScores()
     * postconditions: Screen will have scores updated
     * class invariants: Only LB will be changed
     */
    public void populateScores() {

        // populates ListView rows with DB leaderboard data
        ArrayList<LeaderboardScore> LB = (ArrayList<LeaderboardScore>) this.getLeaderboard();

        LeaderboardListAdapter adapter = new LeaderboardListAdapter(this, LB);
        leaderboard = findViewById(R.id.list);
        leaderboard.setAdapter(adapter);
    }

}