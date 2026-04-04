package finance_backend.controller;

import finance_backend.dto.ApiResponse;
import finance_backend.entity.Role;
import finance_backend.entity.User;
import finance_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Only ADMIN can view all users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(
                ApiResponse.success(userService.getAllUsers(), "Users fetched successfully"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.getUserById(id), "User fetched successfully"));
    }

    // Update user role — ADMIN only
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<User>> updateRole(
            @PathVariable Long id, @RequestParam Role role) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.updateUserRole(id, role), "Role updated"));
    }

    // Toggle active/inactive — ADMIN only
    @PutMapping("/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<User>> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.toggleUserStatus(id), "Status toggled"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null, "User deleted"));
    }
}