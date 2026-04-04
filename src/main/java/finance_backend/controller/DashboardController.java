package finance_backend.controller;

import finance_backend.dto.*;
import finance_backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import finance_backend.dto.MonthlyTrend;


@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // ANALYST and ADMIN can access dashboard summary
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    public ResponseEntity<ApiResponse<DashboardSummary>> getSummary() {
        return ResponseEntity.ok(
                ApiResponse.success(dashboardService.getSummary(), "Dashboard summary fetched"));
    }
    // Add this inside DashboardController class
    @GetMapping("/trends")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<MonthlyTrend>>> getMonthlyTrends() {
        return ResponseEntity.ok(
                ApiResponse.success(dashboardService.getMonthlyTrends(), "Monthly trends fetched"));
    }
}