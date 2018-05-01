package com.semicolon.scientificresearch.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.Models.ResponseModel;
import com.semicolon.scientificresearch.Models.UserModel;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.Services.Api;
import com.semicolon.scientificresearch.Services.Services;
import com.semicolon.scientificresearch.SingleTone.UserSingleTone;
import com.semicolon.scientificresearch.databinding.ActivityTadqeqBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TadqeqActivity extends AppCompatActivity implements Events,UserSingleTone.UserDataInterface{
    private ActivityTadqeqBinding tadqeqBinding;
    private final int FILE_REQ=1;
    private ProgressDialog dialog;
    private UserModel userModel;
    private UserSingleTone userSingleTone;
    private String encodedFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tadqeqBinding = DataBindingUtil.setContentView(this,R.layout.activity_tadqeq);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        userSingleTone = UserSingleTone.getInstance();
        userSingleTone.GetUserData(this);
        tadqeqBinding.setEvent(this);
        CreateProgDialog();
    }

    private void CreateProgDialog()
    {
        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.ko7ly), PorterDuff.Mode.SRC_IN);
        dialog = new ProgressDialog(this);
        dialog.setMessage("جار رفع الملف...");
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

    }
    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.file_name:
                SelectFile();
                break;
            case R.id.upload_btn:
                upload_file();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void upload_file() {
        dialog.show();
        Map<String,String> map = new HashMap<>();
        map.put("user_id_fk",userModel.getUser_id());
        map.put("requested_file",encodedFile);
        Log.e("file",encodedFile);
        Retrofit retrofit = Api.getRetrofit();
        Services services = retrofit.create(Services.class);
        Call<ResponseModel> call = services.UploadTranslateFile(map);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful())
                {
                    if (response.body().getMessage()==1)
                    {
                        dialog.dismiss();
                        Toast.makeText(TadqeqActivity.this, "تم رفع الملف بنجاح", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                dialog.dismiss();
                Log.e("Error",t.getMessage());
                Toast.makeText(TadqeqActivity.this, "فشل حاول مره أخرى لاحقا", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SelectFile() {
        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(intent.createChooser(intent,"إختر الملف"),FILE_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQ && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                enCodeFile(getContentResolver().openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            tadqeqBinding.fileName.setText(String.valueOf(uri));
            Log.e("uri",uri.toString()+"");

        }
    }

    @Override
    public void getUserData(UserModel userModel) {
        this.userModel=userModel;
    }

    private void enCodeFile(InputStream inputStream)
    {
        ByteArrayOutputStream outputStream=null;
        byte [] bytes;
        int len=0;
        try {
            outputStream = new ByteArrayOutputStream();
            bytes = new byte[1024*11];

            while ((len=inputStream.read(bytes))!=-1)
            {
                outputStream.write(bytes,0,len);
            }

            encodedFile = Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);
            Log.e("file",encodedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
