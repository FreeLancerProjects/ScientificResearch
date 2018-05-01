package com.semicolon.scientificresearch.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.databinding.ActivityEfadaOrderBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class EfadaOrderActivity extends AppCompatActivity implements Events{

    private ActivityEfadaOrderBinding efadaOrderBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        efadaOrderBinding = DataBindingUtil.setContentView(this,R.layout.activity_efada_order);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        efadaOrderBinding.setEvent(this);
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
