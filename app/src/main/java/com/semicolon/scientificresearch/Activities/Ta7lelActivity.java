package com.semicolon.scientificresearch.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.databinding.ActivityTa7lelBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Ta7lelActivity extends AppCompatActivity implements Events{

    private ActivityTa7lelBinding ta7lelBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ta7lelBinding = DataBindingUtil.setContentView(this,R.layout.activity_ta7lel);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        ta7lelBinding.setEvent(this);
    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.back:
                finish();
                break;
        }
    }
}
