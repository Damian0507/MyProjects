package com.example.parkourapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;

import com.example.parkourapp.fragments.AllFlipsFragment;
import com.example.parkourapp.fragments.FavoritesFragment;
import com.example.parkourapp.fragments.VaultsFragment;
import com.example.parkourapp.fragments.aLLFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    
    Button vaultsBtn, flipsBtn, allBtn;
    BottomNavigationView navigationView;
    HorizontalScrollView horizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vaultsBtn = findViewById(R.id.vaultsBtn);
        flipsBtn = findViewById(R.id.flipsBtn);
        allBtn = findViewById(R.id.allBtn);
        navigationView = findViewById(R.id.bottomNavigationView);
        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        replaceFragment(new aLLFragment());
        allBtn.setTextColor(ColorStateList.valueOf(Color.BLACK));

       navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.Workouts:
                        fragment = new aLLFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout,fragment);
                        fragmentTransaction.commit();
                        flipsBtn.setTextColor(ColorStateList.valueOf(Color.GRAY));
                        allBtn.setTextColor(ColorStateList.valueOf(Color.BLACK));
                        vaultsBtn.setTextColor(ColorStateList.valueOf(Color.GRAY));
                        horizontalScrollView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.Favorites:
                        horizontalScrollView.setVisibility(View.GONE);
                        fragment = new FavoritesFragment();
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.frameLayout,fragment);
                        fragmentTransaction1.commit();
                        break;

                }
                return true;
            }
        });
        
        vaultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                replaceFragment(new VaultsFragment());
                vaultsBtn.setTextColor(ColorStateList.valueOf(Color.BLACK));
                flipsBtn.setTextColor(ColorStateList.valueOf(Color.GRAY));
                allBtn.setTextColor(ColorStateList.valueOf(Color.GRAY));
                
            }
        });
        flipsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AllFlipsFragment());
                flipsBtn.setTextColor(ColorStateList.valueOf(Color.BLACK));
                allBtn.setTextColor(ColorStateList.valueOf(Color.GRAY));
                vaultsBtn.setTextColor(ColorStateList.valueOf(Color.GRAY));
            }
        });

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new aLLFragment());
                flipsBtn.setTextColor(ColorStateList.valueOf(Color.GRAY));
                allBtn.setTextColor(ColorStateList.valueOf(Color.BLACK));
                vaultsBtn.setTextColor(ColorStateList.valueOf(Color.GRAY));

            }
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();




    }
}