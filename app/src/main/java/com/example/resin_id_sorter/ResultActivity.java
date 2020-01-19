package com.example.resin_id_sorter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView changeText = findViewById(R.id.changes);
        changeText.setText("...Loading...");

        TextView changeText2 = findViewById(R.id.changes2);
        changeText.setText("...Loading...");

        final String code = getIntent().getStringExtra("CODE");

        System.out.println(code);

        switch(code) {
            case "PETE":
                changeText.setText("Polyethylene Terephthalate");
                changeText2.setText("Polyethylene Terephthalate");
                break;
            case "HDPE":
                changeText.setText("High density Polyethylene");
                changeText2.setText("Polyethylene Terephthalate");
                break;
            case "PVC":
                changeText.setText("Polyvinyl Chloride");
                changeText2.setText("Polyethylene Terephthalate");
                break;
            case "LDPE":
                changeText.setText("Low-density Polyethylene");
                changeText2.setText("Polyethylene Terephthalate");
                break;
            case "PP":
                changeText.setText("Polypropylene");
                changeText2.setText("Polyethylene Terephthalate");
                break;
            case "PS":
                changeText.setText("Polystyrene");
                changeText2.setText("Polyethylene Terephthalate");
                break;
            case "OTHER":
                changeText.setText("Other");
                changeText2.setText("Polyethylene Terephthalate");
                break;
            default:
                changeText.setText("...Loading...");
                changeText2.setText("...Loading...");
        }

        Button btn1 = (Button)findViewById(R.id.back);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        final Button btn2 = (Button)findViewById(R.id.recycle);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setText("+1");
                // Write a message to the database
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("users").child("chad").child(code);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int stat = Integer.parseInt(dataSnapshot.getValue().toString());

                        myRef.setValue(stat + 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
