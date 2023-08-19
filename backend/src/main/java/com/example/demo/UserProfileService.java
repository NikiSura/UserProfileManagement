package com.example.demo;
import java.util.List;

public interface UserProfileService {
    List<UserProfile> getAllProfiles();
    UserProfile getProfileById(Long id);
    UserProfile createProfile(UserProfile userProfile);
    UserProfile updateProfile(Long id, UserProfile userProfile);
    void deleteProfile(Long id);
}
