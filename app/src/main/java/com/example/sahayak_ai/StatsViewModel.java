package com.example.sahayak_ai;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;

public class StatsViewModel extends ViewModel {

    private final ApplicationRepository repository;
    private final LiveData<Map<String, Object>> statsLiveData;

    public StatsViewModel() {
        repository = new ApplicationRepository();
        statsLiveData = repository.getStatsLiveData();
    }

    public LiveData<Map<String, Object>> getStats() {
        return statsLiveData;
    }
}
