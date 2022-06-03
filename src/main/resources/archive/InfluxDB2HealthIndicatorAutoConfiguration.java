package archive;

import java.util.Map;

import com.influxdb.client.InfluxDBClient;

import com.influxdb.spring.influx.InfluxDB2AutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(InfluxDBClient.class)
@ConditionalOnBean(InfluxDBClient.class)
@ConditionalOnEnabledHealthIndicator("influx")
@AutoConfigureAfter(InfluxDB2AutoConfiguration.class)
public class InfluxDB2HealthIndicatorAutoConfiguration
        extends CompositeHealthContributorConfiguration<InfluxDB2HealthIndicator, InfluxDBClient> {

    @Bean
    @ConditionalOnMissingBean(name = { "influxDB2HealthIndicator", "influxDB2HealthContributor" })
    public HealthContributor influxDbHealthContributor(final Map<String, InfluxDBClient> influxDBClients) {
        return createContributor(influxDBClients);
    }

}