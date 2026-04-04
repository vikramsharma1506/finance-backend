package finance_backend.dto;

import finance_backend.entity.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FinancialRecordRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Type is required (INCOME or EXPENSE)")
    private TransactionType type;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private String notes;
}