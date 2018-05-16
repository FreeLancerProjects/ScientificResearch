package com.semicolon.scientificresearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.semicolon.scientificresearch.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class SplashActivity extends AppCompatActivity {

    private ShimmerTextView shimmerTextView;
    private LinearLayout logo_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        logo_container = findViewById(R.id.logo_container);

        final Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logo_container.startAnimation(animation1);

            }
        },1000);
        shimmerTextView = findViewById(R.id.shimmer_tv);
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(300);
        shimmer.setStartDelay(300);
        shimmer.setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer.start(shimmerTextView);



        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });








    }

}
