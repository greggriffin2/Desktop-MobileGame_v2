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

    /** Populates the custom XML file leaderboard_custom_list with leaderboard
     * data to be inflated into rows of a ListView
     * @param context Called by the LeaderboardActivity
     * @param players The ArrayList of players and scores
     */
    public LeaderboardListAdapter(Activity context, ArrayList<LeaderboardScore> players) {
        super(context, R.layout.leaderboard_custom_list, players);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.players = players;

    }

    /**
     * Transforms the array of data into RowViews to be put into the custom ListView
     * @param position
     * @param view
     * @param parent
     * @return
     */
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
