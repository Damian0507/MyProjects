package com.example.parkourapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkourapp.R;
import com.example.parkourapp.adapters.FavoritesAdapter;
import com.example.parkourapp.models.VideoItemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FavoritesFragment extends Fragment {

    View view;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase rootNode;
    DatabaseReference ref;
    FavoritesAdapter adapter;

    ArrayList<VideoItemModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorites, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewFav);
        adapter = new FavoritesAdapter(getContext(),list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        InitializeData();
    }

    private void InitializeData() {
        SharedPreferences sharedPreferences;

        String SHARED_PREF_NAME= "mypref";
        String KEY_NAME = "name";

        sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_NAME,null);

        ref = FirebaseDatabase.getInstance().getReference(username);
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                        VideoItemModel vm = new VideoItemModel();
                        vm.setTitle(dataSnapshot.child("title").getValue().toString());
                        vm.setImage(dataSnapshot.child("image").getValue().toString());
                        vm.setVideoId(dataSnapshot.child("videoId").getValue().toString());
                        vm.setDescription(dataSnapshot.child("description").getValue().toString());
                        list.add(vm);
                    }
                    adapter.notifyDataSetChanged();
                }


            }
        });


    }
}