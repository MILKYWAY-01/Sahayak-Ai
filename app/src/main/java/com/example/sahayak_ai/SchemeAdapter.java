package com.example.sahayak_ai;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.ViewHolder> {

    private final List<SchemeModel> schemeList;
    private final OnSchemeClickListener listener;

    public interface OnSchemeClickListener {
        void onViewDetails(SchemeModel scheme);
    }

    public SchemeAdapter(List<SchemeModel> schemeList, OnSchemeClickListener listener) {
        this.schemeList = schemeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scheme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SchemeModel scheme = schemeList.get(position);

        holder.tvTitle.setText(scheme.getTitle());
        holder.tvDept.setText(scheme.getDepartment());
        holder.tvDesc.setText(scheme.getDescription());
        holder.tvNewBadge.setVisibility(scheme.isNew() ? View.VISIBLE : View.GONE);

        // Map icon
        holder.ivIcon.setImageResource(getIconDrawable(scheme.getIcon()));
        
        // Map Background Style
        int bgColor = getBgColor(scheme.getBgStyle(), holder.itemView);
        holder.cardView.setCardBackgroundColor(bgColor);

        holder.btnViewDetails.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewDetails(scheme);
            }
        });
    }

    private int getIconDrawable(String iconName) {
        switch (iconName != null ? iconName : "") {
            case "ic_school": return R.drawable.ic_school;
            case "ic_energy": return R.drawable.ic_energy;
            case "ic_elderly": return R.drawable.ic_person;
            case "ic_business": return R.drawable.ic_work;
            case "ic_health": return R.drawable.ic_child_care;
            case "ic_home": return R.drawable.ic_home;
            default: return R.drawable.ic_lightbulb;
        }
    }

    private int getBgColor(String style, View view) {
        switch (style != null ? style.toLowerCase() : "") {
            case "primary": return ContextCompat.getColor(view.getContext(), R.color.primary_light);
            case "green": return Color.parseColor("#DCFCE7");
            case "purple": return Color.parseColor("#F3E8FF");
            case "pink": return Color.parseColor("#FCE7F3");
            default: return Color.WHITE;
        }
    }

    @Override
    public int getItemCount() {
        return schemeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView ivIcon;
        TextView tvTitle, tvDept, tvDesc, tvNewBadge;
        MaterialButton btnViewDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cvScheme);
            ivIcon = itemView.findViewById(R.id.ivSchemeIcon);
            tvTitle = itemView.findViewById(R.id.tvSchemeTitle);
            tvDept = itemView.findViewById(R.id.tvSchemeDept);
            tvDesc = itemView.findViewById(R.id.tvSchemeDesc);
            tvNewBadge = itemView.findViewById(R.id.tvNewBadge);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}
