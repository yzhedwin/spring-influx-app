package com.spring_app.config;

import com.spring_app.db.Database;
import com.spring_app.db.config.InfluxDBConfiguration;
import com.spring_app.sub.MQTTSubscriberBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Listener implements Runnable {
    @Autowired
    MQTTSubscriberBase subscriber;

    @Override
    public void run() {
        while (true) {
            subscriber.subscribeMessage("MQTT/Message");
        }

    }
}