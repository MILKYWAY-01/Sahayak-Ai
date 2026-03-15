package com.example.sahayak_ai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class dashboard extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private TextView tvTotalAppsCount, tvApprovedCount, tvRejectedCount, tvPendingCount;
    private TextView tvTotalAppsPercent, tvApprovedPercent, tvRejectedPercent, tvPendingPercent;
    private StatsViewModel statsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // Initialize TextViews
        tvTotalAppsCount = findViewById(R.id.tvTotalAppsCount);
        tvApprovedCount = findViewById(R.id.tvApprovedCount);
        tvRejectedCount = findViewById(R.id.tvRejectedCount);
        tvPendingCount = findViewById(R.id.tvPendingCount);

        tvTotalAppsPercent = findViewById(R.id.tvTotalAppsPercent);
        tvApprovedPercent = findViewById(R.id.tvApprovedPercent);
        tvRejectedPercent = findViewById(R.id.tvRejectedPercent);
        tvPendingPercent = findViewById(R.id.tvPendingPercent);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupServiceClickListeners();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_services) {
                startActivity(new Intent(dashboard.this, ServiceActivity.class));
                return true;
            } else if (itemId == R.id.nav_schemes) {
                startActivity(new Intent(dashboard.this, schemes.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(dashboard.this, ProfileActivity.class));
                return true;
            }
            return false;
        });

        TextView tvViewAllServices = findViewById(R.id.tvViewAllServices);
        if (tvViewAllServices != null) {
            tvViewAllServices.setOnClickListener(v -> {
                startActivity(new Intent(dashboard.this, ServiceActivity.class));
            });
        }

        // MVVM Integration: Observe Stats
        statsViewModel = new ViewModelProvider(this).get(StatsViewModel.class);
        statsViewModel.getStats().observe(this, this::updateStatsUI);
    }

    private void updateStatsUI(Map<String, Object> stats) {
        if (stats == null) return;

        // Update Counts
        tvTotalAppsCount.setText(String.valueOf(stats.getOrDefault("totalApps", 0)));
        tvApprovedCount.setText(String.valueOf(stats.getOrDefault("approved", 0)));
        tvPendingCount.setText(String.valueOf(stats.getOrDefault("pending", 0)));
        tvRejectedCount.setText(String.valueOf(stats.getOrDefault("rejected", 0)));

        // Update Badges
        tvTotalAppsPercent.setText(String.valueOf(stats.getOrDefault("totalAppsBadge", "+0%")));
        tvApprovedPercent.setText(String.valueOf(stats.getOrDefault("approvedBadge", "+0%")));
        tvPendingPercent.setText(String.valueOf(stats.getOrDefault("pendingBadge", "+0%")));
        tvRejectedPercent.setText(String.valueOf(stats.getOrDefault("rejectedBadge", "+0%")));
    }

    private void setupServiceClickListeners() {
        CardView cvIncome = findViewById(R.id.cvIncomeCertificate);
        CardView cvCaste = findViewById(R.id.cvCasteCertificate);
        CardView cvResidence = findViewById(R.id.cvResidenceCertificate);
        CardView cvBirth = findViewById(R.id.cvBirthCertificate);

        if (cvIncome != null) cvIncome.setOnClickListener(v -> openApplications("income_certificate"));
        if (cvCaste != null) cvCaste.setOnClickListener(v -> openApplications("caste_certificate"));
        if (cvResidence != null) cvResidence.setOnClickListener(v -> openApplications("residence_certificate"));
        if (cvBirth != null) cvBirth.setOnClickListener(v -> openApplications("birth_certificate"));
    }

    private void openApplications(String serviceType) {
        Intent intent = new Intent(dashboard.this, applications.class);
        intent.putExtra("SERVICE_TYPE", serviceType);
        startActivity(intent);
    }
}