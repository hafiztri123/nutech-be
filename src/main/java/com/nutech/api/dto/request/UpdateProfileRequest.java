package com.nutech.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class UpdateProfileRequest {

    @JsonProperty("first_name")
    @NotBlank(message = "Parameter first name tidak boleh kosong")
    private String firstName;
    
    @JsonProperty("last_name")
    @NotBlank(message = "Parameter last name tidak boleh kosong")
    private String lastName;

    public UpdateProfileRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
}
