package com.example.ashwin.webview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mTitleEditText, mUrlEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews()
    {
        mTitleEditText = (EditText) findViewById(R.id.titleEditText);
        mUrlEditText = (EditText) findViewById(R.id.urlEditText);
    }

    public void goToGoogle(View view)
    {
        Intent intent = new Intent(MainActivity.this, WebActivity.class);
        intent.putExtra("title", mTitleEditText.getText().toString());
        intent.putExtra("url", mUrlEditText.getText().toString());
        startActivity(intent);
    }
}
