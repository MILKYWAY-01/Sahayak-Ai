package com.example.sahayak_ai;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

public class schemes extends AppCompatActivity {

    private static final String TAG = "SchemesActivity";
    private SchemeAdapter schemeAdapter;
    private List<SchemeModel> schemeList;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schemes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.appBarLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        
        // Initialize back button
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Initialize RecyclerView and list
        RecyclerView rvSchemes = findViewById(R.id.rvSchemes);
        rvSchemes.setLayoutManager(new LinearLayoutManager(this));
        schemeList = new ArrayList<>();
        
        schemeAdapter = new SchemeAdapter(schemeList, scheme -> {
            // Show detail bottom sheet using the scheme data
            SchemeDetailBottomSheet sheet = SchemeDetailBottomSheet.newInstance(scheme);
            sheet.show(getSupportFragmentManager(), "SchemeDetail");
        });
        
        rvSchemes.setAdapter(schemeAdapter);

        // Initialize Firebase Database reference for schemes node
        try {
            databaseReference = FirebaseDatabase.getInstance("https://sahayakai-56b58-default-rtdb.firebaseio.com/")
                    .getReference("schemes");
            fetchSchemes();
        } catch (Exception e) {
            Log.e(TAG, "Firebase Init Error", e);
            Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchSchemes() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                schemeList.clear(); 
                
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        // Use the custom factory method for robust data extraction
                        SchemeModel model = SchemeModel.fromSnapshot(dataSnapshot.getKey(), dataSnapshot);
                        if (model != null) {
                            schemeList.add(model);
                        }
                    }
                } else {
                    Log.d(TAG, "No schemes found in database.");
                }
                schemeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(schemes.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
