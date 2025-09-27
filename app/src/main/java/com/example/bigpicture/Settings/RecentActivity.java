package com.example.bigpicture.Settings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bigpicture.R;

public class RecentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fragment_recentlylist.xml 파일을 화면에 표시하도록 설정합니다.
        setContentView(R.layout.fragment_recentlylist);
    }
}