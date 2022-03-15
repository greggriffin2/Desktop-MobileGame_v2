package com.example.sccopilotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sccopilotapp.gamesync.LeaderboardScore;

import java.lang.reflect.Array;
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
        populateScores();
    }

    /**
     * Calls to the facade and gets arrays of the players and their scores,
     * then populates the local arrays to those values
     */
    public ArrayList<LeaderboardScore> getLeaderboard(){
        //return SynchronizationFacade.getScores(0,5);

        // Test code

        ArrayList<LeaderboardScore> testLeaderboard = new ArrayList<>(5);
        for(int i = 0; i < 5; i++){
            testLeaderboard.add(new LeaderboardScore("Joe", (i + 1) * 2 ));
        }
        return testLeaderboard;
    }
    protected void populateScores(){

        this.players.add(player1);
        this.scores.add(player1Score);
        this.players.add(player2);
        this.scores.add(player2Score);
        this.players.add(player3);
        this.scores.add(player3Score);
        ArrayList<LeaderboardScore> LB = this.getLeaderboard();

        for(int i = 0; i < players.size(); i++){
            players.get(i).setText((CharSequence) LB.get(i).name);
            scores.get(i).setText((CharSequence) Integer.toString(LB.get(i).score));
        }
    }

}