package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.web.servlet.ServletComponentScan;


import java.util.List;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/api/getAllProfiles")
    public List<UserProfile> getAllProfiles() {
        return userProfileService.getAllProfiles();
    }

    @GetMapping("/api/getProfile/{id}")
    public UserProfile getProfileById(@PathVariable Long id) {
        return userProfileService.getProfileById(id);
    }

    @PostMapping("/api/createProfile")
    public UserProfile createProfile(@RequestBody UserProfile userProfile) {
        return userProfileService.createProfile(userProfile);
    }

    @PutMapping("/api/updateProfile/{id}")
    public UserProfile updateProfile(@PathVariable Long id, @RequestBody UserProfile userProfile) {
        return userProfileService.updateProfile(id, userProfile);
    }

    @DeleteMapping("/api/deleteProfile/{id}")
    public void deleteProfile(@PathVariable Long id) {
        userProfileService.deleteProfile(id);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserProfileController.class, args);
    }
}

