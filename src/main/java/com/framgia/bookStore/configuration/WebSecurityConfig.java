package com.framgia.bookStore.configuration;

import com.framgia.bookStore.auth.CustomAuthenticationFailureHandler;
import com.framgia.bookStore.auth.CustomAuthenticationSuccessHandler;
import com.framgia.bookStore.constants.AppConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.util.Assert;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final int BCRYPT_PASSWORD_ENCODER_STRENGTH_DEFAULT = 12;
    private static final String DEFAULT_SESSION_COOKIE_NAME = "JSESSIONID";
    private static final String SESSION_COOKIE_NAME = "SESSION";

    @Value("${book-store.security.remember-me.token-validity-seconds}")
    private int tokenValiditySeconds;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    private final Environment env;

    @Autowired
    public WebSecurityConfig(Environment env) {
        Assert.notNull(env, "env cannot be null");
        this.env = env;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers()
            .cacheControl().disable()
            .frameOptions().sameOrigin()
            .and().authorizeRequests()
                .antMatchers("/system/**").access("hasAnyRole('SYSTEM')")
                .antMatchers("/admin/**").access("hasAnyRole('ADMIN', 'SYSTEM')")
                .antMatchers("/user/**").access("hasAnyRole('ADMIN', 'SYSTEM', 'USER')")
                .antMatchers("/home").permitAll()
            .and().formLogin()
                .loginPage("/login").loginProcessingUrl("/j_spring_security_login")
                .usernameParameter(AppConst.USER_PARAMETER).passwordParameter(AppConst.PASSWORD_PARAMETER)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
            .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies(DEFAULT_SESSION_COOKIE_NAME, SESSION_COOKIE_NAME)
                .logoutSuccessUrl("/")
            .and().exceptionHandling()
            .and().rememberMe()
                .key(env.getProperty("security.remember-me.key"))
                .rememberMeCookieName("remember-me")
                .tokenValiditySeconds(tokenValiditySeconds);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/vendors/**", "/css/**", "/js/**", "/img/**", "/images/**", "/static/**", "/**/favicon.ico");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(
            env.getProperty("axas.security.password-encoder.bcrypt.strength", Integer.class,
                BCRYPT_PASSWORD_ENCODER_STRENGTH_DEFAULT));
    }

    @Bean
    public CookieSerializer cookieSerializer(
        @Value("${server.servlet.session.cookie.http-only}") Boolean useHttpOnlyCookie,
        @Value("${server.servlet.session.cookie.secure}") Boolean useSecureCookie) {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(SESSION_COOKIE_NAME);
        serializer.setCookiePath("/");
        serializer.setCookieMaxAge(-1);
        serializer.setUseHttpOnlyCookie(useHttpOnlyCookie);
        serializer.setUseSecureCookie(useSecureCookie);
        serializer.setUseBase64Encoding(true);
        return serializer;
    }

    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(4));
            }
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        };
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
