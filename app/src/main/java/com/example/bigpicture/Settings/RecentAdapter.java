package com.example.bigpicture.Settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.bigpicture.Home.HouseItem;
import com.example.bigpicture.R;
import java.util.List;
import java.util.Locale;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    private List<HouseItem> recentItems;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(HouseItem item);
    }

    public RecentAdapter(Context context, List<HouseItem> recentItems, OnItemClickListener listener) {
        this.context = context;
        this.recentItems = recentItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HouseItem item = recentItems.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return recentItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvPrice;
        TextView tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_house_thumbnail);
            tvPrice = itemView.findViewById(R.id.tv_house_price);
            tvAddress = itemView.findViewById(R.id.tv_house_address);
        }

        public void bind(final HouseItem item, final OnItemClickListener listener) {
            // 가격 설정
            if ("월세".equals(item.priceType)) {
                tvPrice.setText(String.format(Locale.KOREA, "%s %d/%d", item.priceType, item.price, item.priceForWs));
            } else {
                tvPrice.setText(String.format(Locale.KOREA, "%s %,d", item.priceType, item.price));
            }
            // 주소 설정
            tvAddress.setText(item.address);
            // 이미지 설정
            if (item.imageUrls != null && !item.imageUrls.isEmpty()) {
                Glide.with(context).load(item.imageUrls.get(0)).into(ivThumbnail);
            }
            // 클릭 리스너 설정
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}