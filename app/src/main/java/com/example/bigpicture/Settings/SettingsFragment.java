package com.example.bigpicture.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bigpicture.R;
// NoticeActivity.class 등 새로 만들 Activity들을 import 해야 할 수 있습니다.

// View.OnClickListener를 구현(implements)합니다.
public class SettingsFragment extends Fragment implements View.OnClickListener {

    // 레이아웃의 TextView들을 담을 변수를 선언합니다.
    private TextView tvRecentListings;
    private TextView tvNotices;
    private TextView tvDarkMode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // fragment_settings.xml 레이아웃을 화면에 표시하도록 설정합니다.
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // onCreateView에서 만들어진 view를 사용해 TextView 변수들을 초기화합니다.
        tvRecentListings = view.findViewById(R.id.tvrecently);
        tvNotices = view.findViewById(R.id.tvnoti);
        tvDarkMode = view.findViewById(R.id.tvmode);

        // 각 TextView에 클릭 리스너를 설정합니다.
        tvRecentListings.setOnClickListener(this);
        tvNotices.setOnClickListener(this);
        tvDarkMode.setOnClickListener(this);
    }

    // 6. 클릭 이벤트가 발생했을 때 호출되는 메서드입니다.
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvrecently) {
            // '최근 본 매물' 클릭 시
            // Intent를 사용하려면 Activity의 Context가 필요하므로 getActivity()를 사용합니다.
            Intent intent = new Intent(getActivity(), RecentActivity.class);
            startActivity(intent);

        } else if (id == R.id.tvnoti) {
            // '공지사항' 클릭 시
            Intent intent = new Intent(getActivity(), NoticeActivity.class);
            startActivity(intent);

        } else if (id == R.id.tvmode) {
            // '다크모드 설정' 클릭 시
            Intent intent = new Intent(getActivity(), ModeActivity.class);
            startActivity(intent);
        }
    }
}