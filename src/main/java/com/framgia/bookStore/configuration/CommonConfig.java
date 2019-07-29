package com.framgia.bookStore.configuration;

import com.framgia.bookStore.aspect.SystemExceptionHandlerAspect;
import com.framgia.bookStore.service.SendMailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public SystemExceptionHandlerAspect systemExceptionHandlerAspect(SendMailService emailService) {
        return new SystemExceptionHandlerAspect(emailService);
    }
}
