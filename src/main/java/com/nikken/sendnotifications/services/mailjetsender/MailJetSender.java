package com.nikken.sendnotifications.services.mailjetsender;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Email;
import com.nikken.sendnotifications.services.MailSender;
import com.nikken.sendnotifications.services.enums.MailProperties;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

//@Primary
@Service
@Qualifier("${mail.sender.mail-jet}")
@Slf4j
public class MailJetSender implements MailSender {

    private MailjetClient client;
    private MailjetRequest request;
    private MailjetResponse response;

    @Value("${mail.apikey}")
    private String apiKey;

    @Value("${mail.secretKey}")
    private String secretKey;

    @Override
    public Boolean sendMail(String body, String toMail, String[] bccMail) {
        client = new MailjetClient(apiKey, secretKey);
        request = new MailjetRequest(Email.resource)
                .property(Email.FROMEMAIL, MailProperties.MANDRILL_MAIL_FROM.getValue())
                .property(Email.FROMNAME, MailProperties.FROM_NAME.getValue())
                .property(Email.SUBJECT, MailProperties.SUBJECT.getValue())
                .property(Email.HTMLPART, body)
                .property(Email.RECIPIENTS, new JSONArray()
                        .put(new JSONObject()
                                .put("Email", toMail)));
        try {
            response = client.post(request);
            log.info(response.getData().toString());
            log.info("<<<<<<<<<<<<<<<<<<MailJetSender>>>>>>>>>>>>>>>>>>>>>>>>");
        }catch (MailjetException e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @Override
    public Boolean sendMail(String body, String[] toMail) {
        return null;
    }
}
