package com.example.resin_id_sorter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        // Read from the database
        myRef.child("chad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Pie pie = AnyChart.pie();

                List<DataEntry> data = new ArrayList<>();
                int counter = 1;

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ds.getClass().getName();
                    if(counter == 0)
                    {
                        System.out.println(ds.getValue());
                    }
                    else if(counter == 1)
                    {
                        int i = Integer.parseInt(ds.getValue().toString());
                        data.add(new ValueDataEntry("HDPE", i));
                    }
                    else if(counter == 2)
                    {
                        int i = Integer.parseInt(ds.getValue().toString());
                        data.add(new ValueDataEntry("LDPE", i));
                    }
                    else if(counter == 3)
                    {
                        int i = Integer.parseInt(ds.getValue().toString());
                        data.add(new ValueDataEntry("OTHER", i));
                    }
                    else if(counter == 4)
                    {
                        int i = Integer.parseInt(ds.getValue().toString());
                        data.add(new ValueDataEntry("PETE", i));
                    }
                    else if(counter == 5)
                    {
                        int i = Integer.parseInt(ds.getValue().toString());
                            data.add(new ValueDataEntry("PP", i));
                    }
                    else if(counter == 6)
                    {
                        int i = Integer.parseInt(ds.getValue().toString());
                        data.add(new ValueDataEntry("PS", i));
                    }
                    else if(counter == 7)
                    {
                        int i = Integer.parseInt(ds.getValue().toString());
                        data.add(new ValueDataEntry("V", i));
                    }
                    counter++;
                }

                pie.setData(data);

                AnyChartView anyChartView = findViewById(R.id.any_chart_view);
                anyChartView.setChart(pie);
                System.out.println(data);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("myTag", "Failed to read value.", error.toException());
            }
        });

        Button btn1 = (Button)findViewById(R.id.back);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatsActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
}
