package com.example.admin.day05_zxing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {
    private TextView show_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initViews();
        initDatas();
        initListener();
        Toast.makeText(ShowActivity.this,"点击文字可返回",Toast.LENGTH_SHORT).show();
    }

    private void initListener() {
        show_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initDatas() {
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        show_tv.setText(text);
    }

    private void initViews() {
        show_tv = findViewById(R.id.show_tv);
    }
}
