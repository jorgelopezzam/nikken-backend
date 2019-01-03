package com.nikken.sendnotifications.services.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MailProperties {

    SUBJECT("Nikken E-mail"),
    FROM_NAME("Confirmación de envío Nikken"),
    MANDRILL_MAIL_FROM("servicio.mex@nikkenlatam.com");
    private String value;
}
