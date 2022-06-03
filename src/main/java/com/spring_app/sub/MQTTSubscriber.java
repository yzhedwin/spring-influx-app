package com.spring_app.sub;


import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.spring_app.config.MQTTConfig;
import com.spring_app.db.Database;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class MQTTSubscriber extends MQTTConfig implements MqttCallback, MQTTSubscriberBase {

    private String brokerUrl = null;
    final private String colon = ":";
    final private String clientId = "IDE";

    private MqttClient mqttClient = null;
    private MqttConnectOptions connectionOptions = null;
    private MemoryPersistence persistence = null;

    private static final Logger logger = LoggerFactory.getLogger(MQTTSubscriber.class);

    @Autowired
    Database db;

    public MQTTSubscriber() {
        this.config();
    }


    @Override
    public void connectionLost(Throwable cause) {
        logger.info("Connection Lost");

    }
    // Called when a message arrives from the server that matches any
    // subscription made by the client
    //TODO: Place msg into database
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        InfluxDBClient dbClient = db.initDB();
        String time = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println("Message Arrived at Time: " + time + "  Topic: " + topic + "  Message: "
                + new String(message.getPayload()));
        Point point = Point
                .measurement("intellij")
                .addTag("host", "host1")
                .addField("MQTT", new String(message.getPayload()))
                .time(Instant.now(), WritePrecision.NS);

        try (WriteApi writeApi = dbClient.getWriteApi()) {
            writeApi.writePoint(System.getenv("DB_BUCKET"), System.getenv("DB_ORG"), point);
        }
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Leave it blank for subscriber

    }


    @Override
    public void subscribeMessage(String topic) {
        try {
            this.mqttClient.subscribe(topic, this.qos);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            this.mqttClient.disconnect();
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }


    @Override
    protected void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {

        String protocol = this.TCP;
        if (ssl) {
            protocol = this.SSL;
        }

        this.brokerUrl = protocol + this.broker + colon + port;
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();

        try {
            this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            this.connectionOptions.setCleanSession(true);
            if (withUserNamePass) {
                this.connectionOptions.setPassword(this.password.toCharArray());
                this.connectionOptions.setUserName(this.userName);
            }
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException me) {
            me.printStackTrace();
        }

    }

    @Override
    protected void config() {

        this.brokerUrl = this.TCP + this.broker + colon + this.port;
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            this.connectionOptions.setCleanSession(true);
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException me) {
            me.printStackTrace();
        }

    }

}