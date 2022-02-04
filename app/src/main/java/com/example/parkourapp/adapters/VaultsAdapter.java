package com.example.parkourapp.adapters;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parkourapp.R;
import com.example.parkourapp.VideoPlayerActivity;
import com.example.parkourapp.models.VideoItemModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VaultsAdapter extends RecyclerView.Adapter<VaultsAdapter.MyViewHolder> {
    Context context;
    ArrayList<VideoItemModel> list;
    private static final String TAG = "MyFragment";

    public VaultsAdapter(Context context, ArrayList<VideoItemModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_design,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final  VideoItemModel model = list.get(position);

        holder.titleTxt.setText(model.getTitle());
        holder.descText.setText(model.getDescription());
        Picasso.get().load(model.getImage()).into(holder.thumnail);
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseDatabase rootNode;
                DatabaseReference ref;

                String title, videoid, thumbnail, description;
                String username;

                SharedPreferences sharedPreferences;

                 String SHARED_PREF_NAME= "mypref";
                 String KEY_NAME = "name";

                 sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
                 username = sharedPreferences.getString(KEY_NAME,null);

                title = model.getTitle();
                videoid = model.getVideoId();
                thumbnail = model.getImage();
                description = model.getDescription();

                VideoItemModel vm = new VideoItemModel(title,description,videoid,thumbnail);

                rootNode = FirebaseDatabase.getInstance();
                ref = rootNode.getReference().child(username);
                ref.child(title).setValue(vm);

                Toast.makeText(context, "Added succesfully!", Toast.LENGTH_SHORT).show();
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
        public MaterialButton favBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumnail = itemView.findViewById(R.id.thumbanil);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            descText = itemView.findViewById(R.id.descTxt);
            favBtn = itemView.findViewById(R.id.favBtn);






        }
    }
}
