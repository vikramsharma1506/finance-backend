package finance_backend.service;

import finance_backend.dto.FinancialRecordResponse;
import finance_backend.dto.FinancialRecordRequest;
import finance_backend.entity.*;
import finance_backend.repository.FinancialRecordRepository;
import finance_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    public FinancialRecord createRecord(FinancialRecordRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FinancialRecord record = FinancialRecord.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .category(request.getCategory())
                .date(request.getDate())
                .notes(request.getNotes())
                .createdBy(user)
                .deleted(false)
                .build();

        return recordRepository.save(record);
    }

    public List<FinancialRecord> getAllRecords() {
        return recordRepository.findByDeletedFalse();
    }

    public FinancialRecord getRecordById(Long id) {
        return recordRepository.findById(id)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));
    }

    public List<FinancialRecord> filterRecords(String type, String category,
                                               LocalDate startDate, LocalDate endDate) {
        if (type != null) {
            return recordRepository.findByTypeAndDeletedFalse(TransactionType.valueOf(type.toUpperCase()));
        }
        if (category != null) {
            return recordRepository.findByCategoryAndDeletedFalse(category);
        }
        if (startDate != null && endDate != null) {
            return recordRepository.findByDateBetweenAndDeletedFalse(startDate, endDate);
        }
        return recordRepository.findByDeletedFalse();
    }

    public FinancialRecord updateRecord(Long id, FinancialRecordRequest request) {
        FinancialRecord record = getRecordById(id);
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());
        return recordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        FinancialRecord record = getRecordById(id);
        record.setDeleted(true); // soft delete
        recordRepository.save(record);
    }

    public List<FinancialRecord> getRecentActivity(int limit) {
        return recordRepository.findRecentActivity(PageRequest.of(0, limit));
    }
    // Add this helper method inside FinancialRecordService.java
    public FinancialRecordResponse toResponse(FinancialRecord record) {
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
