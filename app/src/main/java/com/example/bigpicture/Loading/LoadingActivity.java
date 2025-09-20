package com.example.bigpicture.Loading;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bigpicture.MainActivity;
import com.example.bigpicture.R;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        View rootView = findViewById(R.id.loading_root);
        rootView.setOnClickListener(v -> {
            // MainActivity로 이동
            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(intent);
            // 로딩 화면은 종료하여 뒤로 가기로 돌아오지 않도록 함
            finish();
        });
    }
}