package com.semicolon.scientificresearch.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.databinding.ActivityDatabaseBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class DatabaseActivity extends AppCompatActivity implements Events{

    private ActivityDatabaseBinding databaseBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseBinding = DataBindingUtil.setContentView(this,R.layout.activity_database);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        databaseBinding.setEvent(this);
    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.prev:
                Intent intent1 = new Intent(this,OtherWebViewActivity.class);
                intent1.putExtra("url","http://ecat.kfnl.gov.sa:88/hipmain/");

                startActivity(intent1);
                break;
            case R.id.order:
                Intent intent2 = new Intent(this,OtherWebViewActivity.class);
                intent2.putExtra("url","https://sdl.edu.sa/SDLPortal/Publishers.aspx");

                startActivity(intent2);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
