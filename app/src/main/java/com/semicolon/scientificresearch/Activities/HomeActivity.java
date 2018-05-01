package com.semicolon.scientificresearch.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.Models.UserModel;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.SingleTone.UserSingleTone;
import com.semicolon.scientificresearch.databinding.ActivityHomeBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class HomeActivity extends AppCompatActivity implements Events,UserSingleTone.UserDataInterface{
    ActivityHomeBinding homeBinding;
    UserSingleTone userSingleTone;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        homeBinding.setEvent(this);
        userSingleTone = UserSingleTone.getInstance();
        userSingleTone.GetUserData(this);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_aenat:
                Intent intent1 = new Intent(this,AenatActivity.class);
                startActivity(intent1);

                break;
            case R.id.btn_db:
                Intent intent2 = new Intent(this,DatabaseActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_tawf:
                Intent intent3 = new Intent(this,OtherWebViewActivity.class);
                intent3.putExtra("url","http://www.bibme.org/items/new");
                startActivity(intent3);
                break;
            case R.id.btn_t7:
                Intent intent4 = new Intent(this,Ta7lelActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_tad:
                Intent intent5 = new Intent(this,OtherWebViewActivity.class);
                intent5.putExtra("url","https://www.bibme.org/grammar-and-plagiarism/?=bmp_BM.A.300-250&intcid=wt.BibMe.BM.A.300-250&inhousead=BM.A.300-250");
                startActivity(intent5);
                break;
            case R.id.btn_control:
                Intent intent6 = new Intent(this,ControlActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_train:
                Intent intent7 = new Intent(this,TrainingActivity.class);
                startActivity(intent7);
                break;
            case R.id.btn_trans:
                Intent intent8 = new Intent(this,TranslateActivity.class);
                startActivity(intent8);
                break;
            case R.id.btn_eqtbas:
                Intent intent9 = new Intent(this,OtherWebViewActivity.class);
                intent9.putExtra("url","https://www.bibme.org/grammar-and-plagiarism/?=bmp_BM.C.300-250&intcid=wt.BibMe.BM.C.300-250&inhousead=BM.C.300-250");
                startActivity(intent9);
                break;

        }
    }

    @Override
    public void getUserData(UserModel userModel) {
        this.userModel=userModel;
    }
}
