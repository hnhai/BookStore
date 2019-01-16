package com.framgia.bookStore.activemq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver{

    private static final Logger LOGGER = LogManager.getLogger(Sender.class);
    @JmsListener(destination = "mail.q")
    public void receive(Email email) {
        System.out.println(email.getContent());
    }

}