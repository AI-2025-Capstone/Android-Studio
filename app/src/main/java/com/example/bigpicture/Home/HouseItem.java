package com.example.bigpicture.Home;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class HouseItem implements Serializable {
    @SerializedName("house_id")
    public String houseId;
    public String address;
    @SerializedName("price_type")
    public String priceType;
    public int price;
    @SerializedName("price_for_ws")
    public Integer priceForWs; // 전세의 경우 null일 수 있으므로 Integer
    @SerializedName("maintenance_fee")
    public String maintenanceFee;
    public List<String> options;
    @SerializedName("detail_info")
    public Map<String, String> detailInfo;
    public double longitude;
    public double latitude;
    @SerializedName("img_urls")
    public List<String> imageUrls;
}