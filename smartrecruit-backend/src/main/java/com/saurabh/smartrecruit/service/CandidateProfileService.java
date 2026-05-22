package com.saurabh.smartrecruit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.saurabh.smartrecruit.dto.CandidateProfileRequest;
import com.saurabh.smartrecruit.dto.CandidateProfileResponse;
import com.saurabh.smartrecruit.entity.CandidateProfile;
import com.saurabh.smartrecruit.entity.User;
import com.saurabh.smartrecruit.exception.BadRequestException;
import com.saurabh.smartrecruit.repository.CandidateProfileRepository;
import com.saurabh.smartrecruit.repository.UserRepository;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;

    public CandidateProfileService(CandidateProfileRepository candidateProfileRepository,
                                   UserRepository userRepository) {
        this.candidateProfileRepository = candidateProfileRepository;
        this.userRepository = userRepository;
    }

    public CandidateProfileResponse createProfile(CandidateProfileRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException("User not found with id: " + request.getUserId()));

        if (candidateProfileRepository.existsByUserId(request.getUserId())) {
            throw new BadRequestException("Candidate profile already exists for this user");
        }

        CandidateProfile profile = new CandidateProfile();
        profile.setUser(user);
        profile.setPhone(request.getPhone());
        profile.setEducation(request.getEducation());
        profile.setExperience(request.getExperience());
        profile.setSkills(request.getSkills());
        profile.setResumeUrl(request.getResumeUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());

        CandidateProfile savedProfile = candidateProfileRepository.save(profile);

        return mapToResponse(savedProfile);
    }

    public CandidateProfileResponse getProfileById(Long id) {

        CandidateProfile profile = candidateProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found with id: " + id));

        return mapToResponse(profile);
    }

    public CandidateProfileResponse getProfileByUserId(Long userId) {

        CandidateProfile profile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found for user id: " + userId));

        return mapToResponse(profile);
    }

    public List<CandidateProfileResponse> getAllProfiles() {

        return candidateProfileRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CandidateProfileResponse updateProfile(Long id, CandidateProfileRequest request) {

        CandidateProfile profile = candidateProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found with id: " + id));

        profile.setPhone(request.getPhone());
        profile.setEducation(request.getEducation());
        profile.setExperience(request.getExperience());
        profile.setSkills(request.getSkills());
        profile.setResumeUrl(request.getResumeUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());

        CandidateProfile updatedProfile = candidateProfileRepository.save(profile);

        return mapToResponse(updatedProfile);
    }

    public String deleteProfile(Long id) {

        CandidateProfile profile = candidateProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found with id: " + id));

        candidateProfileRepository.delete(profile);

        return "Candidate profile deleted successfully";
    }

    private CandidateProfileResponse mapToResponse(CandidateProfile profile) {

        User user = profile.getUser();

        return new CandidateProfileResponse(
                profile.getId(),
                user.getId(),
                user.getName(),
                user.getEmail(),
                profile.getPhone(),
                profile.getEducation(),
                profile.getExperience(),
                profile.getSkills(),
                profile.getResumeUrl(),
                profile.getLinkedinUrl(),
                profile.getGithubUrl(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}