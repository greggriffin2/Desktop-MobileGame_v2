package com.example.sccopilotapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sccopilotapp.gamesync.LeaderboardScore;
import com.example.sccopilotapp.gamesync.SynchronizationFacade;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    private TextView boardTitle;
    private TextView player1;
    private TextView player1Score;
    private TextView player2;
    private TextView player2Score;
    private TextView player3;
    private TextView player3Score;
    private TextView player4;
    private TextView player4Score;
    private TextView player5;
    private TextView player5Score;

    private ArrayList<TextView> players = new ArrayList<>(5);
    private ArrayList<TextView> scores = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        boardTitle = findViewById(R.id.boardTitle);

        player1 = findViewById(R.id.player1);
        player1Score = findViewById(R.id.player_1Score);
        player2 = findViewById(R.id.player2);
        player2Score = findViewById(R.id.player_2Score);
        player3 = findViewById(R.id.player3);
        player3Score = findViewById(R.id.player_3Score);
        player4 = findViewById(R.id.player4);
        player4Score = findViewById(R.id.player_4Score);
        player5 = findViewById(R.id.player5);
        player5Score = findViewById(R.id.player_5Score);
        populateScores();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
     * Calls to the facade and gets array of the players and their scores,
     * helper function to populateScores()
     */
    public List<LeaderboardScore> getLeaderboard() {
        return SynchronizationFacade.getScores(0, 5);
    }

    /**
     * Sets local players and scores Arrays to point to the TextView elements that will be updated,
     * then gets the Leaderboard from the websocket, then populates the screen (TextViews) with
     * that data.
     * preconditions: There must be a valid Object returned from SynchronizationFacade.getScores()
     * postconditions: Screen will have scores updated
     * class invariants: Nothing except for the TextView elements will be changed
     */
    public void populateScores() {

        // populates local lists players and scores, pointing to the TextView elements
        players.add(player1);
        scores.add(player1Score);
        players.add(player2);
        scores.add(player2Score);
        players.add(player3);
        scores.add(player3Score);
        players.add(player4);
        scores.add(player4Score);
        players.add(player5);
        scores.add(player5Score);
        List<LeaderboardScore> LB = this.getLeaderboard();

        for (int i = 0; i < LB.size(); i++) {
            players.get(i).setText((CharSequence) LB.get(i).name);
            scores.get(i).setText((CharSequence) Integer.toString(LB.get(i).score));
        }
    }

}