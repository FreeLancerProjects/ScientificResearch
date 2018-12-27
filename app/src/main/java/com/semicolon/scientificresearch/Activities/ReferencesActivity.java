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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.Models.ResponseModel;
import com.semicolon.scientificresearch.Models.UserModel;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.Services.Api;
import com.semicolon.scientificresearch.Services.Services;
import com.semicolon.scientificresearch.Services.Tags;
import com.semicolon.scientificresearch.SingleTone.UserSingleTone;
import com.semicolon.scientificresearch.databinding.ActivityReferencesBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferencesActivity extends AppCompatActivity implements Events,UserSingleTone.UserDataInterface{

    private String [] spinner_array ;
    private ActivityReferencesBinding activityReferencesBinding;
    private String lang="";
    private AlertDialog alertDialog;
    private ProgressDialog dialog;

    private String user_type="";
    private UserSingleTone userSingleTone;
    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReferencesBinding = DataBindingUtil.setContentView(this,R.layout.activity_references);
        activityReferencesBinding.setEvent(this);
        userSingleTone = UserSingleTone.getInstance();
        userSingleTone.GetUserData(this);
        getDataFromIntent();
        spinner_array = getResources().getStringArray(R.array.lang);
        CreateAlertDialog();
        CreateProgDialog();
        activityReferencesBinding.spinnerLang.setAdapter(new ArrayAdapter<>(this,R.layout.custom_spinner,spinner_array));
        activityReferencesBinding.spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        lang="";
                        break;
                    case 1:
                        lang="1";

                        break;
                    case 2:
                        lang="2";

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void CreateProgDialog()
    {
        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.ko7ly), PorterDuff.Mode.SRC_IN);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.upload_file));
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

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
        if (id == R.id.image_back)
        {
            finish();
        }else if (id == R.id.send_btn)
        {
            if (user_type!=null && user_type.equals(Tags.visitor))
            {
                alertDialog.show();

            }else
            {
                CheckData();

            }
        }
    }

    private void CheckData() {
        String search_title = activityReferencesBinding.edtSearchTitle.getText().toString().trim();
        String search_details = activityReferencesBinding.edtSearchDetails.getText().toString().trim();

        if (!TextUtils.isEmpty(search_title)&&!TextUtils.isEmpty(search_details)&&!TextUtils.isEmpty(lang))
        {
            activityReferencesBinding.edtSearchTitle.setError(null);
            activityReferencesBinding.edtSearchDetails.setError(null);
            Send(search_title,search_details,lang);
        }else
            {
                if (TextUtils.isEmpty(search_title))
                {
                    activityReferencesBinding.edtSearchTitle.setError(getString(R.string.field_required));

                }else
                    {
                        activityReferencesBinding.edtSearchTitle.setError(null);

                    }

                if (TextUtils.isEmpty(search_details))
                {
                    activityReferencesBinding.edtSearchDetails.setError(getString(R.string.field_required));

                }else
                {
                    activityReferencesBinding.edtSearchDetails.setError(null);

                }

                if (TextUtils.isEmpty(lang))
                {
                    Toast.makeText(this, R.string.Choose_language_search, Toast.LENGTH_SHORT).show();
                }

            }

    }

    private void CreateAlertDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.ser_not_av)
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                        finish();

                    }
                }).setCancelable(false).create();
    }
    private void Send(String search_title, String search_details, String lang) {
        dialog.show();
        Api.getRetrofit()
                .create(Services.class)
                .ask_references(userModel.getUser_id(),search_title,search_details,lang)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful())
                        {
                            dialog.dismiss();
                            if (response.body().getMessage()==1)
                            {
                                finish();
                            }else
                                {
                                    Toast.makeText(ReferencesActivity.this,R.string.something, Toast.LENGTH_SHORT).show();

                                }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        try {
                            Log.e("Error",t.getMessage());
                            dialog.dismiss();
                            Toast.makeText(ReferencesActivity.this,R.string.something, Toast.LENGTH_SHORT).show();
                        }catch (Exception e){}
                    }
                });

    }

    @Override
    public void getUserData(UserModel userModel) {
        this.userModel = userModel;
    }
}
