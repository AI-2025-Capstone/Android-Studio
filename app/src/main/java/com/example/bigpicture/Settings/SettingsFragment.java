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

// View.OnClickListener를 구현
public class SettingsFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // fragment_settings.xml 레이아웃을 화면에 표시하도록 설정
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 레이아웃의 TextView들을 담을 변수를 선언
        TextView tvRecentListings = view.findViewById(R.id.tvrecently);
        TextView tvNotices = view.findViewById(R.id.tvnoti);
        TextView tvDarkMode = view.findViewById(R.id.tvmode);

        // 각 TextView에 클릭 리스너를 설정
        tvRecentListings.setOnClickListener(this);
        tvNotices.setOnClickListener(this);
        tvDarkMode.setOnClickListener(this);
    }

    // 클릭 이벤트가 발생했을 때 호출되는 메서드
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvrecently) {
            // '최근 본 매물' 클릭 시
            // Intent를 사용하려면 Activity의 Context가 필요하므로 getActivity()를 사용
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