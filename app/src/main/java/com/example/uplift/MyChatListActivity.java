package com.example.uplift;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyChatListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ChatRecyclerViewAdapter adapter;
    ArrayList<ModelForPostListItem> postListItems =new ArrayList<>();
    Context context;
    ChatRecyclerViewAdapter.ClickListner clickListner;

    String projectId;
    String currentUserId;
    ArrayList<String> chatIds;

    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat_list);

        Intent intent = getIntent();
        projectId = intent.getStringExtra("projectId");
        Bundle args = intent.getBundleExtra("BUNDLE");
        chatIds = (ArrayList<String>) args.getSerializable("chatIds");

        currentUserId = firebaseAuth.getCurrentUser().getUid();

        context=this;
        recyclerView=(RecyclerView)findViewById(R.id.chat_name_list_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        clickListner = new ChatRecyclerViewAdapter.ClickListner() {
            @Override
            public void onPostClick(String uId, String name) {
                Intent intent1=new Intent(context,MyChatActivity.class);
                intent1.putExtra("uid", uId);
                intent1.putExtra("name", name);
                intent1.putExtra("projectId", projectId);
                startActivity(intent1);
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        postListItems.clear();

        int i;
        for(i = 0; i< chatIds.size(); i++) {
            final int finalI = i;
            mDatabase.child("users").child(chatIds.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    ModelForPostListItem modelForPostListItem = new ModelForPostListItem();
                    modelForPostListItem.setPostId(chatIds.get(finalI));
                    modelForPostListItem.setPostName(name);
                    postListItems.add(modelForPostListItem);
                    Log.d("urmi", "uid: "+chatIds.get(finalI));
                    Log.d("urmi", "name: "+name);
                    adapter = new ChatRecyclerViewAdapter(postListItems,clickListner,context);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
