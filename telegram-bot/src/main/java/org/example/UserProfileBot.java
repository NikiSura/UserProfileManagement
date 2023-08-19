package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class UserProfileBot extends org.telegram.telegrambots.bots.TelegramLongPollingBot {

    @Override
    public String getBotToken() {
        return "6697653740:AAH6nVYTTcRkjnqKjfy9cqb2GX2lhzDHy54";
    }

    @Override
    public String getBotUsername() {
        return "NikiHSBot";
    }

    private Map<Long, String> userState = new HashMap<>(); // Declaration for userState
    private Map<Long, UserProfile> userData = new HashMap<>(); // Declaration for userData
    UserProfileClient uc = new UserProfileClient();



    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String currentState = userState.get(chatId);


            if (currentState != null) {
                if ("awaiting_username".equals(currentState)) {
                    // The user provided their username
                    String username = command;

                    // Prompt for email
                    sendReply(chatId, "Please provide your email:");
                    userState.put(chatId, "awaiting_email");
                    userData.put(chatId, new UserProfile(chatId, username, ""));
                } else if ("awaiting_email".equals(currentState)) {
                    // The user provided their email
                    String email = command;

                    // Get the stored username
                    UserProfile userProfile = userData.get(chatId);
                    userProfile.setEmail(email);

                    uc.createProfile(userProfile.getName(), userProfile.getEmail());

                    // Reset user state
                    userState.remove(chatId);
                    userData.remove(chatId);

                    sendReply(chatId, "Profile created successfully!");
                } else if ("awaiting_profileID".equals(currentState)) {
                    long profileId;
                    try {
                        profileId = Long.parseLong(command);
                    } catch (NumberFormatException e) {
                        sendReply(chatId, "Invalid profile ID format. Please provide a valid number.");
                        return;
                    }


                    // Call the method to retrieve a user profile by the profile ID
                    UserProfile userProfile = uc.getProfileById(profileId);

                    if (userProfile != null) {
                        String profileInfo = "Profile ID: " + userProfile.getId() + "\n" +
                                "Username: " + userProfile.getName() + "\n" +
                                "Email: " + userProfile.getEmail();
                        sendReply(chatId, profileInfo);
                    } else {
                        sendReply(chatId, "Profile not found.");
                    }
                    userState.remove(chatId);
                } else if("awaiting_profileID_for_update".equals(currentState)) {
                    long profileId;
                    try {
                        profileId = Long.parseLong(command);
                    } catch (NumberFormatException e) {
                        sendReply(chatId, "Invalid profile ID format. Please provide a valid number.");
                        return;
                    }
                    sendReply(chatId, "Please provide your new username:");
                    userState.put(chatId, "awaiting_new_username");
                    userData.put(chatId, uc.getProfileById(profileId));
                } else if("awaiting_new_username".equals(currentState)){
                    String newUsername = command;

                    // Retrieve stored user profile for updating
                    UserProfile userProfile = userData.get(chatId);
                    long profileId = userProfile.getId();

                    // Call the method to update the username
                    uc.updateProfile(profileId, newUsername);

                    // Reset user state
                    userState.remove(chatId);
                    userData.remove(chatId);

                    sendReply(chatId, "Username updated successfully!");

                    } else if("awaiting_profileID_for_delete".equals(currentState)){
                        long profileId;
                        try {
                            profileId = Long.parseLong(command);
                        } catch (NumberFormatException e) {
                            sendReply(chatId, "Invalid profile ID format. Please provide a valid number.");
                            return;
                        }

                        UserProfile userProfile = userData.get(profileId);
                        uc.deleteProfile(profileId);

                        userState.remove(chatId);
                        userData.remove(chatId);

                    sendReply(chatId, "Profile deleted successfully!");

                }
            }

                if ("/start".equals(command)) {
                    sendReply(chatId, "Hello! I'm your New bot. Type /help to see available commands.");
                } else if ("/help".equals(command)) {
                    sendReply(chatId, "Available commands:\n" +
                            "/getallprofiles - Get a list of all user profiles\n" +
                            "/getprofile {id} - Get a user profile by ID\n" +
                            "/createprofile - Create a new user profile\n" +
                            "/updateprofile {id} - Update an existing user profile\n" +
                            "/deleteprofile {id} - Delete a user profile");
                } else if ("/getallprofiles".equals(command)) {
                    String message = uc.getAllProfiles();
                    sendReply(chatId, message);
                } else if ("/getprofile".equals(command)) {
                    sendReply(chatId, "Please provide the profile id");
                    userState.put(chatId, "awaiting_profileID");

                } else if ("/createprofile".equals(command)) {
                    sendReply(chatId, "Please provide your username:");
                    userState.put(chatId, "awaiting_username");
                } else if (command.startsWith("/updateprofile")) {
                    sendReply(chatId, "Please provide the profile id");
                    userState.put(chatId, "awaiting_profileID_for_update");
                } else if (command.startsWith("/deleteprofile")) {
                    sendReply(chatId, "Please provide the profile id");
                    userState.put(chatId, "awaiting_profileID_for_delete");
                }
            }
        }

    private void sendReply(Long chatId, String message) {
        SendMessage reply = new SendMessage();
        reply.setText(message);
        reply.setChatId(String.valueOf(chatId));
        try {
            execute(reply);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
