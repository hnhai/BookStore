package com.framgia.bookStore.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdatePassword {
    @NotNull
    private Long id;
    @Size(min=6)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
