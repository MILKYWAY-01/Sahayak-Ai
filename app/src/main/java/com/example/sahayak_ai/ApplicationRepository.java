package com.example.sahayak_ai;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplicationRepository {

    private final DatabaseReference mApplicationsRef;
    private final DatabaseReference mStatsRef;
    private final MutableLiveData<Map<String, Object>> statsLiveData;

    public ApplicationRepository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sahayakai-56b58-default-rtdb.firebaseio.com/");
        mApplicationsRef = database.getReference("applications");
        mStatsRef = database.getReference("stats/overview");
        statsLiveData = new MutableLiveData<>();
        
        startListeningToApplications();
    }

    private void startListeningToApplications() {
        mApplicationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ApplicationModel> applications = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ApplicationModel app = dataSnapshot.getValue(ApplicationModel.class);
                    if (app != null) {
                        applications.add(app);
                    }
                }
                
                // Calculate stats
                Map<String, Object> stats = StatsCalculator.calculate(applications);
                
                // Post to LiveData for UI
                statsLiveData.setValue(stats);
                
                // Update stats in Firebase for persistent storage
                mStatsRef.updateChildren(stats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    public MutableLiveData<Map<String, Object>> getStatsLiveData() {
        return statsLiveData;
    }
}
