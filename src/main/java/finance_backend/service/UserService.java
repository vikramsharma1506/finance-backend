package finance_backend.service;

import finance_backend.entity.Role;
import finance_backend.entity.User;
import finance_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User updateUserRole(Long id, Role newRole) {
        User user = getUserById(id);
        user.setRole(newRole);
        return userRepository.save(user);
    }

    public User toggleUserStatus(Long id) {
        User user = getUserById(id);
        user.setActive(!user.isActive());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }
}