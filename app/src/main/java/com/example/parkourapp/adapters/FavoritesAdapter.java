package com.example.parkourapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkourapp.R;
import com.example.parkourapp.VideoPlayerActivity;
import com.example.parkourapp.models.VideoItemModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {

    Context context;
    ArrayList<VideoItemModel> list;

    public FavoritesAdapter(Context context, ArrayList<VideoItemModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_fav,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final  VideoItemModel model = list.get(position);
        holder.titleTxt.setText(model.getTitle());
        holder.descText.setText(model.getTitle());
        Picasso.get().load(model.getImage()).into(holder.thumnail);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title;
                title = model.getTitle();
                SharedPreferences sharedPreferences;

                String SHARED_PREF_NAME= "mypref";
                String KEY_NAME = "name";

                sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String username = sharedPreferences.getString(KEY_NAME,null);

                FirebaseDatabase.getInstance().getReference(username).child(title).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        list.remove(position);
                        notifyDataSetChanged();


                    }
                });
            }
        });
        holder.thumnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, VideoPlayerActivity.class);
                i.putExtra("videoid",model.getVideoId());
                context.startActivity(i);

            }
        });
        holder.titleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, VideoPlayerActivity.class);
                i.putExtra("videoid",model.getVideoId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public RoundedImageView thumnail;
        public MaterialTextView titleTxt;
        public MaterialTextView descText;
        public MaterialButton deleteBtn;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumnail = itemView.findViewById(R.id.thumbanilFav);
            titleTxt = itemView.findViewById(R.id.titleTxtFav);
            descText = itemView.findViewById(R.id.descTxtFav);
            deleteBtn = itemView.findViewById(R.id.delBtn);

        }
    }
}
