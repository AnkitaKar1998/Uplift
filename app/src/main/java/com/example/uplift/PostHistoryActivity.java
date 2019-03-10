package com.example.uplift;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class PostHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomRecyclerViewAdaptar adapter;
    ArrayList<ModelForPostListItem> postListItems =new ArrayList<>();
    Context context;
    CustomRecyclerViewAdaptar.ClickListner clickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_history);

        recyclerView=(RecyclerView)findViewById(R.id.post_history_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PostHistoryActivity.this));

        clickListner=new CustomRecyclerViewAdaptar.ClickListner() {
            @Override
            public void onPostClick(String pId) {
                Intent intent=new Intent(context,MyPostActivity.class);
                intent.putExtra("P_id",pId);
                startActivity(intent);
            }
        };
    }
}
