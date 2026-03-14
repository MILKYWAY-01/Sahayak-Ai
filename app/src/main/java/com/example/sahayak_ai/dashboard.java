package com.example.sahayak_ai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dashboard extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private TextView tvTotalAppsCount, tvApprovedCount, tvRejectedCount, tvPendingCount;
    private TextView tvTotalAppsPercent, tvApprovedPercent, tvRejectedPercent, tvPendingPercent;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        
        // Initialize Firebase Database with explicit URL
        try {
            mDatabase = FirebaseDatabase.getInstance("https://sahayakai-56b58-default-rtdb.firebaseio.com/").getReference();
        } catch (Exception e) {
            Log.e(TAG, "Firebase initialization failed", e);
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_services) {
                startActivity(new Intent(dashboard.this, applications.class));
                return true;
            } else if (itemId == R.id.nav_schemes) {
                startActivity(new Intent(dashboard.this, schemes.class));
                return true;
            }
            return false;
        });

        fetchDashboardStats();
    }

    private void fetchDashboardStats() {
        Log.d(TAG, "Fetching stats from path: stats");
        mDatabase.child("stats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d(TAG, "Snapshot data: " + snapshot.getValue());
                    
                    // Update Counts
                    String total = snapshot.child("total_apps").getValue() != null ? 
                                   String.valueOf(snapshot.child("total_apps").getValue()) : "0";
                    String approved = snapshot.child("approved").getValue() != null ? 
                                      String.valueOf(snapshot.child("approved").getValue()) : "0";
                    String rejected = snapshot.child("rejected").getValue() != null ? 
                                      String.valueOf(snapshot.child("rejected").getValue()) : "0";
                    String pending = snapshot.child("pending").getValue() != null ? 
                                     String.valueOf(snapshot.child("pending").getValue()) : "0";

                    tvTotalAppsCount.setText(total);
                    tvApprovedCount.setText(approved);
                    tvRejectedCount.setText(rejected);
                    tvPendingCount.setText(pending);

                    // Update Percentages
                    if (snapshot.hasChild("total_percent")) {
                        tvTotalAppsPercent.setText(String.valueOf(snapshot.child("total_percent").getValue()));
                    }
                    if (snapshot.hasChild("approved_percent")) {
                        tvApprovedPercent.setText(String.valueOf(snapshot.child("approved_percent").getValue()));
                    }
                    if (snapshot.hasChild("rejected_percent")) {
                        tvRejectedPercent.setText(String.valueOf(snapshot.child("rejected_percent").getValue()));
                    }
                    if (snapshot.hasChild("pending_percent")) {
                        tvPendingPercent.setText(String.valueOf(snapshot.child("pending_percent").getValue()));
                    }
                } else {
                    Log.d(TAG, "Snapshot does not exist at path: stats");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(dashboard.this, "Failed to load stats: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}