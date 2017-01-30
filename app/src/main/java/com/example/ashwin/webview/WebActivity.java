package com.example.ashwin.webview;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {

    private String url = "http://google.com";
    private String title = "Google";
    private ProgressBar mProgressBar;
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        getIntentData();

        initViews();
    }


    private void getIntentData()
    {
        Intent intent = getIntent();

        if(intent.hasExtra("url") && intent.hasExtra("title"))
        {
            url = intent.getStringExtra("url");
            title = intent.getStringExtra("title");
        }
    }


    private void initViews()
    {
        initToolbar();
        initTitle();
        setContent();
        initProgressbar();
    }


    private Toolbar mToolbar;

    private void initToolbar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }


    private void initTitle()
    {
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText(title);
    }


    private void initProgressbar()
    {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mProgressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);

        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 100 && mProgressBar.getVisibility() == View.GONE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }


    private void setContent()
    {
        myWebView = (WebView) findViewById(R.id.webview);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(url);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(phoneIntent);
                    view.reload();
                    return true;
                } else if (url.startsWith("mailto:")) {
                    Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                    startActivity(Intent.createChooser(mailIntent, "Select mail client"));
                    view.reload();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
}
