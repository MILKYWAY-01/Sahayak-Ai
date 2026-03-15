package com.example.sahayak_ai;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

        // Handle Status Badge
        String status = model.getStatus() != null ? model.getStatus().toLowerCase() : "pending";
        holder.tvStatusBadge.setText(status.substring(0, 1).toUpperCase() + status.substring(1));
        
        if (status.equals("approved")) {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_emerald);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#059669"));
        } else if (status.equals("rejected")) {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_rose);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#B91C1C"));
        } else {
            holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_amber);
            holder.tvStatusBadge.setTextColor(Color.parseColor("#D97706"));
        }

        // Handle Service Icon and Colors
        String serviceType = model.getServiceType() != null ? model.getServiceType() : "";
        int iconRes = R.drawable.ic_document;
        int colorRes = R.color.primary;
        int bgColorRes = R.drawable.bg_circle_primary_10;

        if (serviceType.contains("income")) {
            iconRes = R.drawable.ic_description;
            colorRes = R.color.indigo;
            bgColorRes = R.drawable.bg_icon_indigo;
        } else if (serviceType.contains("caste")) {
            iconRes = R.drawable.ic_group;
            colorRes = R.color.orange;
            bgColorRes = R.drawable.bg_icon_orange;
        } else if (serviceType.contains("residence")) {
            iconRes = R.drawable.ic_home;
            colorRes = R.color.blue;
            bgColorRes = R.drawable.bg_icon_blue;
        } else if (serviceType.contains("birth")) {
            iconRes = R.drawable.ic_child_care;
            colorRes = R.color.pink;
            bgColorRes = R.drawable.bg_icon_pink;
        }

        holder.ivServiceIcon.setImageResource(iconRes);
        holder.ivServiceIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), colorRes)));
        holder.flIconContainer.setBackgroundResource(bgColorRes);

        // Handle Rejection Reason
        if (model.getReason() != null && !model.getReason().isEmpty()) {
            holder.tvReason.setText(model.getReason());
            holder.llReasonBox.setVisibility(View.VISIBLE);
        } else {
            holder.llReasonBox.setVisibility(View.GONE);
        }

        // Handle Urgent Badge
        holder.tvUrgentBadge.setVisibility(model.isUrgent() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvApplicationTitle, tvLocation, tvDate, tvReason, tvUrgentBadge, tvStatusBadge;
        ImageView ivServiceIcon;
        FrameLayout flIconContainer;
        LinearLayout llReasonBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvApplicationTitle = itemView.findViewById(R.id.tvApplicationTitle);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvReason = itemView.findViewById(R.id.tvReason);
            tvUrgentBadge = itemView.findViewById(R.id.tvUrgentBadge);
            tvStatusBadge = itemView.findViewById(R.id.tvStatusBadge);
            ivServiceIcon = itemView.findViewById(R.id.ivServiceIcon);
            flIconContainer = itemView.findViewById(R.id.flIconContainer);
            llReasonBox = itemView.findViewById(R.id.llReasonBox);
        }
    }
}