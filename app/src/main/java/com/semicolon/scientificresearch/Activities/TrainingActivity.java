package com.semicolon.scientificresearch.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.databinding.ActivityTrainingBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class TrainingActivity extends AppCompatActivity implements Events{

    private ActivityTrainingBinding trainingBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trainingBinding = DataBindingUtil.setContentView(this,R.layout.activity_training);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        trainingBinding.setEvent(this);
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
