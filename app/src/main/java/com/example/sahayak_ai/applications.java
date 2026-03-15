package com.example.sahayak_ai;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class applications extends AppCompatActivity {

    private ApplicationAdapter applicationAdapter;
    private List<ApplicationModel> applicationList;
    private DatabaseReference databaseReference;
    private String filterServiceType;
    private TextView tvHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_applications);

        // Get filter from intent if opened from dashboard
        filterServiceType = getIntent().getStringExtra("SERVICE_TYPE");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.appBarLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Setup Back Button
        View flBack = findViewById(R.id.flBack);
        if (flBack != null) {
            flBack.setOnClickListener(v -> finish());
        }

        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        if (filterServiceType != null && tvHeaderTitle != null) {
            // Set header title based on selected service
            String title = filterServiceType.replace("_", " ");
            title = title.substring(0, 1).toUpperCase() + title.substring(1);
            tvHeaderTitle.setText(title);
        }

        // Initialize RecyclerView and list
        RecyclerView rvApplications = findViewById(R.id.rvApplications);
        rvApplications.setLayoutManager(new LinearLayoutManager(this));
        applicationList = new ArrayList<>();
        applicationAdapter = new ApplicationAdapter(applicationList);
        rvApplications.setAdapter(applicationAdapter);

        // Initialize Firebase Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sahayakai-56b58-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference("applications");

        // Fetch data
        fetchApplications();
    }

    private void fetchApplications() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                applicationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ApplicationModel model = dataSnapshot.getValue(ApplicationModel.class);
                    if (model != null) {
                        // If a filter is active, only show applications of that type
                        if (filterServiceType == null || filterServiceType.equals(model.getServiceType())) {
                            applicationList.add(model);
                        }
                    }
                }
                applicationAdapter.notifyDataSetChanged();
                
                if (applicationList.isEmpty()) {
                    Toast.makeText(applications.this, "No applications found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseDB", "Database error: " + error.getMessage());
                Toast.makeText(applications.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}