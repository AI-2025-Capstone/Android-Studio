package com.example.bigpicture.Home;

import android.content.Context;
import android.graphics.Color;
import android.util.Log; // ◀◀◀ 이 import 구문을 추가하세요.

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class CustomClusterRenderer extends DefaultClusterRenderer<HouseClusterItem> {

    // (이하 코드는 이전과 동일합니다)
    private final GoogleMap map;
    private static final float ZOOM_THRESHOLD = 16.0f;

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<HouseClusterItem> clusterManager) {
        super(context, map, clusterManager);
        this.map = map;
    }

    @Override
    protected void onBeforeClusterItemRendered(HouseClusterItem item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<HouseClusterItem> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
    }

    @Override
    public int getColor(int clusterSize) {
        if (clusterSize >= 10) {
            return Color.rgb(255, 165, 0); // 주황색
        } else {
            return Color.rgb(0, 127, 255); // 파란색
        }
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<HouseClusterItem> cluster) {
        try {
            float currentZoom = map.getCameraPosition().zoom;
            return currentZoom < ZOOM_THRESHOLD;
        } catch (Exception e) {
            Log.e("CustomClusterRenderer", "shouldRenderAsCluster에서 오류 발생", e);
            return super.shouldRenderAsCluster(cluster);
        }
    }
}