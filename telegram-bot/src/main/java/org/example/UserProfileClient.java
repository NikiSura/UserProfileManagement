package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserProfileClient {

    private static final String BASE_URL = "http://localhost:9090"; // Update with your server URL

    public static void main(String[] args) {
        UserProfileClient userProfileClient = new UserProfileClient();
        //userProfileClient.getAllProfiles();
        //userProfileClient.getProfileById(1); // Replace with an existing profile ID
        userProfileClient.createProfile("New User99", "newuser@example.com");
        //userProfileClient.updateProfile(2, "Updated User"); // Replace with an existing profile ID
        //userProfileClient.deleteProfile(3); // Replace with an existing profile ID
    }

    public String getAllProfiles() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserProfile[]> response = restTemplate.getForEntity(BASE_URL + "/api/getAllProfiles", UserProfile[].class);
        UserProfile[] profiles = response.getBody();
        String responseStr = "";
        for (UserProfile profile : profiles) {
            responseStr += "Profile ID: " + profile.getId() + ", Name: " + profile.getName() + ", Email: " + profile.getEmail() +"\n";
        }
        return responseStr;
    }

    public UserProfile getProfileById(long profileId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserProfile> response = restTemplate.getForEntity(BASE_URL + "/api/getProfile/" + profileId, UserProfile.class);
        UserProfile profile = response.getBody();
        UserProfile myProfile = new UserProfile(profileId, profile.getName(), profile.getEmail());
        return profile;
        //return "Profile by ID: " + profile.getName() + ", Email: " + profile.getEmail();
        //System.out.println("Profile by ID: " + profile.getName() + ", Email: " + profile.getEmail());
    }

    public void createProfile(String name, String email) {
        RestTemplate restTemplate = new RestTemplate();
        UserProfile newProfile = new UserProfile();
        newProfile.setName(name);
        newProfile.setEmail(email);
        ResponseEntity<UserProfile> response = restTemplate.postForEntity(BASE_URL + "/api/createProfile", newProfile, UserProfile.class);
        UserProfile createdProfile = response.getBody();
        System.out.println("Created Profile: " + createdProfile.getName() + ", Email: " + createdProfile.getEmail());
    }

    public void updateProfile(long profileId, String newName) {
        RestTemplate restTemplate = new RestTemplate();
        String updateUrl = BASE_URL + "/api/updateProfile/" + profileId;
        UserProfile profileToUpdate = getProfileById(profileId); // Retrieve the current profile
        profileToUpdate.setName(newName); // Update the username
        restTemplate.put(updateUrl, profileToUpdate);
    }

    public void deleteProfile(long profileId) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(BASE_URL + "/api/deleteProfile/" + profileId);
    }
}


