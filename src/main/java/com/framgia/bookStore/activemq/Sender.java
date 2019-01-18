package com.framgia.bookStore.activemq;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class Sender {

    private static final Logger LOGGER = LogManager.getLogger(Sender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(Email email) {
        jmsTemplate.convertAndSend("mail.q", email);
    }
}
