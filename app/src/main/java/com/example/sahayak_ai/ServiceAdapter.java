package com.example.sahayak_ai;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private List<ServiceModel> serviceList;
    private OnServiceClickListener listener;

    public interface OnServiceClickListener {
        void onServiceClick(ServiceModel service);
    }

    public ServiceAdapter(List<ServiceModel> serviceList, OnServiceClickListener listener) {
        this.serviceList = serviceList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceModel service = serviceList.get(position);

        holder.tvServiceName.setText(service.getName());
        holder.tvDepartment.setText(service.getDepartment());
        holder.tvStatus.setText(service.isActive() ? "Active" : "Inactive");
        holder.tvStatus.setBackgroundResource(service.isActive() ? R.drawable.bg_badge_emerald : R.drawable.bg_badge_rose);
        holder.tvStatus.setTextColor(Color.parseColor(service.isActive() ? "#059669" : "#B91C1C"));

        // Set dynamic icon and color
        int iconRes = getIconResource(service.getIcon());
        int colorRes = getColorResource(service.getColor());
        int bgRes = getBgResource(service.getColor());

        holder.ivServiceIcon.setImageResource(iconRes);
        holder.ivServiceIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), colorRes)));
        holder.flIconBg.setBackgroundResource(bgRes);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onServiceClick(service);
            }
        });
    }

    private int getIconResource(String iconName) {
        switch (iconName != null ? iconName : "") {
            case "ic_description": return R.drawable.ic_description;
            case "ic_group": return R.drawable.ic_group;
            case "ic_home": return R.drawable.ic_home;
            case "ic_child_care": return R.drawable.ic_child_care;
            default: return R.drawable.ic_document;
        }
    }

    private int getColorResource(String colorName) {
        switch (colorName != null ? colorName : "") {
            case "indigo": return R.color.indigo;
            case "orange": return R.color.orange;
            case "blue": return R.color.blue;
            case "pink": return R.color.pink;
            default: return R.color.primary;
        }
    }

    private int getBgResource(String colorName) {
        switch (colorName != null ? colorName : "") {
            case "indigo": return R.drawable.bg_icon_indigo;
            case "orange": return R.drawable.bg_icon_orange;
            case "blue": return R.drawable.bg_icon_blue;
            case "pink": return R.drawable.bg_icon_pink;
            default: return R.drawable.bg_circle_primary_10;
        }
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceName, tvDepartment, tvStatus;
        ImageView ivServiceIcon;
        FrameLayout flIconBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivServiceIcon = itemView.findViewById(R.id.ivServiceIcon);
            flIconBg = itemView.findViewById(R.id.flIconBg);
        }
    }
}
