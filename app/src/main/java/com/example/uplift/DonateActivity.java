package com.example.uplift;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class DonateActivity extends AppCompatActivity {

    Button donate;
    TextInputLayout amount;
    String inputAmount;

    String pid, cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        Intent i = getIntent();
//        projectId = i.getStringExtra("projectId");
          cid = i.getStringExtra("cid");

        Random random = new Random();
        int ran = random.nextInt(1000000000)+10000;
        pid = String.valueOf(ran);

        amount = findViewById(R.id.til_amount);
        donate = findViewById(R.id.btn_donate);

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputAmount = amount.getEditText().getText().toString();

                Intent intent = new Intent(DonateActivity.this, checksum.class);
                intent.putExtra("projectId", pid);
                intent.putExtra("custid", cid);
                intent.putExtra("amount", inputAmount);
                startActivity(intent);
            }
        });
    }
}