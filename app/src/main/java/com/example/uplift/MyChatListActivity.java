package com.example.uplift;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MyChatListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomRecyclerViewAdaptar adapter;
    ArrayList<ModelForPostListItem> postListItems =new ArrayList<>();
    Context context;
    CustomRecyclerViewAdaptar.ClickListner clickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat_list);
        context=this;
        recyclerView=(RecyclerView)findViewById(R.id.post_history_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        clickListner=new CustomRecyclerViewAdaptar.ClickListner() {
            @Override
            public void onPostClick(String pId) {
                Intent intent=new Intent(context,MyChatActivity.class);
                startActivity(intent);
            }
        };
    }
}
