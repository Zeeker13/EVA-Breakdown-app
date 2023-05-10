package com.example.ppaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN=2500;


    //variables
    Animation topAnim,bottomAnim;
    ImageView image;
    ImageView image2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        topAnim= AnimationUtils.loadAnimation( this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation( this,R.anim.bot_animation);

        //hooks
        image=findViewById(R.id.image2);
        image2=findViewById(R.id.my_image);

        image.setAnimation(topAnim);
        image2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
            Intent intent = new Intent(MainActivity.this,Dashboard.class);
            startActivity(intent);
            finish();
        }
        },SPLASH_SCREEN);

    }
}