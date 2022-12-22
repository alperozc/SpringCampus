package com.copring.springcampus.dto;

import jakarta.validation.constraints.NotBlank;

public class RefreshRequest {

    @NotBlank
    private String oldToken;

    public RefreshRequest(String oldToken) {
        this.oldToken = oldToken;
    }

    public String getOldToken() {
        return oldToken;
    }

    public void setOldToken(String oldToken) {
        this.oldToken = oldToken;
    }

}
