package com.semicolon.scientificresearch.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.semicolon.scientificresearch.Adapters.TrainningAdapter;
import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.Models.TrainingModel;
import com.semicolon.scientificresearch.Models.UserModel;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.Services.Api;
import com.semicolon.scientificresearch.Services.Services;
import com.semicolon.scientificresearch.Services.Tags;
import com.semicolon.scientificresearch.SingleTone.UserSingleTone;
import com.semicolon.scientificresearch.databinding.ActivityTrainingBinding;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrainingActivity extends AppCompatActivity implements Events,UserSingleTone.UserDataInterface{

    private ActivityTrainingBinding trainingBinding;
    private List<TrainingModel> trainingModelList;
    private TrainningAdapter adapter;
    private UserModel userModel;
    private ProgressDialog dialog;
    private String user_type="";
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trainingBinding = DataBindingUtil.setContentView(this,R.layout.activity_training);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        trainingBinding.setEvent(this);
        UserSingleTone singleTone =UserSingleTone.getInstance();
        singleTone.GetUserData(this);
        CreateProgDialog();
        CreateAlertDialog();
        getDataFromIntent();
        trainingModelList = new ArrayList<>();
        adapter = new TrainningAdapter(this,trainingModelList);
        trainingBinding.recView.setLayoutManager(new LinearLayoutManager(this));
        trainingBinding.recView.setHasFixedSize(true);
        trainingBinding.recView.setAdapter(adapter);
        trainingBinding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.ko7ly), PorterDuff.Mode.SRC_IN);
    }
    private void CreateProgDialog()
    {
        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.ko7ly), PorterDuff.Mode.SRC_IN);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.book_course));
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

    }
    private void CreateAlertDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.ser_not_av))
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
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
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user_type.equals(Tags.visitor))
        {
            getData("all");

        }else if (user_type.equals(Tags.user_app))
        {
            getData(userModel.getUser_id());

        }
    }

    private void getData(String user_id) {
        Retrofit retrofit = Api.getRetrofit();
        Services services = retrofit.create(Services.class);
        Call<List<TrainingModel>> call = services.TrainingData(user_id);
        call.enqueue(new Callback<List<TrainingModel>>() {
            @Override
            public void onResponse(Call<List<TrainingModel>> call, Response<List<TrainingModel>> response) {
                if (response.isSuccessful())
                {
                    trainingModelList.clear();
                    trainingModelList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    trainingBinding.progBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<TrainingModel>> call, Throwable t) {
                trainingBinding.progBar.setVisibility(View.GONE);

                Toast.makeText(TrainingActivity.this, R.string.netconn, Toast.LENGTH_SHORT).show();
                Log.e("Error",t.getMessage());
            }
        });


    }

    @Override
    public void getUserData(UserModel userModel) {
        this.userModel = userModel;
    }
    
    public void SetPos(int pos)
    {
        TrainingModel trainingModel = trainingModelList.get(pos);

        Intent intent = new Intent(TrainingActivity.this,CourseDetailsActivity.class);
        intent.putExtra("details",trainingModel);
        intent.putExtra("user_type",user_type);
        startActivity(intent);

    }


}
