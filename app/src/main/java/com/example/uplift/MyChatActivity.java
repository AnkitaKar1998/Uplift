package com.example.uplift;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyChatActivity extends AppCompatActivity {

    Button sendButton;
    EditText message;
    LinearLayout chatSection;


    String currentUserId;
    String askerName;
    String askerId;
    String projectId;

    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat);

        Intent intent = getIntent();
        askerId = intent.getStringExtra("uid");
        askerName = intent.getStringExtra("name");
        projectId = intent.getStringExtra("projectId");

        currentUserId = firebaseAuth.getCurrentUser().getUid();

        sendButton = findViewById(R.id.send_button);
        message = findViewById(R.id.et_message);
        chatSection = findViewById(R.id.ll_teacher_chat_section);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = message.getText().toString();
                message.getText().clear();
                TextView textView = new TextView(MyChatActivity.this);
                textView.setText(msg);
                setDesign(textView);
                chatSection.addView(textView);

                String msgId=mDatabase.push().getKey();
                mDatabase.child("users").child(currentUserId).child("chat").child(projectId).child(askerId).child(msgId).child("id").setValue(currentUserId);
                mDatabase.child("users").child(currentUserId).child("chat").child(projectId).child(askerId).child(msgId).child("msg").setValue(msg);
                final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollView));
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.child("users").child(currentUserId).child("chat").child(projectId).child(askerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatSection.removeAllViews();
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    String msg = data.child("msg").getValue(String.class);
                    TextView textView = new TextView(MyChatActivity.this);
                    textView.setText(msg);
                    setDesign(textView);
                    chatSection.addView(textView);
                    final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollView));
                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setDesign(TextView textView) {
        LinearLayout.LayoutParams txtlayParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtlayParams.gravity = Gravity.CENTER;
        txtlayParams.setMargins(50,20,50,20);
        textView.setLayoutParams(txtlayParams);
        textView.setPadding(20,20,20,20);
        textView.setTextSize(20);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setBackgroundColor(Color.parseColor("#FFCDDEFF"));
    }
}
