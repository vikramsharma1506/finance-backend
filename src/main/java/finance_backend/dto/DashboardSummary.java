package finance_backend.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;
import java.util.List;

@Data
@Builder
public class DashboardSummary {
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netBalance;
    private Map<String, BigDecimal> categoryTotals;
    private List<Object> recentActivity;
}