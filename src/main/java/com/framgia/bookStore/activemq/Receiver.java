package com.framgia.bookStore.activemq;

import com.framgia.bookStore.service.SendMailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver{

    @Autowired
    private SendMailService sendMailService;

    private static final Logger LOGGER = LogManager.getLogger(Sender.class);
    @JmsListener(destination = "mail.q")
    public void receive(Email email){
        sendMailService.send(email);
    }

}
