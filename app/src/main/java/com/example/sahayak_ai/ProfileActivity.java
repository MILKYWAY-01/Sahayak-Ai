package com.example.sahayak_ai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private TextView tvProfileName, tvCscId, tvEmail;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        
        // Initialize Firebase Database with explicit URL
        try {
            mDatabase = FirebaseDatabase.getInstance("https://sahayakai-56b58-default-rtdb.firebaseio.com/").getReference();
        } catch (Exception e) {
            Log.e(TAG, "Firebase initialization failed", e);
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvProfileName = findViewById(R.id.tvProfileName);
        tvCscId = findViewById(R.id.tvCscId);
        tvEmail = findViewById(R.id.tvEmail);
        FrameLayout flBack = findViewById(R.id.flBack);

        if (flBack != null) {
            flBack.setOnClickListener(v -> finish());
        }

        // Functional Sign Out Button
        findViewById(R.id.btnSignOut).setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show();
            
            // Redirect to LoginActivity and clear activity stack
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        loadUserProfile();
    }

    private void loadUserProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            Log.d(TAG, "Loading profile for UID: " + uid);
            
            mDatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Log.d(TAG, "User data: " + snapshot.getValue());
                        
                        String name = String.valueOf(snapshot.child("name").getValue());
                        String cscId = String.valueOf(snapshot.child("cscId").getValue());
                        String email = String.valueOf(snapshot.child("email").getValue());

                        tvProfileName.setText(name.equals("null") ? "User" : name);
                        tvCscId.setText("cscid : " + (cscId.equals("null") ? "N/A" : cscId));
                        tvEmail.setText("Email : " + (email.equals("null") ? user.getEmail() : email));
                    } else {
                        Log.d(TAG, "No data found for user in database.");
                        tvProfileName.setText("User");
                        tvCscId.setText("cscid : N/A");
                        tvEmail.setText("Email : " + user.getEmail());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Database error: " + error.getMessage());
                    Toast.makeText(ProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d(TAG, "No user is currently signed in.");
            finish();
        }
    }
}
