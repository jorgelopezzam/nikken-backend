package com.nikken.sendnotifications.services.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessIndicatorEnum {
    AUTOMATIC_PROCESS("AUTOMATICO"),

    MANUAL_PROCESS_ON("1"),
    MANUAL_PROCESS_OFF("0"),

    TEST_PROCESS_ON("1"),
    TEST_PROCESS_OFF("0"),

    NO_DATA_TO_SEND("[NO DATA TO SEND]"),
    NO_PROCESS_SCHEDULED("[NO PROCESS SCHEDULED]"),
    STATUS_OK("[Status OK]"),
    BUSY_PROCESS("EN EJECUCION"),
    PROCESS_FINISHED("0"),
    IN_PROCESS("1"),
    COMPLETED("TERMINADO"),
    MAIL_FORMED_EMAIL("ERROR_FORMATO_CORREO"),
    ESTAFETA_FORMAT_ERROR("ERROR_FORMATO_GUIA_ESTAFETA"),
    FAILED("FALLIDO");

    private String value;
}
