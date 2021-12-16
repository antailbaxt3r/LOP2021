package com.antailbaxt3r.lop2021.leaderRV;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.lop2021.R;

import java.util.ArrayList;

public class LeaderRVAdapter extends RecyclerView.Adapter<LeaderViewHolder>{

    private ArrayList<LeaderboardModel> list;

    public LeaderRVAdapter(ArrayList<LeaderboardModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public LeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderViewHolder holder, int position) {
        holder.populate(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
