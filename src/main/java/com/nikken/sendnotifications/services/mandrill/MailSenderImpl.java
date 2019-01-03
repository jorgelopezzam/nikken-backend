package com.nikken.sendnotifications.services.mandrill;

import javax.mail.internet.MimeMessage;

import com.nikken.sendnotifications.services.MailSender;
import com.nikken.sendnotifications.services.enums.MailProperties;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

@Primary
@Service
@Qualifier("${mail.sender.mandrill}")
@Slf4j
public class MailSenderImpl implements MailSender {

    private JavaMailSender mandrillMailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender mandrillMailSender) {
        this.mandrillMailSender = mandrillMailSender;
    }

    @Override
    public Boolean sendMail(String body, String toMail, String[] bccMail) {

        try{
            MimeMessage message = mandrillMailSender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(MailProperties.SUBJECT.getValue());
            helper.setFrom(MailProperties.MANDRILL_MAIL_FROM.getValue());
            helper.setTo(toMail);
            helper.setBcc(bccMail);

            // use the true flag to indicate the text included is HTML
            helper.setText(body, true);
            mandrillMailSender.send(message);
            log.info("<<<<<<<<<<<<<<<<<<MandrillMailSender>>>>>>>>>>>>>>>>>>>>>>>>");
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean sendMail(String body, String toMail[]) {

        try{
            MimeMessage message = mandrillMailSender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(MailProperties.SUBJECT.getValue());
            helper.setFrom(MailProperties.MANDRILL_MAIL_FROM.getValue());
            helper.setTo(toMail);

            // use the true flag to indicate the text included is HTML
            helper.setText(body, true);
            mandrillMailSender.send(message);
            log.info("<<<<<<<<<<<<<<<<<<MandrillMailSender>>>>>>>>>>>>>>>>>>>>>>>>");
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
