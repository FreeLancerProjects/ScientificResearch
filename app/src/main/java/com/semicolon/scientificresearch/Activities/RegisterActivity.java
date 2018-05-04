package com.semicolon.scientificresearch.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.Models.UserModel;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.Services.Api;
import com.semicolon.scientificresearch.Services.Preferences;
import com.semicolon.scientificresearch.Services.Services;
import com.semicolon.scientificresearch.Services.Tags;
import com.semicolon.scientificresearch.SingleTone.UserSingleTone;
import com.semicolon.scientificresearch.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements Events{
    private ActivityRegisterBinding registerBinding;
    private String user_Type,degree;
    private UserSingleTone userSingleTone;
    private ProgressDialog dialog;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerBinding.userPhone.setDefaultCountry("sa");
        registerBinding.userPhone.getTextInputLayout().getEditText().setTextColor(Color.WHITE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.user_type));
        registerBinding.userType.setAdapter(adapter);
        registerBinding.userType.setSelection(0);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.type));
        registerBinding.type.setAdapter(adapter2);
        registerBinding.type.setSelection(0);
        registerBinding.setEvent(this);
        registerBinding.userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try
                {
                    int pos = registerBinding.userType.getSelectedItemPosition();
                    switch (pos)
                    {
                        case 0:
                            user_Type= Tags.mo7alel;
                            break;
                        case 1:
                            user_Type= Tags.ba7eth;

                            break;
                        case 2:
                            user_Type= Tags.mo7akem;

                            break;
                        case 3:
                            user_Type = Tags.modaqeq;
                            break;

                    }
                    ((TextView)adapterView.getChildAt(0)).setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.white));

                }catch (NullPointerException e){}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        registerBinding.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try
                {
                    int pos = registerBinding.type.getSelectedItemPosition();
                    switch (pos)
                    {
                        case 0:
                            degree= Tags.doc;
                            break;
                        case 1:
                            degree= Tags.mo3eed;

                            break;
                        case 2:
                            degree= Tags.mo7ader;

                            break;
                        case 3:
                            degree = Tags.ostaz;
                            break;
                        case 4:
                            degree = Tags.ostazmosa3ed;
                            break;
                        case 5:
                            degree = Tags.ostazmosharek;
                            break;

                    }
                    ((TextView)adapterView.getChildAt(0)).setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.white));

                }catch (NullPointerException e){}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ////////////////////////////////////////////
        preferences = new Preferences(this);
        userSingleTone = UserSingleTone.getInstance();
        CreateProgDialog();
        ////////////////////////////////////////////


    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.create_account:
                CreateNewAccount();

                break;
            case R.id.back:
                Intent intent2 = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent2);
                finish();
                break;

        }
    }

    private void CreateProgDialog()
    {
        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.ko7ly), PorterDuff.Mode.SRC_IN);
        dialog = new ProgressDialog(this);
        dialog.setMessage("جار إنشاء حساب جديد...");
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

    }
    private void CreateNewAccount() {

        String name = registerBinding.name.getText().toString();
        String email= registerBinding.email.getText().toString();
        String phone= registerBinding.userPhone.getPhoneNumber();
        String country=registerBinding.country.getText().toString();
        String organization = registerBinding.organization.getText().toString();
        String user_name = registerBinding.userName.getText().toString();
        String user_pass = registerBinding.userPassword.getText().toString();
        String user_specialization = registerBinding.speciality.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            registerBinding.name.setError("أدخل الاسم الاول والاخير");
        }else if (TextUtils.isEmpty(email))
        {
            registerBinding.name.setError(null);
            registerBinding.email.setError("أدخل البريد الالكتروني");


        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            registerBinding.name.setError(null);
            registerBinding.email.setError("تحقق من البريد الالكتروني");


        }
        else if (TextUtils.isEmpty(phone))
        {
            registerBinding.email.setError(null);
            registerBinding.userPhone.setError("أدخل رقم الجوال");

        }
        else if (!registerBinding.userPhone.isValid())
        {
            registerBinding.email.setError(null);
            registerBinding.userPhone.setError("تحقق من رقم الجوال");

        }
        else if (TextUtils.isEmpty(country))
        {
            registerBinding.userPhone.setError(null);
            registerBinding.country.setError("أدخل الدولة");

        }
        else if (TextUtils.isEmpty(organization))
        {
            registerBinding.country.setError(null);
            registerBinding.organization.setError("أدخل إسم المؤسسة");

        }
        else if (TextUtils.isEmpty(user_specialization))
        {
            registerBinding.organization.setError(null);
            registerBinding.speciality.setError("أدخل التخصص");

        }
        else if (TextUtils.isEmpty(user_name))
        {
            registerBinding.speciality.setError(null);
            registerBinding.userPhone.setError("أدخل إسم المستخدم");

        }
        else if (TextUtils.isEmpty(user_pass))
        {
            registerBinding.userName.setError(null);
            registerBinding.userPassword.setError("أدخل كلمة المرور");

        }
        else
            {
                dialog.show();
                Map<String,String> map = new HashMap<>();
                map.put("user_name",name);
                map.put("user_username",user_name);
                map.put("user_pass",user_pass);
                map.put("user_email",email);
                map.put("user_phone",phone);
                map.put("user_country",country);
                map.put("degree",degree);
                map.put("company",organization);
                map.put("specialization",user_specialization);
                map.put("user_type",user_Type);

                registerBinding.userPassword.setError(null);
                Retrofit retrofit = Api.getRetrofit();
                Services services = retrofit.create(Services.class);
                Call<UserModel> call = services.CreateNewAccount(map);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful())
                        {


                        UserModel userModel = response.body();
                        if (userModel.getMessage()==1)
                        {
                            preferences.CreateSharedPref(userModel);
                            userSingleTone.SetUserData(userModel);
                            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }else
                            {
                                Toast.makeText(RegisterActivity.this, "لم يتم إنشاء حساب حاول مره أخرى لاحقا", Toast.LENGTH_LONG).show();
                                dialog.dismiss();


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.e("error",t.getMessage());
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "لم يتم إنشاء حساب حاول مره أخرى لاحقا", Toast.LENGTH_LONG).show();


                    }
                });
            }



    }


}
