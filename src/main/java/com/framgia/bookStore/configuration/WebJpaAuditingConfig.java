package com.framgia.bookStore.configuration;

import com.framgia.bookStore.auth.CustomUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class WebJpaAuditingConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebJpaAuditingConfig.class);
    private static final Long SYSTEM_USER = Long.valueOf(-1);

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<Long> {

        @Override
        public Optional<Long> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || (authentication instanceof AnonymousAuthenticationToken)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("SYSTEM_USER");
                }
                return Optional.of(SYSTEM_USER);
            }
            return Optional.of(((CustomUserDetail) authentication.getPrincipal()).getUser().getId());
        }
    }

}