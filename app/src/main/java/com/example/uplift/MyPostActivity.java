package com.example.uplift;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyPostActivity extends AppCompatActivity {

    Context context;
    TextView pName,desc,budget,moneyDonated, percent, msDesc, pageNo;
    ProgressBar progressBar;
    ImageView chat,prev,next;
    Button donateActivity;
    String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        context=this;
        pName=findViewById(R.id.my_pname_tv);
        budget=findViewById(R.id.my_tv_budget);
        moneyDonated=findViewById(R.id.my_tv_donated);
        percent=findViewById(R.id.my_tv_percent);
        msDesc=findViewById(R.id.my_tv_ms_desc);
        pageNo=findViewById(R.id.my_tv_page_no);
        prev=findViewById(R.id.my_prev_iv);
        next=findViewById(R.id.my_next_iv);
        donateActivity = findViewById(R.id.my_btn_donate_activity);
        progressBar = findViewById(R.id.my_pb);
        chat=findViewById(R.id.my_chat_iv);

        Intent intent=getIntent();
        pid=intent.getStringExtra("P_id");

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyPostActivity.this, MyChatListActivity.class);
                startActivity(intent1);
            }
        });
    }
}
