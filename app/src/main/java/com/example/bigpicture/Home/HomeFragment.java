package com.example.bigpicture.Home;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigpicture.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.clustering.ClusterManager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private PlacesAutoCompleteAdapter adapter;
    private PlacesClient placesClient;
    private List<HouseItem> houseItems;
    private ClusterManager<HouseClusterItem> clusterManager; // ◀◀◀ ClusterManager 선언

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadHouseDataFromJson();

        // ‼️ 중요: 실제 API 키로 교체
        String apiKey = "AIzaSyC6D9wrTh1pQw8TGqr9W36kIAzerUTlp1E";
        if (!Places.isInitialized()) {
            Places.initialize(requireActivity().getApplicationContext(), apiKey);
        }
        placesClient = Places.createClient(requireActivity());

        searchView = view.findViewById(R.id.map_search_view);
        recyclerView = view.findViewById(R.id.auto_complete_recycler_view);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        setupRecyclerView();
        setupSearchView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // --- 클러스터링 설정 (초기화) ---
        clusterManager = new ClusterManager<>(requireActivity(), mGoogleMap);
        CustomClusterRenderer renderer = new CustomClusterRenderer(requireActivity(), mGoogleMap, clusterManager);
        clusterManager.setRenderer(renderer);

        // 지도 이벤트 리스너를 ClusterManager에 연결합니다.
        mGoogleMap.setOnCameraIdleListener(clusterManager);
        mGoogleMap.setOnMarkerClickListener(clusterManager);

        // 개별 마커 클릭 시 BottomSheet 표시 리스너를 설정합니다.
        clusterManager.setOnClusterItemClickListener(item -> {
            HouseDetailsBottomSheetFragment bottomSheet = HouseDetailsBottomSheetFragment.newInstance(item.getHouseItem());
            bottomSheet.show(getChildFragmentManager(), bottomSheet.getTag());
            return true;
        });

        // ▼▼▼ 지도가 완전히 로드된 후에 실행될 코드를 여기에 작성합니다. ▼▼▼
        mGoogleMap.setOnMapLoadedCallback(() -> {
            // 1. JSON 데이터로 클러스터 아이템 추가
            if (houseItems != null && !houseItems.isEmpty()) {
                for (HouseItem item : houseItems) {
                    clusterManager.addItem(new HouseClusterItem(item));
                }
            }

            // 2. 초기 카메라 위치로 이동
            LatLng hanshinUniv = new LatLng(37.194068, 127.024057);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hanshinUniv, 14));

            // 3. 초기 클러스터링 실행
            clusterManager.cluster();
        });
    }
    private void getPlaceDetails(String placeId) {
        // ▼▼▼ 맵과 클러스터 관리자가 준비되지 않았으면 작업을 중단하여 충돌을 방지합니다. ▼▼▼
        if (mGoogleMap == null || clusterManager == null) {
            if (getActivity() != null) {
                Toast.makeText(getActivity(), "지도가 로딩 중입니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
            return; // 메서드 실행 중단
        }

        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();

        placesClient.fetchPlace(request).addOnSuccessListener(response -> {
            Place place = response.getPlace();
            LatLng latLng = place.getLatLng();
            if (latLng != null) {
                // 검색 시에는 기존 클러스터를 모두 지우고 검색된 위치만 표시
                clusterManager.clearItems();
                mGoogleMap.clear();

                mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(place.getName()));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                searchView.setQuery("", false);
                searchView.clearFocus();
                recyclerView.setVisibility(View.GONE);
            }
        }).addOnFailureListener(e -> {
            if (getActivity() != null) {
                Toast.makeText(getActivity(), "장소 정보를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new PlacesAutoCompleteAdapter(prediction -> getPlaceDetails(prediction.getPlaceId()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    getAutocompletePredictions(newText);
                } else {
                    recyclerView.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }

    private void getAutocompletePredictions(String query) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setCountries("KR")
                .setQuery(query)
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(response -> {
            List<AutocompletePrediction> allPredictions = response.getAutocompletePredictions();
            adapter.setPredictions(allPredictions.size() > 3 ? allPredictions.subList(0, 3) : allPredictions);
            recyclerView.setVisibility(View.VISIBLE);
        }).addOnFailureListener(e -> {
            if (getActivity() != null) {
                Toast.makeText(getActivity(), "자동완성 검색에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadHouseDataFromJson() {
        AssetManager assetManager = requireActivity().getAssets();
        try (InputStream is = assetManager.open("university_hanshin_onetwo_houses.json");
             InputStreamReader reader = new InputStreamReader(is)) {
            Type listType = new TypeToken<List<HouseItem>>(){}.getType();
            houseItems = new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            Log.e("HomeFragment", "Error reading JSON file", e);
            if (getActivity() != null) {
                Toast.makeText(getActivity(), "매물 정보를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}