package com.spring_app.sub;

import com.spring_app.pub.MQTTPublisherBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MQTTSubscriberBase {

    public static final Logger logger = LoggerFactory.getLogger(MQTTPublisherBase.class);


    public void subscribeMessage(String topic);


    public void disconnect();
}