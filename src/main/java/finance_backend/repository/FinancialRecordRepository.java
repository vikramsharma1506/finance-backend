package finance_backend.repository;

import finance_backend.entity.FinancialRecord;
import finance_backend.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByDeletedFalse();

    List<FinancialRecord> findByTypeAndDeletedFalse(TransactionType type);

    List<FinancialRecord> findByCategoryAndDeletedFalse(String category);

    List<FinancialRecord> findByDateBetweenAndDeletedFalse(LocalDate start, LocalDate end);

    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type = :type AND f.deleted = false")
    BigDecimal sumByType(@Param("type") TransactionType type);

    @Query("SELECT f.category, SUM(f.amount) FROM FinancialRecord f WHERE f.deleted = false GROUP BY f.category")
    List<Object[]> sumByCategory();

    @Query("SELECT f FROM FinancialRecord f WHERE f.deleted = false ORDER BY f.createdAt DESC")
    List<FinancialRecord> findRecentActivity(org.springframework.data.domain.Pageable pageable);


    @Query("SELECT YEAR(f.date), MONTH(f.date), f.type, SUM(f.amount) " +
            "FROM FinancialRecord f WHERE f.deleted = false " +
            "GROUP BY YEAR(f.date), MONTH(f.date), f.type " +
            "ORDER BY YEAR(f.date) DESC, MONTH(f.date) DESC")
    List<Object[]> getMonthlyTrends();


}
