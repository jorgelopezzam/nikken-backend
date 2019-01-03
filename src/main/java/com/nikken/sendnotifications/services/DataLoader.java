package com.nikken.sendnotifications.services;

public interface DataLoader {
    void dataLoader(String startDate, String endDate);
    Boolean generateDataToSend();
}
