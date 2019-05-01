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
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class MyPostActivity extends AppCompatActivity {

    Context context;
    TextView pName,desc,budget,moneyDonated, percent, msDesc, pageNo;
    ProgressBar progressBar;
    ImageView chat,prev,next;

    String projectId;
    String projectUserId;
    String currentUserId;
    ArrayList<String> chatIds = new ArrayList<>();
    ArrayList<String> chatNames = new ArrayList<>();

    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        Intent intent=getIntent();
        projectId=intent.getStringExtra("P_id");
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        context=this;
        pName=findViewById(R.id.my_pname_tv);
        budget=findViewById(R.id.my_tv_budget);
        moneyDonated=findViewById(R.id.my_tv_donated);
        percent=findViewById(R.id.my_tv_percent);
        msDesc=findViewById(R.id.my_tv_ms_desc);
        pageNo=findViewById(R.id.my_tv_page_no);
        prev=findViewById(R.id.my_prev_iv);
        next=findViewById(R.id.my_next_iv);
        progressBar = findViewById(R.id.my_pb);
        chat=findViewById(R.id.my_chat_iv);


        mDatabase.child("users").child(currentUserId).child("chat").child(projectId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    String key = data.getKey();
                    chatIds.add(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyPostActivity.this, MyChatListActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("chatIds",(Serializable)chatIds);
                intent1.putExtra("BUNDLE",args);
                intent1.putExtra("projectId", projectId);
                startActivity(intent1);
            }
        });
    }

}
