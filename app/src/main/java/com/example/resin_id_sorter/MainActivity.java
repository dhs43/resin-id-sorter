package com.example.resin_id_sorter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLRemoteModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn1 = (Button)findViewById(R.id.button_scan);
        Button btn2 = (Button)findViewById(R.id.button_stats);
        btn1.setEnabled(false);

        FirebaseAutoMLRemoteModel remoteModel = new FirebaseAutoMLRemoteModel.Builder("plastics_202011820648").build();

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        btn1.setEnabled(true);
                    }
                });

        ListView listView = (ListView) findViewById(R.id.list);
        ArrayList<String> arrayList=new ArrayList<>();

        arrayList.add("\n \nIMPORTANT NOTICE:\n \nRules and regulations can differ by location; check with your local waste management services for details on what can and can not be recycled locally.");
        arrayList.add("1 – PETE – Polyethylene Terephthalate\n" +
                "\n" +
                "The easiest plastics to recycle. Often used for soda bottles, water bottles and many common food packages. Is recycled into bottles and polyester fibers. Picked up through most curbside recycling programs.");
        arrayList.add("2 – HDPE – High density Polyethylene\n" +
                "\n" +
                "Also readily recyclable – Mostly used for packaging detergents, bleach, milk containers, hair care products and motor oil. Is recycled into more bottles or bags. Picked up through most curbside recycling programs, although some allow only those containers with necks.");
        arrayList.add("3 – PVC – Polyvinyl Chloride\n" +
                "\n" +
                "This stuff is everywhere – pipes, toys, furniture, packaging – you name it. Difficult to recycle and PVC is a major environmental and health threat.\n");
        arrayList.add("4 – LDPE - Low-density Polyethylene\n" +
                "\n" +
                "Used for many different kinds of wrapping, grocery bags and sandwich bags and can be recycled into more of the same. LDPE is not often recycled through curbside programs, but some communities will accept it. Plastic shopping bags can be returned to many stores for recycling.");
        arrayList.add("5 – PP – Polypropylene\n" +
                "\n" +
                "Clothing, bottles, tubs and ropes. Can be recycled into fibers. Number 5 plastics can be recycled through some curbside programs.");
        arrayList.add("6 – PS – Polystyrene\n" +
                "\n" +
                "Cups, foam food trays, packing peanuts. Polystyrene (also known as styrofoam) is a real problem as it’s bulky yet very lightweight and that makes it difficult to recycle. For example, a carload of expanded polystyrene would weigh next to nothing so there’s not a lot of materials to reclaim, particularly when you take into account the transport getting it to the point of recycling. It can however be reused. Number 6 plastics can be recycled through some curbside programs.");
        arrayList.add("7 – Other\n" +
                "\n" +
                "Could be a mixture of any and all of the above or plastics not readily recyclable such as polyurethane. Avoid it if you can – recyclers generally speaking don’t want it.");
        arrayList.add("\n \nIMPORTANT NOTICE:\n \nRules and regulations differ by location; check with your local waste management services for details on what can and can not be recycled locally.");
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.list1,arrayList);
        listView.setAdapter(arrayAdapter);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StatsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}