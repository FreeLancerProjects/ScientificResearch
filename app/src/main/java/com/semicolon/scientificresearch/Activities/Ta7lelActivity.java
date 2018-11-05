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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.Models.ResponseModel;
import com.semicolon.scientificresearch.Models.UserModel;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.Services.Api;
import com.semicolon.scientificresearch.Services.Services;
import com.semicolon.scientificresearch.Services.Tags;
import com.semicolon.scientificresearch.SingleTone.UserSingleTone;
import com.semicolon.scientificresearch.databinding.ActivityTa7lelBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

public class Ta7lelActivity extends AppCompatActivity implements Events,UserSingleTone.UserDataInterface{

    private ActivityTa7lelBinding ta7lelBinding;
    private FilePickerDialog filePickerDialog;
    private ProgressDialog dialog;
    private String encodedFile,encodedFile2;
    private UserModel userModel;
    private String user_type="";
    private AlertDialog alertDialog;
    private String file_word_path="",file_xls_path="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ta7lelBinding = DataBindingUtil.setContentView(this,R.layout.activity_ta7lel);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        ta7lelBinding.setEvent(this);
        UserSingleTone singleTone = UserSingleTone.getInstance();
        singleTone.GetUserData(this);
        CreateProgDialog();
        CreateAlertDialog();
        getDataFromIntent();
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
    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.back:
                finish();
                break;
            case R.id.plan_file_btn:
                if (user_type!=null && user_type.equals(Tags.visitor))
                {
                    alertDialog.show();
                }else
                    {
                        SelectWordFile();

                    }
                break;
            case R.id.data_file_btn:
                if (user_type!=null && user_type.equals(Tags.visitor))
                {
                    alertDialog.show();
                }else
                {
                    SelectXlsFile();

                }
                break;
            case R.id.upload_btn:
                if (user_type!=null && user_type.equals(Tags.visitor))
                {
                    alertDialog.show();

                }else
                    {
                        upload_file();


                    }
                break;
        }
    }
    private void SelectXlsFile() {
        String [] exten = {"xls","xlsx"};

        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = exten;

        filePickerDialog = new FilePickerDialog(this,properties);
        filePickerDialog.setTitle("Select file");
        filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                Log.e("file",files[0]+"");
                file_xls_path = files[0];
                ta7lelBinding.tvData.setText(file_xls_path.substring(file_xls_path.lastIndexOf("/")+1));
                File file = new File(file_xls_path);
                try {
                    InputStream inputStream = new FileInputStream(file);
                    enCodeFile(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        filePickerDialog.show();

        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("file/*");
        startActivityForResult(intent.createChooser(intent,"إختر الملف"),FILE_REQ);*/
    }
    private void SelectWordFile() {
        String [] exten = {"doc","docx"};

        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = exten;

        filePickerDialog = new FilePickerDialog(this,properties);
        filePickerDialog.setTitle("Select file");
        filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                Log.e("file",files[0]+"");
                file_word_path = files[0];
                ta7lelBinding.tvPlan.setText(file_word_path.substring(file_word_path.lastIndexOf("/")+1));
                File file = new File(file_word_path);
                try {
                    InputStream inputStream = new FileInputStream(file);
                    enCodeFile2(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        filePickerDialog.show();

        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("file/*");
        startActivityForResult(intent.createChooser(intent,"إختر الملف"),FILE_REQ);*/
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
    private void enCodeFile2(InputStream inputStream)
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

            encodedFile2 = Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);
            Log.e("file",encodedFile2);
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
    private void upload_file() {
 //       Log.e("user_id",userModel.getUser_id());

        if (!TextUtils.isEmpty(file_word_path)&& !TextUtils.isEmpty(file_xls_path))
        {
            dialog.show();
            Map<String,String> map = new HashMap<>();
            Log.e("user_id",userModel.getUser_id());
            map.put("user_id_fk",userModel.getUser_id());
            map.put("requested_file",encodedFile2);
            map.put("other_requested_file",encodedFile);

            Log.e("file",encodedFile);
            Retrofit retrofit = Api.getRetrofit();
            Services services = retrofit.create(Services.class);
            Call<ResponseModel> call = services.UploadTa7lel(map);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful())
                    {
                        if (response.body().getMessage()==1)
                        {
                            dialog.dismiss();
                            Toast.makeText(Ta7lelActivity.this, R.string.file_uploaded, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    dialog.dismiss();
                    Log.e("Error",t.getMessage());
                    Toast.makeText(Ta7lelActivity.this, R.string.fail_try, Toast.LENGTH_SHORT).show();
                }
            });
        }else if (TextUtils.isEmpty(file_word_path))
        {
            Toast.makeText(this, R.string.choose_plan_file, Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(file_xls_path))
        {
            Toast.makeText(this, R.string.choose_data_file, Toast.LENGTH_LONG).show();

        }


        }

    @Override
    public void getUserData(UserModel userModel) {
        this.userModel = userModel;
    }
}
