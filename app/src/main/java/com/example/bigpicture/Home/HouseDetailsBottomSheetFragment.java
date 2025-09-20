package com.example.bigpicture.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.example.bigpicture.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Locale;
import java.util.Map;

public class HouseDetailsBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_HOUSE_ITEM = "house_item";

    public static HouseDetailsBottomSheetFragment newInstance(HouseItem houseItem) {
        HouseDetailsBottomSheetFragment fragment = new HouseDetailsBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_HOUSE_ITEM, houseItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HouseItem houseItem = (HouseItem) getArguments().getSerializable(ARG_HOUSE_ITEM);

        if (houseItem != null) {
            ImageView houseImage = view.findViewById(R.id.house_image);
            TextView priceText = view.findViewById(R.id.price_text);
            TextView addressText = view.findViewById(R.id.address_text);
            TextView maintenanceFeeText = view.findViewById(R.id.maintenance_fee_text);
            LinearLayout detailInfoContainer = view.findViewById(R.id.detail_info_container);
            ChipGroup optionsChipGroup = view.findViewById(R.id.options_chip_group);

            // 1. 이미지 로딩
            if (houseItem.imageUrls != null && !houseItem.imageUrls.isEmpty()) {
                Glide.with(this).load(houseItem.imageUrls.get(0)).into(houseImage);
            }

            // 2. 가격 정보 설정
            if ("월세".equals(houseItem.priceType)) {
                priceText.setText(String.format(Locale.KOREA, "%d / %d (%s)", houseItem.price, houseItem.priceForWs, houseItem.priceType));
            } else {
                priceText.setText(String.format(Locale.KOREA, "%,d (%s)", houseItem.price, houseItem.priceType));
            }

            // 3. 주소 및 관리비 설정
            addressText.setText(houseItem.address);
            maintenanceFeeText.setText("관리비: " + houseItem.maintenanceFee);

            // 4. 상세 정보 동적 추가
            detailInfoContainer.removeAllViews();
            for (Map.Entry<String, String> entry : houseItem.detailInfo.entrySet()) {
                View detailItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_detail_info, detailInfoContainer, false);
                TextView keyText = detailItemView.findViewById(R.id.detail_key);
                TextView valueText = detailItemView.findViewById(R.id.detail_value);
                keyText.setText(entry.getKey());
                valueText.setText(entry.getValue());
                detailInfoContainer.addView(detailItemView);
            }

            // 5. 옵션 Chip 동적 추가
            optionsChipGroup.removeAllViews();
            if (houseItem.options != null) {
                for (String option : houseItem.options) {
                    Chip chip = new Chip(getContext());
                    chip.setText(option);
                    optionsChipGroup.addView(chip);
                }
            }
        }
    }
}