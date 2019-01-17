package com.framgia.bookStore.service;

import com.framgia.bookStore.activemq.Email;

public interface SendMailService {
    void send(Email email);
}
