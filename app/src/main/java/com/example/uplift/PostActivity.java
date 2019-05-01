package com.example.uplift;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    Context context;
    TextView pName,desc,budget,moneyDonated, percent, msDesc, pageNo;
    ProgressBar progressBar;
    ImageView chat,prev,next;
    Button donateActivity;

    ArrayList<ModelForMilestone> milestones = new ArrayList<>();
    FirebaseAuth firebaseAuth;

    String projectId;
    String projectUserId;
    String currentUserId;
    ArrayList<String> chatNames = new ArrayList<>();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("projects");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        context=this;
        pName=findViewById(R.id.pname_tv);
        desc=findViewById(R.id.desc_tv);
        budget=findViewById(R.id.tv_budget);
        moneyDonated=findViewById(R.id.tv_donated);
        percent=findViewById(R.id.tv_percent);
        msDesc=findViewById(R.id.tv_ms_desc);
        pageNo=findViewById(R.id.tv_page_no);
        prev=findViewById(R.id.prev_iv);
        next=findViewById(R.id.next_iv);
        donateActivity = findViewById(R.id.btn_donate_activity);

        progressBar = findViewById(R.id.pb);

        chat=findViewById(R.id.chat_iv);

        final Intent intent=getIntent();
        projectId =intent.getStringExtra("P_id");
        Log.d("urmi", "post pid: "+projectId);

//        prev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Character.getNumericValue(pageNo.getText().toString().charAt(0))>0){
//                    percent.setText(milestones.get(Character.getNumericValue(pageNo.getText().toString().charAt(0))).getPercent());
//                    msDesc.setText(milestones.get(Character.getNumericValue(pageNo.getText().toString().charAt(0))).getDescription());
//                    pageNo.setText(Character.getNumericValue(pageNo.getText().toString().charAt(0))-1+" of 3");
//                    progressBar.setProgress(Integer.parseInt(milestones.get(Character.getNumericValue(pageNo.getText().toString().charAt(0))).getPercent()));
//                }
//            }
//        });
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Character.getNumericValue(pageNo.getText().toString().charAt(0))<3){
//                    percent.setText(milestones.get(Character.getNumericValue(pageNo.getText().toString().charAt(0))-1).getPercent());
//                    msDesc.setText(milestones.get(Character.getNumericValue(pageNo.getText().toString().charAt(0))-1).getDescription());
//                    pageNo.setText(Character.getNumericValue(pageNo.getText().toString().charAt(0))+1+" of 3");
//                    progressBar.setProgress(Integer.parseInt(milestones.get(Character.getNumericValue(pageNo.getText().toString().charAt(0))-1).getPercent()));
//                }
//            }
//        });


        donateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = firebaseAuth.getCurrentUser().getUid();
                Intent intent1 = new Intent(PostActivity.this, DonateActivity.class);
                intent1.putExtra("projectId", projectId);
                intent1.putExtra("cid", cid);
                startActivity(intent1);
            }
        });


        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(PostActivity.this, ChatActivity.class);
                intent2.putExtra("projectUid", projectUserId);
                intent2.putExtra("projectId", projectId);
                startActivity(intent2);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = databaseReference.orderByChild("pid").equalTo(projectId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {



                    pName.setText("Name: "+data.child("name").getValue(String.class));
                    desc.setText("Description:\n"+data.child("description").getValue(String.class));
                    budget.setText("Budget: "+data.child("budget").getValue(String.class));
                    moneyDonated.setText("Amount Collected: "+data.child("donatedAmount").getValue(String.class));
                    String ms1_percent1 = data.child("milestone").child("0").child("percent").getValue(String.class);
                    String ms1_description1 = data.child("milestone").child("0").child("description").getValue(String.class);
                    String ms1_percent2 = data.child("milestone").child("1").child("percent").getValue(String.class);
                    String ms1_description2 = data.child("milestone").child("1").child("description").getValue(String.class);
                    String ms1_percent3 = data.child("milestone").child("2").child("percent").getValue(String.class);
                    String ms1_description3 = data.child("milestone").child("2").child("description").getValue(String.class);
                    projectUserId = data.child("uid").getValue(String.class);
                    pageNo.setText(1+" of 3");
                    ModelForMilestone modelForMilestone1 = new ModelForMilestone(ms1_percent1, ms1_description1);
                    ModelForMilestone modelForMilestone2 = new ModelForMilestone(ms1_percent2, ms1_description2);
                    ModelForMilestone modelForMilestone3 = new ModelForMilestone(ms1_percent3, ms1_description3);
                    milestones.add(modelForMilestone1);
                    milestones.add(modelForMilestone2);
                    milestones.add(modelForMilestone3);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
