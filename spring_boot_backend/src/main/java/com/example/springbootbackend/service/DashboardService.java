package com.example.springbootbackend.service;

import com.example.springbootbackend.api.dto.DashboardDtos;
import com.example.springbootbackend.domain.DefectSeverity;
import com.example.springbootbackend.repo.CorrectiveActionRepository;
import com.example.springbootbackend.repo.DefectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregated dashboard metrics service.
 */
@Service
public class DashboardService {

    private final DefectRepository defects;
    private final CorrectiveActionRepository actions;
    private final ActionService actionService;

    public DashboardService(DefectRepository defects, CorrectiveActionRepository actions, ActionService actionService) {
        this.defects = defects;
        this.actions = actions;
        this.actionService = actionService;
    }

    /**
     * PUBLIC_INTERFACE
     * Returns a severity distribution across all defects.
     */
    @Transactional(readOnly = true)
    public DashboardDtos.SeverityDistributionResponse severityDistribution() {
        Map<DefectSeverity, Long> counts = new EnumMap<>(DefectSeverity.class);
        for (DefectSeverity s : DefectSeverity.values()) {
            counts.put(s, defects.countBySeverity(s));
        }
        return new DashboardDtos.SeverityDistributionResponse(counts);
    }

    /**
     * PUBLIC_INTERFACE
     * Returns overdue corrective action count.
     */
    @Transactional(readOnly = true)
    public DashboardDtos.OverdueActionsResponse overdueActions() {
        return new DashboardDtos.OverdueActionsResponse(actionService.countOverdue());
    }

    /**
     * PUBLIC_INTERFACE
     * Returns a simple defect trend: last N days grouped by day boundary (UTC).
     * Uses naive counting by scanning createdAt in range (sufficient for basic dashboards).
     */
    @Transactional(readOnly = true)
    public DashboardDtos.DefectTrendResponse defectTrendLastDays(int days) {
        if (days <= 0 || days > 365) {
            throw new IllegalArgumentException("days must be between 1 and 365");
        }

        ZonedDateTime end = ZonedDateTime.now(ZoneOffset.UTC).toLocalDate().plusDays(1).atStartOfDay(ZoneOffset.UTC);
        ZonedDateTime start = end.minusDays(days);

        Instant startI = start.toInstant();
        Instant endI = end.toInstant();

        var inRange = defects.findByCreatedAtBetween(startI, endI);

        // Build day buckets
        long[] buckets = new long[days];
        for (var d : inRange) {
            int idx = (int) java.time.Duration.between(startI, d.getCreatedAt()).toDays();
            if (idx >= 0 && idx < days) {
                buckets[idx]++;
            }
        }

        List<DashboardDtos.TrendPoint> points = java.util.stream.IntStream.range(0, days)
                .mapToObj(i -> new DashboardDtos.TrendPoint(
                        start.plusDays(i).toLocalDate().toString(),
                        buckets[i]
                ))
                .toList();

        return new DashboardDtos.DefectTrendResponse(points);
    }
}
