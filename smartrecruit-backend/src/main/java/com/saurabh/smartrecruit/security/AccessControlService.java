package com.saurabh.smartrecruit.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.saurabh.smartrecruit.entity.Role;
import com.saurabh.smartrecruit.entity.User;
import com.saurabh.smartrecruit.repository.UserRepository;

@Service
public class AccessControlService {

    private final UserRepository userRepository;

    public AccessControlService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Authentication required");
        }

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AccessDeniedException("Authenticated user not found"));
    }

    public void requireCandidateOwnership(Long ownerUserId) {

        User currentUser = getCurrentUser();

        if (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.HR) {
            return;
        }

        if (currentUser.getRole() != Role.CANDIDATE || !currentUser.getId().equals(ownerUserId)) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
    }
}
