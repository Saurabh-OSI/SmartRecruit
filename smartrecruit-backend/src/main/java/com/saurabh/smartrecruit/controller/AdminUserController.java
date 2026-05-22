package com.saurabh.smartrecruit.controller;

import com.saurabh.smartrecruit.dto.AdminCreateUserRequest;
import com.saurabh.smartrecruit.dto.StaffUserResponse;
import com.saurabh.smartrecruit.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public List<StaffUserResponse> getStaffUsers() {

        return adminUserService.getStaffUsers();
    }

    @PostMapping
    public StaffUserResponse createStaffUser(@Valid @RequestBody AdminCreateUserRequest request) {

        return adminUserService.createStaffUser(request);
    }
}
