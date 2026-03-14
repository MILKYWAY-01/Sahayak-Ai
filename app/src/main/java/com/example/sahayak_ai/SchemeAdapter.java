package com.example.sahayak_ai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.ViewHolder> {

    private List<SchemeModel> schemeList;

    public SchemeAdapter(List<SchemeModel> schemeList) {
        this.schemeList = schemeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scheme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SchemeModel model = schemeList.get(position);
        holder.tvSchemeTitle.setText(model.getTitle());
        holder.tvSchemeDescription.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return schemeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSchemeTitle, tvSchemeDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSchemeTitle = itemView.findViewById(R.id.tvSchemeTitle);
            tvSchemeDescription = itemView.findViewById(R.id.tvSchemeDescription);
        }
    }
}
