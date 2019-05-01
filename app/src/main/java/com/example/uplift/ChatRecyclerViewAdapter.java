package com.example.uplift;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.SimpleViewHolder> {

    ArrayList<ModelForPostListItem> postListItems=new ArrayList<>();
    ChatRecyclerViewAdapter.ClickListner clickListner;
    Context context;

    public ChatRecyclerViewAdapter(ArrayList<ModelForPostListItem> postListItems, ChatRecyclerViewAdapter.ClickListner clickListner, Context context) {
        this.postListItems = postListItems;
        this.clickListner = clickListner;
        this.context = context;
    }


    interface ClickListner{
        public void onPostClick(String uId, String name);
    }


    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_design,parent,false);
        return new ChatRecyclerViewAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder simpleViewHolder, int i) {
        simpleViewHolder.name.setText(postListItems.get(i).getPostName());
    }


    @Override
    public int getItemCount() {
        return postListItems.size();
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        //        ImageView image;
        LinearLayout postLayout;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.p_name);
//            image=itemView.findViewById(R.id.image);
            postLayout=itemView.findViewById(R.id.post_layout);

            postLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListner.onPostClick(postListItems.get(getAdapterPosition()).getPostId(), postListItems.get(getAdapterPosition()).getPostName());
                }
            });

        }
    }

}
