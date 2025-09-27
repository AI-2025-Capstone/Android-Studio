package com.example.bigpicture.Settings;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
// Material 디자인 팝업을 위해 import
import com.example.bigpicture.R;

public class NoticeActivity extends AppCompatActivity {

    private TextView tvAnnounce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fragment_recentlylist.xml 파일을 화면에 표시하도록 설정합니다.
        setContentView(R.layout.fragment_notice);

        // 1. XML 레이아웃의 TextView를 ID로 찾아와 변수에 연결합니다.
        tvAnnounce = findViewById(R.id.tvannounce);

        // 2. TextView에 클릭 리스너를 설정합니다.
        tvAnnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3. 클릭 시 showNoticeDialog() 메서드를 호출해 팝업을 띄웁니다.
                showNoticeDialog();
            }
        });
    }

        // 4. 공지사항 팝업을 생성하고 보여주는 메서드
        private void showNoticeDialog() {
            // MaterialAlertDialogBuilder를 사용해 세련된 디자인의 팝업을 만듭니다.
            new MaterialAlertDialogBuilder(this)
                    .setTitle("📢 빅픽처부동산 공지") // 팝업창 제목
                    .setMessage("빅픽처부동산 앱이 새롭게 출시되었습니다! \n많은 사랑과 관심 부탁. \n\n- 빅픽처팀 드림 -") // 팝업창 내용
                    .setPositiveButton("확인", (dialog, which) -> {
                        // "확인" 버튼을 눌렀을 때의 동작
                        dialog.dismiss(); // 팝업창 닫기
                    })
                    .show(); // 팝업창 보여주기
    }
}
