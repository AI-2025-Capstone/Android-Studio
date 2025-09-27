package com.example.bigpicture.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.bigpicture.Home.HouseItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecentItemManager {

    private static final String PREFS_NAME = "RecentPrefs";
    private static final String KEY_RECENT_ITEMS = "recent_house_items";
    private static final int MAX_ITEMS = 10;

    // SharedPreferences에 매물 아이템 저장
    public static void addRecentItem(Context context, HouseItem newItem) {
        List<HouseItem> recentItems = getRecentItems(context);

        // 이미 목록에 있는지 확인 (house_id 기준)
        recentItems.removeIf(item -> item.houseId.equals(newItem.houseId));

        // 목록 맨 앞에 추가
        recentItems.add(0, newItem);

        // 목록이 10개를 초과하면 가장 오래된 아이템 제거
        if (recentItems.size() > MAX_ITEMS) {
            recentItems = recentItems.subList(0, MAX_ITEMS);
        }

        saveRecentItems(context, recentItems);
    }

    // SharedPreferences에서 매물 목록 불러오기
    public static List<HouseItem> getRecentItems(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_RECENT_ITEMS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<HouseItem>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    // 매물 목록을 JSON 형태로 SharedPreferences에 저장
    private static void saveRecentItems(Context context, List<HouseItem> items) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(items);
        editor.putString(KEY_RECENT_ITEMS, json);
        editor.apply();
    }
}