package org.backend.core.user.application.dto;

import java.util.UUID;

public class GetUsersDto {
    private String input = "";

    private UUID userID;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }
}
