package com.example.sahayak_ai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
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

public class ServiceActivity extends AppCompatActivity {

    private RecyclerView rvServices;
    private ServiceAdapter serviceAdapter;
    private List<ServiceModel> serviceList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_services);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.appBarLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        FrameLayout flBack = findViewById(R.id.flBack);
        if (flBack != null) {
            flBack.setOnClickListener(v -> finish());
        }

        rvServices = findViewById(R.id.rvServices);
        rvServices.setLayoutManager(new LinearLayoutManager(this));
        serviceList = new ArrayList<>();
        
        serviceAdapter = new ServiceAdapter(serviceList, service -> {
            // Open applications activity with filter for this service
            Intent intent = new Intent(ServiceActivity.this, applications.class);
            intent.putExtra("SERVICE_TYPE", service.getKey());
            startActivity(intent);
        });
        rvServices.setAdapter(serviceAdapter);

        mDatabase = FirebaseDatabase.getInstance("https://sahayakai-56b58-default-rtdb.firebaseio.com/").getReference("services");

        fetchServices();
    }

    private void fetchServices() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ServiceModel model = dataSnapshot.getValue(ServiceModel.class);
                    if (model != null) {
                        model.setKey(dataSnapshot.getKey()); // Store the key (e.g., income_certificate)
                        serviceList.add(model);
                    }
                }
                serviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ServiceActivity", "Database error: " + error.getMessage());
                Toast.makeText(ServiceActivity.this, "Failed to load services.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
