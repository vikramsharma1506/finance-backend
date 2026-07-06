package finance_backend.service;

import finance_backend.dto.DashboardSummary;
import finance_backend.dto.FinancialRecordResponse;
import finance_backend.dto.MonthlyTrend;
import finance_backend.entity.FinancialRecord;
import finance_backend.entity.TransactionType;
import finance_backend.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialRecordRepository recordRepository;
    // FinancialRecordService REMOVED — no longer needed here

    @Transactional(readOnly = true)
    public DashboardSummary getSummary() {

        // Total income and expenses
        BigDecimal totalIncome = recordRepository
                .sumByType(TransactionType.INCOME);
        BigDecimal totalExpenses = recordRepository
                .sumByType(TransactionType.EXPENSE);

        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpenses == null) totalExpenses = BigDecimal.ZERO;

        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        // Category totals
        List<Object[]> categoryData = recordRepository.sumByCategory();
        Map<String, BigDecimal> categoryTotals = new LinkedHashMap<>();
        for (Object[] row : categoryData) {
            categoryTotals.put(
                    (String) row[0],
                    (BigDecimal) row[1]);
        }

        // Recent activity — mapped directly here
        // no need to call FinancialRecordService
        List<FinancialRecordResponse> recentActivity =
                recordRepository
                        .findRecentActivity(PageRequest.of(0, 5))
                        .stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList());

        return DashboardSummary.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(netBalance)
                .categoryTotals(categoryTotals)
                .recentActivity(new ArrayList<>(recentActivity))
                .build();
    }

    @Transactional(readOnly = true)
    public List<MonthlyTrend> getMonthlyTrends() {
        return recordRepository.getMonthlyTrends()
                .stream()
                .map(row -> new MonthlyTrend(
                        ((Number) row[0]).intValue(),
                        ((Number) row[1]).intValue(),
                        row[2].toString(),
                        (BigDecimal) row[3]))
                .collect(Collectors.toList());
    }

    // Moved toResponse() here from FinancialRecordService
    // so DashboardService doesn't depend on FinancialRecordService
    private FinancialRecordResponse toResponse(FinancialRecord record) {
        return FinancialRecordResponse.builder()
                .id(record.getId())
                .amount(record.getAmount())
                .type(record.getType())
                .category(record.getCategory())
                .date(record.getDate())
                .notes(record.getNotes())
                .createdBy(record.getCreatedBy().getUsername())
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .build();
    }
}