package com.semicolon.scientificresearch.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.databinding.ActivityMo3adlaBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Mo3adlaActivity extends AppCompatActivity implements Events{

    private ActivityMo3adlaBinding mo3adlaBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mo3adlaBinding = DataBindingUtil.setContentView(this,R.layout.activity_mo3adla);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        mo3adlaBinding.setEvent(this);
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
