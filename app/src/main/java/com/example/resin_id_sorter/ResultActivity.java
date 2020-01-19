package com.example.resin_id_sorter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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


        ImageView img= (ImageView) findViewById(R.id.changeImage);
        img.setImageResource(R.drawable.mark);


        final String code = getIntent().getStringExtra("CODE");

        System.out.println(code);

        switch(code) {
            case "PETE":
                changeText.setText("Polyethylene Terephthalate (1 - PETE)");
                img.setImageResource(R.drawable.pete_1);
                changeText2.setText("The easiest plastics to recycle. Often used for soda bottles, water bottles and many common food packages. Is recycled into bottles and polyester fibers. Picked up through most curbside recycling programs.\n \n" +
                        "Common Product Description: \n •Clear and optically smooth surfaces\n" +
                        "• Excellent barrier to oxygen, water, and carbon dioxide \n" +
                        "• High impact capability and shatter resistance \n" +
                        "• Excellent resistance to most solvents \n" +
                        "• Capability for hot filling \n");
                break;
            case "HDPE":
                changeText.setText("High density Polyethylene (2 - HDPE)");
                img.setImageResource(R.drawable.hdpe_2);
                changeText2.setText("Also readily recyclable – Mostly used for packaging detergents, bleach, milk containers, hair care products and motor oil. Is recycled into more bottles or bags. Picked up through most curbside recycling programs, although some allow only those containers with necks.\n" +
                        "\n" +
                        "Common Product Description:\n" +
                        "• Excellent resistance to solvents \n" +
                        "• Higher tensile strength\n" +
                        "• Relatively stiff material with useful temperature capabilities\n");
                break;
            case "V":
                changeText.setText("Polyvinyl Chloride (3 - V)");
                img.setImageResource(R.drawable.pvc_3);
                changeText2.setText("This stuff is everywhere – pipes, toys, furniture, packaging – you name it. Difficult to recycle and PVC is a major environmental and health threat. Most curbside recycling programs will not accept this waste; do not recycle directly.\n" +
                        "\n" +
                        "Common Product Description:\n" +
                        "• High impact strength, brilliant clarity, excellent processing performance \n" +
                        "• Resistance to grease, oil and chemicals \n");
                break;
            case "LDPE":
                changeText.setText("Low-density Polyethylene (4 - LDPE)");
                img.setImageResource(R.drawable.ldpe_4);
                changeText2.setText("Used for many different kinds of wrapping, grocery bags and sandwich bags and can be recycled into more of the same. LDPE is not often recycled through curbside programs, but some communities will accept it. Plastic shopping bags can be returned to many stores for recycling.\n" +
                        "\n" +
                        "Common Product Description:\n" +
                        "• Excellent resistance to acids, bases, and oils \n" +
                        "• Toughness, flexibility and relative transparency \n");
                break;
            case "PP":
                changeText.setText("Polypropylene (5 - PP)");
                img.setImageResource(R.drawable.pp_5);
                changeText2.setText("Clothing, bottles, tubs and ropes. Can be recycled into fibers. Number 5 plastics can be recycled through some curbside programs.\n" +
                        "\n" +
                        "Common Product Descriptions:\n" +
                        "•Excellent optical clarity\n" +
                        "• Low moisture vapor transmission \n" +
                        "• Inertness toward \n");
                break;
            case "PS":
                changeText.setText("Polystyrene (6 - PS)");
                img.setImageResource(R.drawable.ps_6);
                changeText2.setText("Cups, foam food trays, packing peanuts. Polystyrene is a real problem as it’s bulky yet very lightweight and that makes it difficult to recycle. For example, a carload of expanded polystyrene would weigh next to nothing so there’s not a lot of materials to reclaim. It can however be reused. Number 6 plastics can be recycled through some curbside programs.\n" +
                        "\n" +
                        "Common Product Descriptions:\n" +
                        "• Excellent moisture barrier for short shelf life products \n" +
                        "• Significant stiffness in both foamed and rigid forms. \n" +
                        "• Low density and high stiffness in foamed applications \n");
                break;
            case "OTHER":
                changeText.setText("Other (7)");
                img.setImageResource(R.drawable.mark);
                changeText2.setText("Could be a mixture of any and all of the above or plastics not readily recyclable such as polyurethane. Avoid it if you can and don’t recycle through curbside programs– recyclers generally speaking don’t want it. \n" +
                        "\n" +
                        "Common Product Descriptions:\n" +
                        "Dependent on resin or combination of resins\n");
                break;
            default:
                changeText.setText("...Loading...");
                img.setImageResource(R.drawable.mark);
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
                btn2.setClickable(false);
//              btn2.setBackgroundColor(Color.parseColor("#393939"));
                btn2.setBackgroundResource(R.drawable.rounded_button_grey);
                btn2.setTextColor(Color.parseColor("#80e27e"));

                // Write a message to the database
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("users").child("chad").child(code);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int stat = Integer.parseInt(dataSnapshot.getValue().toString());

                        myRef.setValue(stat + 1);

                        Context context = getApplicationContext();
                        CharSequence text = "Recycle Statistics Updated!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
