package com.spring_app.pub;

public interface MQTTPublisherBase {


    public void publishMessage(String topic, String message);

    public void disconnect();

}