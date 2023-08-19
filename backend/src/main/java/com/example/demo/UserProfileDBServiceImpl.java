package com.example.demo;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileDBServiceImpl implements UserProfileService {
    private final String DATABASE_URL = "jdbc:sqlite:/Users/nikisura/Desktop/database.db";

    @Override
    public List<UserProfile> getAllProfiles() {
        List<UserProfile> profiles = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM user_profiles")) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                UserProfile profile = new UserProfile(id, name, email);
                profiles.add(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profiles;
    }

    @Override
    public UserProfile getProfileById(Long id) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_profiles WHERE id = ? ")) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                return new UserProfile(id, name, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public UserProfile createProfile(UserProfile userProfile) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO user_profiles (name, email) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, userProfile.getName());
            statement.setString(2, userProfile.getEmail());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long newId = generatedKeys.getLong(1);
                userProfile.setId(newId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userProfile;
    }

    @Override
    public UserProfile updateProfile(Long id, UserProfile userProfile) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("UPDATE user_profiles SET name = ?, email = ? WHERE id = ?")) {

            statement.setString(1, userProfile.getName());
            statement.setString(2, userProfile.getEmail());
            statement.setLong(3, id);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                userProfile.setId(id);
                return userProfile;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteProfile(Long id) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM user_profiles WHERE id = ?")) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

