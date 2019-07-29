package com.framgia.bookStore.aspect;

import com.framgia.bookStore.service.SendMailService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.orm.jpa.JpaSystemException;

import javax.jms.JMSException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aspect
public class SystemExceptionHandlerAspect {

    private final Set<Class<? extends Throwable>> classes;
    private final SendMailService sendMailService;

    public SystemExceptionHandlerAspect(SendMailService emailService) {
        this.sendMailService = emailService;
        this.classes = new HashSet<>();
        classes.add(JDBCConnectionException.class);
        classes.add(ConnectException.class);
        classes.add(SQLException.class);
        classes.add(GenericJDBCException.class);
        classes.add(OutOfMemoryError.class);
        classes.add(StackOverflowError.class);
        classes.add(JMSException.class);
        classes.add(JmsException.class);
        classes.add(JpaSystemException.class);
    }

    @Pointcut("within(@org.springframework.stereotype.Component *)")
    public void component() {

    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void service() {

    }

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {

    }

    @AfterThrowing(pointcut = "component() || service() || controller()", throwing = "t")
    public void handleException(JoinPoint joinPoint, Throwable t) {
        if (isSystemError(t)) {

            final Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
            logger.error(t.getMessage(), t);

            // TODO refactor
//            sendMailService.send();
        }
    }

    private boolean isSystemError(Throwable throwable) {
        List<Throwable> throwableList = ExceptionUtils.getThrowableList(throwable);
        for (Throwable t : throwableList) {
            if (classes.contains(t.getClass())) {
                return true;
            }
        }
        return false;
    }
}
