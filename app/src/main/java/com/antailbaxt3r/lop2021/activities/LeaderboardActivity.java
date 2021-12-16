package com.antailbaxt3r.lop2021.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.antailbaxt3r.lop2021.R;
import com.antailbaxt3r.lop2021.leaderRV.LeaderRVAdapter;
import com.antailbaxt3r.lop2021.leaderRV.LeaderboardModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ArrayList<LeaderboardModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        rv = findViewById(R.id.leader_rv);
        DatabaseReference leaderboardRef = FirebaseDatabase.getInstance("https://sop-2021-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("leaderboard");
        leaderboardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot shot: snapshot.getChildren()) {
                    LeaderboardModel m = shot.getValue(LeaderboardModel.class);
                    m.setRank("#" + m.getRank());
                    list.add(m);
                }
                rv.setAdapter(new LeaderRVAdapter(list));
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("Leaderboard Error", error.getMessage());
            }
        });
    }
}