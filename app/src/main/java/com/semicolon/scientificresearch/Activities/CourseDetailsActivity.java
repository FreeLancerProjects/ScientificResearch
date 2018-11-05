package com.semicolon.scientificresearch.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.semicolon.scientificresearch.Adapters.TrainingVideoAdapter;
import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.Models.ResponseModel;
import com.semicolon.scientificresearch.Models.TrainingModel;
import com.semicolon.scientificresearch.Models.UserModel;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.Services.Api;
import com.semicolon.scientificresearch.Services.Services;
import com.semicolon.scientificresearch.Services.Tags;
import com.semicolon.scientificresearch.SingleTone.UserSingleTone;
import com.semicolon.scientificresearch.databinding.ActivityCourseDetailsBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CourseDetailsActivity extends AppCompatActivity implements Events,UserSingleTone.UserDataInterface{

    private ActivityCourseDetailsBinding detailsBinding;
    private TrainingModel trainingModel;
    private ProgressDialog dialog;
    private UserModel userModel;
    private UserSingleTone singleTone;
    private String user_type="";
    private AlertDialog alertDialog;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_course_details);
        /*Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);*/
        detailsBinding.setEvent(this);
        singleTone = UserSingleTone.getInstance();
        singleTone.GetUserData(this);

        getDataFromIntent();
        CreateProgDialog();
        CreateAlertDialog();

        manager = new LinearLayoutManager(this);
        detailsBinding.recView.setLayoutManager(manager);
        detailsBinding.recView.setNestedScrollingEnabled(true);
    }

    private void CreateAlertDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.ser_not_av))
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                }).setCancelable(false).create();
    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            trainingModel = (TrainingModel) intent.getSerializableExtra("details");
            user_type = intent.getStringExtra("user_type");
            UpdateUI(user_type,trainingModel);
        }
    }

    private void UpdateUI(String user_type, TrainingModel trainingModel) {
        if (user_type.equals(Tags.visitor))
        {
            detailsBinding.cardVideo.setVisibility(View.GONE);

        }else if (user_type.equals(Tags.user_app))
        {
            detailsBinding.cardVideo.setVisibility(View.VISIBLE);
            UpdateAdapter(trainingModel);

        }

        if (trainingModel.getReservation()==1)
        {
            detailsBinding.reserve.setVisibility(View.GONE);
        }else if (trainingModel.getReservation()==0)
        {
            detailsBinding.reserve.setVisibility(View.VISIBLE);

        }


        detailsBinding.setTranModel(trainingModel);
        Picasso.with(this).load(Uri.parse(Tags.ImgPath+ this.trainingModel.getCourse_image())).into(detailsBinding.courseImage);

    }

    private void UpdateAdapter(TrainingModel trainingModel) {

        adapter = new TrainingVideoAdapter(trainingModel.getCourses_video(),this);
        detailsBinding.recView.setAdapter(adapter);

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
    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.back:
                finish();
                break;
            case R.id.reserve:
                if (user_type!=null)
                {
                    if (user_type.equals(Tags.visitor))
                    {
                        alertDialog.show();

                    }else
                        {
                            RequestCourse(trainingModel.getCourse_id_pk(),userModel.getUser_id());

                        }
                }
                break;
        }


    }

    private void RequestCourse(int course_id_pk, String user_id) {
        //Log.e("user_id",userModel.getUser_id());

        dialog.show();
        Map<String,String> map = new HashMap<>();
        map.put("user_id_fk",user_id);
        map.put("course_id_fk",String.valueOf(course_id_pk));
        Retrofit retrofit = Api.getRetrofit();
        Services services = retrofit.create(Services.class);
        Call<ResponseModel> call = services.ReserveCourse(map);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful())
                {
                    Log.e("msg",response.body().getMessage()+"");

                    if (response.body().getMessage() ==1)
                    {
                        dialog.dismiss();
                        Toast.makeText(CourseDetailsActivity.this, R.string.course_booked, Toast.LENGTH_LONG).show();
                    }else if (response.body().getMessage() ==0)
                    {
                        dialog.dismiss();

                        Toast.makeText(CourseDetailsActivity.this, R.string.fail_try, Toast.LENGTH_LONG).show();

                    }else if (response.body().getMessage() ==2)
                    {
                        dialog.dismiss();

                        Toast.makeText(CourseDetailsActivity.this, R.string.course_booked_later, Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                dialog.dismiss();

                Log.e("error",t.getMessage());
                Toast.makeText(CourseDetailsActivity.this, R.string.fail_try, Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public void getUserData(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setItem(TrainingModel.VideoModel videoModel) {
        Intent intent = new Intent(this,VideoViewActivity.class);
        intent.putExtra("data",videoModel);
        startActivity(intent);
    }
}
