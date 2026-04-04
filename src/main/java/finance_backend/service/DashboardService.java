package finance_backend.service;

import finance_backend.dto.MonthlyTrend;
import finance_backend.dto.DashboardSummary;
import finance_backend.dto.FinancialRecordResponse;
import finance_backend.entity.TransactionType;
import finance_backend.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialRecordRepository recordRepository;
    private final FinancialRecordService recordService;  // ADD THIS

    public DashboardSummary getSummary() {
        BigDecimal totalIncome = recordRepository.sumByType(TransactionType.INCOME);
        BigDecimal totalExpenses = recordRepository.sumByType(TransactionType.EXPENSE);

        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpenses == null) totalExpenses = BigDecimal.ZERO;

        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        // Category totals
        List<Object[]> categoryData = recordRepository.sumByCategory();
        Map<String, BigDecimal> categoryTotals = new LinkedHashMap<>();
        for (Object[] row : categoryData) {
            categoryTotals.put((String) row[0], (BigDecimal) row[1]);
        }

        // Recent activity — use FinancialRecordResponse to avoid serialization issues
        List<FinancialRecordResponse> recentActivity = recordRepository
                .findRecentActivity(PageRequest.of(0, 5))
                .stream()
                .map(recordService::toResponse)   // converts to safe DTO
                .collect(Collectors.toList());

        return DashboardSummary.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(netBalance)
                .categoryTotals(categoryTotals)
                .recentActivity(new ArrayList<>(recentActivity))
                .build();
    }
    public List<MonthlyTrend> getMonthlyTrends() {
        return recordRepository.getMonthlyTrends().stream()
                .map(row -> new MonthlyTrend(
                        ((Number) row[0]).intValue(),
                        ((Number) row[1]).intValue(),
                        row[2].toString(),
                        (java.math.BigDecimal) row[3]
                ))
                .collect(java.util.stream.Collectors.toList());
    }
}