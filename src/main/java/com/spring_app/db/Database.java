package com.spring_app.db;

import com.influxdb.client.InfluxDBClient;
import com.spring_app.db.config.InfluxDBConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Database {

    @Autowired
    InfluxDBConfiguration config;

    @Bean
    public InfluxDBClient initDB() {
        System.out.println("InfluxDB IS CONNECTED: " + config.client().health());
        return config.client();
    }
}

