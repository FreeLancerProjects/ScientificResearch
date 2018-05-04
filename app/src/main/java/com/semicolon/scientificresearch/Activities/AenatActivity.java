package com.semicolon.scientificresearch.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.databinding.ActivityAenatBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class AenatActivity extends AppCompatActivity implements Events{
    private String user_type;
    private ActivityAenatBinding aenatBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aenatBinding = DataBindingUtil.setContentView(this,R.layout.activity_aenat);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        aenatBinding.setEvent(this);
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("user_type"))
            {
                user_type = intent.getStringExtra("user_type");
            }
        }
    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn1:
                Intent intent1 = new Intent(this,Mo3adlaActivity.class);
                intent1.putExtra("user_type",user_type);
                startActivity(intent1);
                break;
            case R.id.btn2:
                Intent intent2 = new Intent(this,OtherWebViewActivity.class);
                intent2.putExtra("url","https://www.surveysystem.com/sscalc.htm");
                startActivity(intent2);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
