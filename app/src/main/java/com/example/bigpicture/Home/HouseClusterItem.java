package com.example.bigpicture.Home;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class HouseClusterItem implements ClusterItem {
    private final HouseItem houseItem;
    private final LatLng position;

    public HouseClusterItem(HouseItem houseItem) {
        this.houseItem = houseItem;
        this.position = new LatLng(houseItem.latitude, houseItem.longitude);
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return houseItem.address;
    }

    public String getSnippet() {
        return "";
    }

    @Nullable
    @Override
    public Float getZIndex() {
        return 0f; // 기본 z-index를 사용하려면 null 또는 0f를 반환
    }

    public HouseItem getHouseItem() {
        return houseItem;
    }
}