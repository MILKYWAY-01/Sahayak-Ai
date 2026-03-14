package com.example.sahayak_ai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    private List<ApplicationModel> applicationList;

    public ApplicationAdapter(List<ApplicationModel> applicationList) {
        this.applicationList = applicationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_application, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApplicationModel model = applicationList.get(position);

        holder.tvApplicationTitle.setText(model.getTitle());
        holder.tvLocation.setText(model.getLocation());
        holder.tvDate.setText(model.getDate());

        if (model.getReason() != null && !model.getReason().isEmpty()) {
            holder.tvReason.setText(model.getReason());
            holder.tvReason.setVisibility(View.VISIBLE);
        } else {
            holder.tvReason.setVisibility(View.GONE);
        }

        if (model.isUrgent()) {
            holder.tvUrgentBadge.setVisibility(View.VISIBLE);
        } else {
            holder.tvUrgentBadge.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvApplicationTitle, tvLocation, tvDate, tvReason, tvUrgentBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvApplicationTitle = itemView.findViewById(R.id.tvApplicationTitle);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvReason = itemView.findViewById(R.id.tvReason);
            tvUrgentBadge = itemView.findViewById(R.id.tvUrgentBadge);
        }
    }
}
