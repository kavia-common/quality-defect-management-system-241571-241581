package com.example.springbootbackend.api.dto;

import com.example.springbootbackend.domain.DefectSeverity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

public class DashboardDtos {

    @Schema(name = "SeverityDistributionResponse")
    public record SeverityDistributionResponse(
            Map<DefectSeverity, Long> counts
    ) {
    }

    @Schema(name = "OverdueActionsResponse")
    public record OverdueActionsResponse(
            long overdueCount
    ) {
    }

    @Schema(name = "TrendPoint")
    public record TrendPoint(
            String period,
            long count
    ) {
    }

    @Schema(name = "DefectTrendResponse")
    public record DefectTrendResponse(
            List<TrendPoint> points
    ) {
    }
}
