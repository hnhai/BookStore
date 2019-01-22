package com.framgia.bookStore.form;

import com.framgia.bookStore.constants.Gender;
import com.framgia.bookStore.constants.RoleType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Register {
    @NotNull
    private String username;

    @NotNull
    private String email;

    @Size(min=6)
    private String password;

    private Gender gender;

    @Size(min=6)
    private String fullname;

    private RoleType roleType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
