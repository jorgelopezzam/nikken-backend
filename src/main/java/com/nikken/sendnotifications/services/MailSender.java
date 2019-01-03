package com.nikken.sendnotifications.services;

public interface MailSender {
    Boolean sendMail(String body, String toMail, String[] bccEmail);
    Boolean sendMail(String body, String toMail[]);
}
