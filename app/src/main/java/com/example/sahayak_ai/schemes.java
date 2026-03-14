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

public class schemes extends AppCompatActivity {

    private RecyclerView rvSchemes;
    private SchemeAdapter schemeAdapter;
    private List<SchemeModel> schemeList;
    private DatabaseReference databaseReference;

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

        // Initialize back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Initialize RecyclerView and list
        rvSchemes = findViewById(R.id.rvSchemes);
        rvSchemes.setLayoutManager(new LinearLayoutManager(this));
        schemeList = new ArrayList<>();
        schemeAdapter = new SchemeAdapter(schemeList);
        rvSchemes.setAdapter(schemeAdapter);

        // Initialize Firebase Database reference for schemes node
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sahayakai-56b58-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference("schemes");

        // Fetch data
        fetchSchemes();
    }

    private void fetchSchemes() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                schemeList.clear(); // Clear the list to avoid duplicate entries on update
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SchemeModel model = dataSnapshot.getValue(SchemeModel.class);
                    if (model != null) {
                        schemeList.add(model);
                    }
                }
                schemeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseDB", "Database error: " + error.getMessage());
                Toast.makeText(schemes.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
