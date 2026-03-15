package com.example.sahayak_ai;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logo extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Register button
        TextView tvSignIn = findViewById(R.id.Sign_in);
        if (tvSignIn != null) {
            tvSignIn.setOnClickListener(v -> {
                if (handler != null && runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                startActivity(new Intent(logo.this, ResisterActivity.class));
                finish();
            });
        }

        // ✅ Auto-navigate after 3 seconds — but check login first
        handler = new Handler(Looper.getMainLooper());
        runnable = () -> {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null) {
                // ✅ Already logged in → go directly to dashboard
                startActivity(new Intent(logo.this, dashboard.class));
            } else {

                startActivity(new Intent(logo.this, LoginActivity.class));
            }
            finish();
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}