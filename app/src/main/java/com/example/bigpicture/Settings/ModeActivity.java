package com.example.bigpicture.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.bigpicture.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ModeActivity extends AppCompatActivity {

    private SwitchMaterial switchDarkMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_darkmode);

        // 1. XML의 스위치 위젯을 ID로 찾아와 연결합니다.
        switchDarkMode = findViewById(R.id.switch_darkmode);

        // 2. SharedPreferences로 저장된 다크모드 설정값을 불러옵니다.
        //    "AppSettingPrefs"는 설정 파일을 저장할 이름, "NightMode"는 설정값의 이름(키)입니다.
        SharedPreferences sharedPref = getSharedPreferences("AppSettingPrefs", Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPref.getBoolean("NightMode", false); // 기본값은 false (라이트 모드)

        // 3. 불러온 설정값에 따라 스위치의 ON/OFF 상태를 맞춰줍니다.
        switchDarkMode.setChecked(isDarkMode);

        // 4. 스위치를 누를 때마다 호출될 리스너를 설정합니다.
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPref.edit();

            if (isChecked) {
                // 스위치가 ON 상태가 되면, 다크 모드를 활성화하고 그 상태를 저장합니다.
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("NightMode", true);
            } else {
                // 스위치가 OFF 상태가 되면, 라이트 모드를 활성화하고 그 상태를 저장합니다.
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("NightMode", false);
            }
            editor.apply(); // 변경된 설정값을 최종 저장합니다.
        });
    }
}
