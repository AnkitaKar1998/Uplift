package com.example.uplift;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    Button sendButton;
    EditText message;
    LinearLayout chatSection;
    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String currentUserId;
    String projectUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        projectUserId = ((Intent) intent).getStringExtra("projectUid");

        currentUserId = firebaseAuth.getCurrentUser().getUid();

        sendButton = findViewById(R.id.send_button);
        message = findViewById(R.id.et_message);
        chatSection = findViewById(R.id.ll_teacher_chat_section);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = message.getText().toString();
                message.getText().clear();
                TextView textView = new TextView(ChatActivity.this);
                textView.setText(msg);
                setDesign(textView);
                chatSection.addView(textView);

                if((currentUserId.equals("uJNEsWqIvmSlTXLLYtGksss6oHw1") && projectUserId.equals("zfo09k5uzNeoQyz1PYsNydCKH5b2"))
                        || (currentUserId.equals("zfo09k5uzNeoQyz1PYsNydCKH5b2") && projectUserId.equals("uJNEsWqIvmSlTXLLYtGksss6oHw1"))) {
                    String id=mDatabase.push().getKey();
                    mDatabase.child("chat").child("111").child(id).child("id").setValue(currentUserId);
                    mDatabase.child("chat").child("111").child(id).child("msg").setValue(msg);
                }



//                String id=mDatabase.push().getKey();
//                mDatabase.child("chat").child(projectUserId).child(id).setValue(msg);
//                mDatabase.child("users").child(projectUserId).child("chat").child(currentUserId).child(id).setValue(msg);
//                mDatabase.child("groups").child(Gname).child(currentChatID).setValue(modelForMessage);
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

    @Override
    protected void onStart() {
        super.onStart();

        if((currentUserId.equals("uJNEsWqIvmSlTXLLYtGksss6oHw1") && projectUserId.equals("zfo09k5uzNeoQyz1PYsNydCKH5b2"))
                || (currentUserId.equals("zfo09k5uzNeoQyz1PYsNydCKH5b2") && projectUserId.equals("uJNEsWqIvmSlTXLLYtGksss6oHw1"))) {
            mDatabase.child("chat").child("111").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    chatSection.removeAllViews();
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        String msg=data.child("msg").getValue(String.class);
                        TextView textView = new TextView(ChatActivity.this);
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
    }
}

