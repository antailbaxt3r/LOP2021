package com.antailbaxt3r.lop2021.leaderRV;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.lop2021.R;

public class LeaderViewHolder extends RecyclerView.ViewHolder {
    private TextView rank, name, points;
    public LeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        rank = itemView.findViewById(R.id.rank);
        name = itemView.findViewById(R.id.name_leader);
        points = itemView.findViewById(R.id.points);
    }
    public void populate(LeaderboardModel i) {
        rank.setText(i.getRank());
        name.setText(i.getName());
        points.setText(i.getPoints());
    }
}
