package com.nervelife.soma.riyadhmetronetworkserver.domain.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserModel {

    @NotEmpty
	@NotNull
    private String username;
    
    private String password;
    
    private String role;
    
    private String fullName;
    
    private String gender;

    //======================== GETTERS/SETTERS ========================//

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
}
