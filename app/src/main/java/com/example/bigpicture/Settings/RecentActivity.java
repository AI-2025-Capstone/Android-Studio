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

        // 뷰들을 초기화하는 작업은 onCreate에서 한 번만 수행
        recyclerView = findViewById(R.id.recyclerView_recent);
        tvEmptyList = findViewById(R.id.tv_empty_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 화면이 사용자에게 보일 때마다 이 코드가 실행
        loadRecentItems();
    }

    private void loadRecentItems() {
        // SharedPreferences에서 최신 목록을 불러옴
        recentItems = RecentItemManager.getRecentItems(this);

        if (recentItems.isEmpty()) {
            // 목록이 비어있으면 "없음" 텍스트를 표시
            recyclerView.setVisibility(View.GONE);
            tvEmptyList.setVisibility(View.VISIBLE);
        } else {
            // 목록이 있으면 RecyclerView를 설정하고 보여줌
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyList.setVisibility(View.GONE);
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        // 어댑터를 생성하고 아이템 클릭 리스너를 설정
        adapter = new RecentAdapter(this, recentItems, item -> {
            // 아이템 클릭 시, 해당 매물 정보로 BottomSheet를 띄움
            HouseDetailsBottomSheetFragment bottomSheet = HouseDetailsBottomSheetFragment.newInstance(item);
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });
        recyclerView.setAdapter(adapter);
    }
}