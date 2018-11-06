package com.semicolon.scientificresearch.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.semicolon.scientificresearch.EventListener.Events;
import com.semicolon.scientificresearch.Models.TrainingModel;
import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.Services.Tags;
import com.semicolon.scientificresearch.databinding.ActivityVideoViewBinding;

import cn.jzvd.Jzvd;


public class VideoViewActivity extends AppCompatActivity implements Events {
    private ActivityVideoViewBinding binding;
    private TrainingModel.VideoModel videoModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_view);
        binding.setEvent(this);
        getDataFromIntent();



    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            videoModel = (TrainingModel.VideoModel) intent.getSerializableExtra("data");
            UpdateUI(videoModel);
        }
    }

    private void UpdateUI(TrainingModel.VideoModel videoModel) {
        binding.tvTitle.setText(videoModel.getVideo_title());
        //binding.imgPlayBtn.setVisibility(View.GONE);
        binding.videoplayer.setUp(Tags.video_path+videoModel.getVideo()
                , videoModel.getVideo_title() , Jzvd.SCREEN_WINDOW_NORMAL);
        binding.videoplayer.batteryLevel.setVisibility(View.GONE);
        binding.videoplayer.bottomProgressBar.setVisibility(View.VISIBLE);
        binding.videoplayer.loadingProgressBar.setVisibility(View.VISIBLE);
        binding.videoplayer.loadingProgressBar.setIndeterminate(true);


    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        /*if (id==R.id.imgPlayBtn)
        {



        }else*/ if (id==R.id.back)
        {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
//https://github.com/MarcinMoskala/VideoPlayView/raw/master/videos/cat1.mp4"
