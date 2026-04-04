package finance_backend.controller;

import finance_backend.dto.*;
import finance_backend.entity.FinancialRecord;
import finance_backend.service.FinancialRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService recordService;

    @GetMapping
    @PreAuthorize("hasAnyRole('VIEWER', 'ANALYST', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<FinancialRecordResponse>>> getAllRecords(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<FinancialRecordResponse> records = recordService
                .filterRecords(type, category, startDate, endDate)
                .stream()
                .map(recordService::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(records, "Records fetched successfully"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('VIEWER', 'ANALYST', 'ADMIN')")
    public ResponseEntity<ApiResponse<FinancialRecordResponse>> getRecordById(@PathVariable Long id) {
        FinancialRecordResponse response = recordService.toResponse(recordService.getRecordById(id));
        return ResponseEntity.ok(ApiResponse.success(response, "Record fetched successfully"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FinancialRecordResponse>> createRecord(
            @Valid @RequestBody FinancialRecordRequest request,
            Authentication auth) {
        FinancialRecord record = recordService.createRecord(request, auth.getName());
        return ResponseEntity.ok(ApiResponse.success(recordService.toResponse(record), "Record created successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FinancialRecordResponse>> updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody FinancialRecordRequest request) {
        FinancialRecord updated = recordService.updateRecord(id, request);
        return ResponseEntity.ok(ApiResponse.success(recordService.toResponse(updated), "Record updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Record deleted successfully"));
    }
}