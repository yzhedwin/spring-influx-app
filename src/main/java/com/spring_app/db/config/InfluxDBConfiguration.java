package com.spring_app.db.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfiguration {
    private final String url = System.getenv("DB_URL");
    private final String org = System.getenv("DB_ORG");
    private final String username = System.getenv("DB_USERNAME");
    private final String pass = System.getenv("DB_PASS");
    private final String token = System.getenv("DB_TOKEN");
    private final String bucket = System.getenv("DB_BUCKET");

    private static final Logger logger = LoggerFactory.getLogger("InfluxDBClient");

    @Bean
    public InfluxDBClient client() {
        logger.info("Connecting to: {}, token: {}, org: {}, bucketId: {}",
                this.url, this.token, this.org, this.bucket
        );
        return InfluxDBClientFactory.create(
                this.url, this.token.toCharArray(), this.org, this.bucket
        );
    }
}
