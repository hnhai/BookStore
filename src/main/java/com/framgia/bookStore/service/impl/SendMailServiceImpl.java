package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.activemq.Email;
import com.framgia.bookStore.constants.MailConst;
import com.framgia.bookStore.service.SendMailService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class SendMailServiceImpl implements SendMailService {
    private static final Logger LOGGER = LogManager.getLogger(SendMailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("#{'${book-store.admin.mail}'.split(',')}")
    private List<String> adminMails;

    private final String from = "book-store@gmail.com";
    private final String setSubject = "System Error";

    @Override
    public void sendExceptionMail() {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        adminMails.forEach(mail -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
           try {
               mimeMessageHelper.setFrom(from);
               mimeMessageHelper.setSubject(setSubject);
               mimeMessageHelper.setTo(mail);
               mimeMessageHelper.setText("System Error");
               mailSender.send(mimeMessage);
           }catch (Exception e){
               LOGGER.error(e);
           }
        });
    }

    @Override
    public void send(Email email) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Context ctx = new Context();
        if (email.getType().equals(MailConst.ResetPassword.toString())){
            ctx = resetPassword(email);
        }else if(email.getType().equals(MailConst.CreatAccount.toString())){
            ctx = createAccount(email);
        }else if(email.getType().equals(MailConst.ConfirmOrder.toString())){
            ctx = confirmOrder(email);
        }
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            mimeMessageHelper.setFrom(email.getFrom());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setTo(email.getTo());
            String htmlContent = "";
            htmlContent = templateEngine.process(email.getTemplate(), ctx);
            mimeMessageHelper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        }catch (Exception e){
            LOGGER.error(e);
        }
    }

    private Context resetPassword(Email email){
        Context ctx = new Context();
        ctx.setVariable("url", email.getVars().get("url"));
        ctx.setVariable("username", email.getVars().get("user"));
        return ctx;
    }

    private Context createAccount(Email email){
        Context ctx = new Context();
        ctx.setVariable("baseUrl", email.getVars().get("baseUrl"));
        ctx.setVariable("username", email.getVars().get("username"));
        ctx.setVariable("password", email.getVars().get("password"));
        return ctx;
    }

    private Context confirmOrder(Email email){
        Context ctx = new Context();
        ctx.setVariable("orderUrl", email.getVars().get("orderUrl"));
        ctx.setVariable("total", email.getVars().get("total"));
        ctx.setVariable("payment", email.getVars().get("payment"));
        return ctx;
    }
}
