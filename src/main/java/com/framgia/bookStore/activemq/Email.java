package com.framgia.bookStore.activemq;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    private String to;
    private String from;
    private String personal;
    private String subject;
    private String content;
    private Map<String, Object> vars;
    private boolean html;
    private String template;
    private boolean multipart;

    public Email() {
        vars = new HashMap<>();;
        html = true;
        multipart = false;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isMultipart() {
        return multipart;
    }

    public void setMultipart(boolean multipart) {
        this.multipart = multipart;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public Map<String, Object> getVars() {
        return vars;
    }

    public void setVars(Map<String, Object> vars) {
        this.vars = vars;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}