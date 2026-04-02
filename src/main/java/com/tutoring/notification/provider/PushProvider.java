package com.tutoring.notification.provider;

public interface PushProvider {

    void sendPush(String deviceToken, String message);

}