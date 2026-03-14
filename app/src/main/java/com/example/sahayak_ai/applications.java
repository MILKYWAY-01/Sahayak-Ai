package com.example.sahayak_ai;

import android.os.Bundle;
import android.util.Log;
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

    private RecyclerView rvApplications;
    private ApplicationAdapter applicationAdapter;
    private List<ApplicationModel> applicationList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_applications);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.appBarLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // Bottom padding not needed since it's a fixed header
            return insets;
        });

        // Initialize RecyclerView and list
        rvApplications = findViewById(R.id.rvApplications);
        rvApplications.setLayoutManager(new LinearLayoutManager(this));
        applicationList = new ArrayList<>();
        applicationAdapter = new ApplicationAdapter(applicationList);
        rvApplications.setAdapter(applicationAdapter);

        // Initialize Firebase Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sahayakai-56b58-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference("applications"); // assuming 'applications' is the node

        // Fetch data
        fetchApplications();
    }

    private void fetchApplications() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                applicationList.clear(); // Clear the list to avoid duplicate entries on update
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ApplicationModel model = dataSnapshot.getValue(ApplicationModel.class);
                    if (model != null) {
                        applicationList.add(model);
                    }
                }
                applicationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseDB", "Database error: " + error.getMessage());
                Toast.makeText(applications.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}