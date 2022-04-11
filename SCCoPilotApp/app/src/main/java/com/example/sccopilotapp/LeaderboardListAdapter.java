package com.example.sccopilotapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sccopilotapp.gamesync.LeaderboardScore;

import java.util.ArrayList;

public class LeaderboardListAdapter extends ArrayAdapter<LeaderboardScore> {

    private final Activity context;
    ArrayList<LeaderboardScore> players;

    public LeaderboardListAdapter(Activity context, ArrayList<LeaderboardScore> players) {
        super(context, R.layout.leaderboard_custom_list, players);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.players = players;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView =
                inflater.inflate(R.layout.leaderboard_custom_list, null, true);

        TextView playerName = rowView.findViewById(R.id.LBtitle);
        TextView scoreNum = rowView.findViewById(R.id.LBsubtitle);

        playerName.setText(players.get(position).name);
        String score = Integer.toString(players.get(position).score);
        scoreNum.setText(score);

        return rowView;

    }

    ;
}
