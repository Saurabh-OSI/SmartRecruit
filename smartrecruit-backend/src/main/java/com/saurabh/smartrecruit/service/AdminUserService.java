package com.saurabh.smartrecruit.service;

import com.saurabh.smartrecruit.dto.AdminCreateUserRequest;
import com.saurabh.smartrecruit.dto.StaffUserResponse;
import com.saurabh.smartrecruit.entity.Role;
import com.saurabh.smartrecruit.entity.User;
import com.saurabh.smartrecruit.exception.BadRequestException;
import com.saurabh.smartrecruit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<StaffUserResponse> getStaffUsers() {

        return userRepository.findAllByRoleInOrderByCreatedAtDesc(List.of(Role.ADMIN, Role.HR))
                .stream()
                .map(this::toStaffUserResponse)
                .toList();
    }

    public StaffUserResponse createStaffUser(AdminCreateUserRequest request) {

        if (request.getRole() == Role.CANDIDATE) {
            throw new BadRequestException("Admin user creation is limited to HR and ADMIN accounts");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return toStaffUserResponse(userRepository.save(user));
    }

    private StaffUserResponse toStaffUserResponse(User user) {

        return new StaffUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
