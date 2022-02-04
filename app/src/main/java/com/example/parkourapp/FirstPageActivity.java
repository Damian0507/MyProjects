package com.example.parkourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class FirstPageActivity extends AppCompatActivity {

    EditText nameLog;
    MaterialButton btnStart;
    SharedPreferences sharedPreferences;
    Animation animateTxt,animateBtn;

    private static final String SHARED_PREF_NAME= "mypref";
    private static final String KEY_NAME = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        nameLog = findViewById(R.id.nameTxt);
        btnStart = findViewById(R.id.startBtn);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        animateTxt= AnimationUtils.loadAnimation(this,R.anim.animate_texts);
        animateBtn = AnimationUtils.loadAnimation(this,R.anim.animate_btn);
        nameLog.setAnimation(animateTxt);
        btnStart.setAnimation(animateBtn);

        checkSharedPref();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME,nameLog.getText().toString());
                editor.apply();

                Intent i = new Intent(FirstPageActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
    public void checkSharedPref(){

        String name = sharedPreferences.getString(KEY_NAME,null);

        if(name!=null){

            Intent i = new Intent(FirstPageActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }


    }
}