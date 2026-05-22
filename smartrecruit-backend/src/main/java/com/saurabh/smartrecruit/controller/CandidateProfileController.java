package com.saurabh.smartrecruit.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saurabh.smartrecruit.dto.CandidateProfileRequest;
import com.saurabh.smartrecruit.dto.CandidateProfileResponse;
import com.saurabh.smartrecruit.service.CandidateProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "*")
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;

    public CandidateProfileController(CandidateProfileService candidateProfileService) {
        this.candidateProfileService = candidateProfileService;
    }

    @PostMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public CandidateProfileResponse createProfile(@Valid @RequestBody CandidateProfileRequest request) {
        return candidateProfileService.createProfile(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<CandidateProfileResponse> getAllProfiles() {
        return candidateProfileService.getAllProfiles();
    }

    @GetMapping("/profile/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','CANDIDATE')")
    public CandidateProfileResponse getProfileById(@PathVariable Long id) {
        return candidateProfileService.getProfileById(id);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','CANDIDATE')")
    public CandidateProfileResponse getProfileByUserId(@PathVariable Long userId) {
        return candidateProfileService.getProfileByUserId(userId);
    }

    @PutMapping("/profile/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    public CandidateProfileResponse updateProfile(@PathVariable Long id,
                                                  @Valid @RequestBody CandidateProfileRequest request) {
        return candidateProfileService.updateProfile(id, request);
    }

    @DeleteMapping("/profile/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','CANDIDATE')")
    public String deleteProfile(@PathVariable Long id) {
        return candidateProfileService.deleteProfile(id);
    }
}