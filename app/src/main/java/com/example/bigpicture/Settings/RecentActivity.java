package com.example.bigpicture.Settings;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bigpicture.Home.HouseDetailsBottomSheetFragment;
import com.example.bigpicture.Home.HouseItem;
import com.example.bigpicture.R;
import java.util.List;

public class RecentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecentAdapter adapter;
    private List<HouseItem> recentItems;
    private TextView tvEmptyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recentlylist);

        recyclerView = findViewById(R.id.recyclerView_recent);
        tvEmptyList = findViewById(R.id.tv_empty_list);

        // SharedPreferences에서 최근 본 매물 목록 불러오기
        recentItems = RecentItemManager.getRecentItems(this);

        if (recentItems.isEmpty()) {
            // 목록이 비어있으면 "없음" 텍스트 표시
            recyclerView.setVisibility(View.GONE);
            tvEmptyList.setVisibility(View.VISIBLE);
        } else {
            // 목록이 있으면 RecyclerView 설정
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyList.setVisibility(View.GONE);
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 어댑터 생성 및 아이템 클릭 리스너 설정
        adapter = new RecentAdapter(this, recentItems, item -> {
            // 아이템 클릭 시, 해당 매물 정보로 BottomSheet 띄우기
            HouseDetailsBottomSheetFragment bottomSheet = HouseDetailsBottomSheetFragment.newInstance(item);
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });
        recyclerView.setAdapter(adapter);
    }
}