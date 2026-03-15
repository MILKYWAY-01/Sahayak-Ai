package com.example.sahayak_ai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SchemeDetailBottomSheet extends BottomSheetDialogFragment {

    private String title, dept, desc, eligibility, benefit, docs, applyAt, deadline;

    public static SchemeDetailBottomSheet newInstance(SchemeModel scheme) {
        SchemeDetailBottomSheet fragment = new SchemeDetailBottomSheet();
        Bundle args = new Bundle();
        args.putString("title", scheme.getTitle());
        args.putString("dept", scheme.getDepartment());
        args.putString("desc", scheme.getDescription());
        args.putString("eligibility", scheme.getEligibility());
        args.putString("benefit", scheme.getBenefit());
        args.putString("docs", scheme.getDocuments());
        args.putString("applyAt", scheme.getApplyAt());
        args.putString("deadline", scheme.getDeadline());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            dept = getArguments().getString("dept");
            desc = getArguments().getString("desc");
            eligibility = getArguments().getString("eligibility");
            benefit = getArguments().getString("benefit");
            docs = getArguments().getString("docs");
            applyAt = getArguments().getString("applyAt");
            deadline = getArguments().getString("deadline");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_scheme_detail_bottom_sheet, container, false);

        ((TextView) v.findViewById(R.id.tvSheetTitle)).setText(title);
        ((TextView) v.findViewById(R.id.tvSheetDept)).setText(dept);
        ((TextView) v.findViewById(R.id.tvSheetDesc)).setText(desc);
        ((TextView) v.findViewById(R.id.tvSheetEligibility)).setText(eligibility);
        ((TextView) v.findViewById(R.id.tvSheetBenefit)).setText(benefit);
        ((TextView) v.findViewById(R.id.tvSheetDocuments)).setText(docs);
        ((TextView) v.findViewById(R.id.tvSheetApplyAt)).setText(applyAt);
        ((TextView) v.findViewById(R.id.tvSheetDeadline)).setText(deadline);

        v.findViewById(R.id.btnApplyNow).setOnClickListener(view -> {
            // Logic for application can be added here
            dismiss();
        });

        return v;
    }
}
