package com.framgia.bookStore.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Profile {
    @NotNull
    private String fullName;

    @NotNull
    @Size(min = 20)
    private String address;

    @NotNull
    @Size(min = 8)
    private String phoneNumber;

    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
