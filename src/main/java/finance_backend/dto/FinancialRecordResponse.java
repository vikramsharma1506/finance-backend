package finance_backend.dto;

import finance_backend.entity.TransactionType;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class FinancialRecordResponse {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private String category;
    private LocalDate date;
    private String notes;
    private String createdBy;   // just the username, not the whole User object
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}