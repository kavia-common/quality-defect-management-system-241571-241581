package com.example.springbootbackend.api;

import com.example.springbootbackend.api.dto.DashboardDtos;
import com.example.springbootbackend.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // PUBLIC_INTERFACE
    @GetMapping("/severity-distribution")
    @Operation(summary = "Severity distribution", description = "Counts defects by severity.")
    public DashboardDtos.SeverityDistributionResponse severityDistribution() {
        return dashboardService.severityDistribution();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/overdue-actions")
    @Operation(summary = "Overdue actions", description = "Counts overdue corrective actions.")
    public DashboardDtos.OverdueActionsResponse overdueActions() {
        return dashboardService.overdueActions();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/defect-trend")
    @Operation(summary = "Defect trend", description = "Defect counts per day for last N days.")
    public DashboardDtos.DefectTrendResponse defectTrend(@RequestParam(defaultValue = "14") int days) {
        return dashboardService.defectTrendLastDays(days);
    }
}
