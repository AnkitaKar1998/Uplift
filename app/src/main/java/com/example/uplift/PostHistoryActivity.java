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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomRecyclerViewAdaptar adapter;
    ArrayList<ModelForPostListItem> postListItems =new ArrayList<>();
    Context context;
    CustomRecyclerViewAdaptar.ClickListner clickListner;

    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_history);

        context = PostHistoryActivity.this;
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        recyclerView=(RecyclerView)findViewById(R.id.post_history_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PostHistoryActivity.this));

        clickListner=new CustomRecyclerViewAdaptar.ClickListner() {
            @Override
            public void onPostClick(String pId, String name) {
                Intent intent=new Intent(context,MyPostActivity.class);
                intent.putExtra("P_id",pId);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        postListItems.clear();

        Query query = mDatabase.child("projects").orderByChild("uid").equalTo(currentUserId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    String projectId = data.child("pid").getValue(String.class);
                    String projectName = data.child("name").getValue(String.class);
                    ModelForPostListItem modelForPostListItem = new ModelForPostListItem();
                    modelForPostListItem.setPostId(projectId);
                    modelForPostListItem.setPostName(projectName);
                    postListItems.add(modelForPostListItem);
                }
                adapter = new CustomRecyclerViewAdaptar(postListItems, clickListner ,context);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
