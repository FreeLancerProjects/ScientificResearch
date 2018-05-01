package com.semicolon.scientificresearch.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.semicolon.scientificresearch.R;
import com.semicolon.scientificresearch.databinding.ActivityOtherWebViewBinding;

public class OtherWebViewActivity extends AppCompatActivity {

    private ActivityOtherWebViewBinding webViewBinding;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewBinding = DataBindingUtil.setContentView(this,R.layout.activity_other_web_view);
        getDataFromIntent();
        webViewBinding.webView.getSettings().setJavaScriptEnabled(true);
        webViewBinding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewBinding.webView.getSettings().setLoadWithOverviewMode(true);
        webViewBinding.webView.getSettings().setUseWideViewPort(true);
        webViewBinding.webView.getSettings().setBuiltInZoomControls(true);

        webViewBinding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webViewBinding.webView.loadUrl(url);
                return false;
            }
        });
        webViewBinding.webView.loadUrl(url);


    }
// اخري https://www.surveysystem.com/sscalc.htm
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            url = intent.getStringExtra("url");
        }
    }


}
