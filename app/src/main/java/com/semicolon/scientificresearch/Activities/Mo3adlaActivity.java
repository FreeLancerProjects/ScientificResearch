package com.semicolon.scientificresearch.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.Services.Tags;
import com.semicolon.scientificresearch.databinding.ActivityMo3adlaBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Mo3adlaActivity extends AppCompatActivity implements Events{

    private ActivityMo3adlaBinding mo3adlaBinding;
    private String user_type;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mo3adlaBinding = DataBindingUtil.setContentView(this,R.layout.activity_mo3adla);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        mo3adlaBinding.setEvent(this);
        getDataFromIntent();
        CreateAlertDialog();
    }

    private void CreateAlertDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage("هذه الخدمة غير متاحة للزائرين عليك بإنشاء حساب وتسجيل الدخول")
                .setPositiveButton("إغلاق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                        finish();
                    }
                }).setCancelable(false).create();
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
            case R.id.result_btn:
                if (user_type.equals(Tags.visitor))
                {
                    alertDialog.show();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
