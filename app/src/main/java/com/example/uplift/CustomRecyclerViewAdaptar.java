package com.example.uplift;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomRecyclerViewAdaptar extends RecyclerView.Adapter<CustomRecyclerViewAdaptar.SimpleViewHolder> {
    ArrayList<ModelForPostListItem> postListItems=new ArrayList<>();
    ClickListner clickListner;
    Context context;

    public CustomRecyclerViewAdaptar(ArrayList<ModelForPostListItem> postListItems, ClickListner clickListner, Context context) {
        this.postListItems = postListItems;
        this.clickListner = clickListner;
        this.context = context;
    }

    interface ClickListner{
        public void onPostClick(String pId);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_design,parent,false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.name.setText(postListItems.get(position).getPostName());
//        Glide.with(context).load(postListItems.get(position).getImageURL()).into(holder.image);
        //Log.d("msg","image url="+postListItems.get(position).getImageURL());
//        Picasso.get().load("http://192.168.43.165/"+postListItems.get(position).getImageURL()).into(holder.image);
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
                    clickListner.onPostClick(postListItems.get(getAdapterPosition()).postId);
                }
            });

        }
    }
}

