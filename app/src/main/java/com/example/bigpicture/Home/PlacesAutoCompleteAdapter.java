package com.example.bigpicture.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigpicture.R;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import java.util.ArrayList;
import java.util.List;

public class PlacesAutoCompleteAdapter extends RecyclerView.Adapter<PlacesAutoCompleteAdapter.PredictionViewHolder> {

    private List<AutocompletePrediction> predictions = new ArrayList<>();
    private OnPlaceClickListener onPlaceClickListener;

    public interface OnPlaceClickListener {
        void onPlaceClick(AutocompletePrediction prediction);
    }

    public PlacesAutoCompleteAdapter(OnPlaceClickListener listener) {
        this.onPlaceClickListener = listener;
    }

    public void setPredictions(List<AutocompletePrediction> predictions) {
        this.predictions = predictions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PredictionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_suggestion, parent, false);
        return new PredictionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionViewHolder holder, int position) {
        AutocompletePrediction prediction = predictions.get(position);
        holder.bind(prediction);
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }

    class PredictionViewHolder extends RecyclerView.ViewHolder {
        private TextView placeNameTextView;

        PredictionViewHolder(@NonNull View itemView) {
            super(itemView);
            placeNameTextView = itemView.findViewById(R.id.text_view_place_name);

            itemView.setOnClickListener(v -> {
                if (onPlaceClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onPlaceClickListener.onPlaceClick(predictions.get(getAdapterPosition()));
                }
            });
        }

        void bind(AutocompletePrediction prediction) {
            placeNameTextView.setText(prediction.getFullText(null));
        }
    }
}