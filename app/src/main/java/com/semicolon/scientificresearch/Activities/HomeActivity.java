package com.semicolon.scientificresearch.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.Models.UserModel;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.Services.Preferences;
import com.semicolon.scientificresearch.Services.Tags;
import com.semicolon.scientificresearch.SingleTone.UserSingleTone;
import com.semicolon.scientificresearch.databinding.ActivityHomeBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class HomeActivity extends AppCompatActivity implements Events,UserSingleTone.UserDataInterface{
    private ActivityHomeBinding homeBinding;
    private UserSingleTone userSingleTone;
    private UserModel userModel;
    private AlertDialog alertDialog;
    private Preferences preferences;
    private String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        homeBinding.setEvent(this);
        setSupportActionBar(homeBinding.toolBar);
        preferences = new Preferences(this);
        userSingleTone = UserSingleTone.getInstance();
        userSingleTone.GetUserData(this);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        Log.e("home","Home");
        CreateLogOutAlert();
        getDatafromIntent();
    }

    private void getDatafromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("user_type"))
            {
                user_type = intent.getStringExtra("user_type");
            }
        }
    }

    private void CreateLogOutAlert() {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.Logout_)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LogOut();
                        alertDialog.dismiss();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                }).create();

    }

    private void LogOut() {
        preferences.clearPref();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_aenat:
                Intent intent1 = new Intent(this,AenatActivity.class);
                intent1.putExtra("user_type",user_type);
                startActivity(intent1);

                break;
            case R.id.btn_db:
                Intent intent2 = new Intent(this,DatabaseActivity.class);
                intent2.putExtra("user_type",user_type);
                startActivity(intent2);
                break;
            case R.id.btn_tawf:
                Intent intent3 = new Intent(this,OtherWebViewActivity.class);
                intent3.putExtra("url","http://www.bibme.org/items/new");
                startActivity(intent3);
                break;
            case R.id.btn_t7:
                Intent intent4 = new Intent(this,Ta7lelActivity.class);
                intent4.putExtra("user_type",user_type);

                startActivity(intent4);
                break;
            case R.id.btn_tad:
                Intent intent5 = new Intent(this,OtherWebViewActivity.class);
                intent5.putExtra("url","https://www.bibme.org/grammar-and-plagiarism/?=bmp_BM.A.300-250&intcid=wt.BibMe.BM.A.300-250&inhousead=BM.A.300-250");
                startActivity(intent5);
                break;
            case R.id.btn_control:
                Intent intent6 = new Intent(this,ControlActivity.class);
                intent6.putExtra("user_type",user_type);

                startActivity(intent6);
                break;
            case R.id.btn_train:
                Intent intent7 = new Intent(this,TrainingActivity.class);
                intent7.putExtra("user_type",user_type);
                startActivity(intent7);
                break;
            case R.id.btn_trans:
                Intent intent8 = new Intent(this,TranslateActivity.class);
                intent8.putExtra("user_type",user_type);
                startActivity(intent8);
                break;
            case R.id.btn_eqtbas:
                Intent intent9 = new Intent(this,EqtbasActivity.class);
                intent9.putExtra("user_type",user_type);
                startActivity(intent9);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.logout:
                if (user_type!=null && user_type.equals(Tags.visitor))
                {
                    finish();
                }else
                    {
                        alertDialog.show();

                    }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getUserData(UserModel userModel) {
        this.userModel=userModel;
    }
}
