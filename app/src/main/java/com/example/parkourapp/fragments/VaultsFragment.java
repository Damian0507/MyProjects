package com.example.parkourapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkourapp.R;
import com.example.parkourapp.adapters.VaultsAdapter;
import com.example.parkourapp.models.VideoItemModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VaultsFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase rootNode;
    DatabaseReference ref;
    RequestQueue mQueue;


    ArrayList<VideoItemModel> list = new ArrayList<>();
    private static final String TAG = "MyActivity";


    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vaults, container, false);




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mQueue = Volley.newRequestQueue(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewVaults);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        fetchdata();

    }

    private void fetchdata(){



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://youtube.googleapis.com/youtube/v3/playlistItems?part=contentDetails%2C%20id%2C%20snippet%2C%20status&maxResults=999&playlistId=PLKS0_ubzc8t6-F3CRtf9D57g5bD1qxMjD&key=AIzaSyCiZCoPyKxm5DTmTCyEM_8mm_JDPtyqE98",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getJSONObject("snippet").getString("title");
                        String videoID = jsonObject.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                        String thumbnail = jsonObject.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                        String description = jsonObject.getJSONObject("snippet").getString("description");


                        VideoItemModel vm = new VideoItemModel(title,description,videoID,thumbnail);
                        list.add(vm);

                        rootNode = FirebaseDatabase.getInstance();
                        ref = rootNode.getReference().child("Vaults");
                        ref.child(title).setValue(vm);


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                VaultsAdapter adapter = new VaultsAdapter(getContext(),list);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }

}