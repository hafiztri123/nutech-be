package com.nutech.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {
    
    @Email(message = "Parameter email tidak valid")
    private String email;

    @Size(min = 8, message = "Parameter password minimal 8 karakter")
    private String password;

    @NotBlank(message = "Parameter first name tidak boleh kosong")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "Parameter last name tidak boleh kosong")
    @JsonProperty("last_name")
    private String lastName;

    public RegistrationRequest (){}

    public RegistrationRequest(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

