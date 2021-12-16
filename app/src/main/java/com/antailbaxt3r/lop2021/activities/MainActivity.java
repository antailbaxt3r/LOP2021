package com.antailbaxt3r.lop2021.activities;

import static com.github.mikephil.charting.charts.CombinedChart.DrawOrder.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.antailbaxt3r.lop2021.R;
import com.antailbaxt3r.lop2021.utils.SharedPrefs;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SharedPrefs prefs;
    private CombinedChart chart;
    private TextView helloBanner;
    private DatabaseReference globalRef, userRef;
    private ArrayList<BarEntry> entries1 = new ArrayList<>();
    private ArrayList<Entry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = new SharedPrefs(this);
        prefs.saveUID("123");
        prefs.saveName("Arjun");
        globalRef = FirebaseDatabase.getInstance("https://sop-2021-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("globalData");
        userRef = FirebaseDatabase.getInstance("https://sop-2021-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users").child(prefs.getUID());
        entries1 = new ArrayList<>();

        initViews();
    }

    private void initViews() {
        chart = findViewById(R.id.main_graph);
        helloBanner = findViewById(R.id.hello_banner);
        userRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                helloBanner.setText("Hello, " + snapshot.getValue().toString() + "!");
                Log.i("Firebase update", "Fetched name");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setUpChart();
    }

    private void setUpChart() {
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.setAutoScaleMinMaxEnabled(true);

        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
            BAR, LINE
        });

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setEnabled(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                cal.set(Calendar.MONTH, (int) value-1);
                String month_name = month_date.format(cal.getTime());
                return month_name;
            }
        });

        CombinedData data = new CombinedData();

//        globalRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(int i = 0; i < 12; i++) {
//                    double d = Double.parseDouble(snapshot.child(String.valueOf(i+1)).getValue().toString());
//                    int v = (int) d;
//                    entries1.add(new BarEntry(i, 2));
////                    Log.i("Bar Entry", String.valueOf(entries1.get(i).getX()));
//                }
////                data.setData(generateBarData());
////                chart.setData(data);
////                chart.invalidate();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        for(int i = 0; i < 12; i++) {
//            double d = Double.parseDouble(snapshot.child(String.valueOf(i+1)).getValue().toString());
//            int v = (int) d;
            entries1.add(new BarEntry(i+1, (new Random()).nextInt(1200) + 400));
//                    Log.i("Bar Entry", String.valueOf(entries1.get(i).getX()));
        }
        data.setData(generateBarData());
        chart.setData(data);
        userRef.child("monthly").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot: snapshot.getChildren()) {
                    entries.add(new Entry(Integer.parseInt(shot.getKey()), Float.parseFloat(shot.getValue().toString())));
                }
                data.setData(generateLineData());
                chart.setData(data);
                chart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);


    }

    private LineData generateLineData() {

        LineData d = new LineData();

        LineDataSet set = new LineDataSet(entries, "Your monthly consumption");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {

//        for(int i = 0; i < 12; i++) {
//            entries1.add(new BarEntry(i+1, i*5));
//        }

        BarDataSet set1 = new BarDataSet(entries1, "Average Monthly Consumption In Your State");
        set1.setColor(Color.rgb(157, 240, 215));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);

        return d;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_bill:
                Intent i = new Intent(this, AddBillActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.leaderboard:
                Intent i1 = new Intent(this, LeaderboardActivity.class);
                startActivity(i1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}