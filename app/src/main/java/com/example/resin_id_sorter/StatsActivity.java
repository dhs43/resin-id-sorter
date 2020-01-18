package com.example.resin_id_sorter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("PETE", 2));
        data.add(new ValueDataEntry("HDPE", 3));
        data.add(new ValueDataEntry("PVC", 10));
        data.add(new ValueDataEntry("LDPE", 7));
        data.add(new ValueDataEntry("PP", 3));
        data.add(new ValueDataEntry("PS", 0));
        data.add(new ValueDataEntry("Other", 5));

        pie.setData(data);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);

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
