package com.example.sahayak_ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsCalculator {

    public static Map<String, Object> calculate(List<ApplicationModel> applications) {
        int approved = 0;
        int pending = 0;
        int rejected = 0;

        for (ApplicationModel app : applications) {
            String status = app.getStatus() != null ? app.getStatus().toLowerCase() : "";
            switch (status) {
                case "approved":
                    approved++;
                    break;
                case "pending":
                    pending++;
                    break;
                case "rejected":
                    rejected++;
                    break;
            }
        }

        int totalApps = approved + pending + rejected;

        Map<String, Object> stats = new HashMap<>();
        stats.put("approved", approved);
        stats.put("pending", pending);
        stats.put("rejected", rejected);
        stats.put("totalApps", totalApps);
        
        // Example logic for badges (can be expanded based on history if needed)
        stats.put("approvedBadge", "+0%");
        stats.put("pendingBadge", "+0%");
        stats.put("rejectedBadge", "+0%");
        stats.put("totalAppsBadge", "+0%");

        return stats;
    }
}
