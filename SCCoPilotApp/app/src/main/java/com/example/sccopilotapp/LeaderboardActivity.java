package com.example.sccopilotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sccopilotapp.gamesync.LeaderboardScore;
import com.example.sccopilotapp.gamesync.SynchronizationFacade;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    private TextView boardTitle;
    private TextView player1;
    private TextView player1Score;

    private ArrayList<String> players;
    private ArrayList<Integer> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        boardTitle = findViewById(R.id.boardTitle);
        player1 = findViewById(R.id.player1);
        player1Score = findViewById(R.id.player_1Score);
    }

    /**
     * On click, sends user back to page where they were
     */
    public void back(){ }

    /**
     * Calls to the facade and gets arrays of the players and their scores,
     * then populates the local arrays to those values
     */
    public List<LeaderboardScore> getLeaderboard(){
        return SynchronizationFacade.getScores(0,5);
    }

}