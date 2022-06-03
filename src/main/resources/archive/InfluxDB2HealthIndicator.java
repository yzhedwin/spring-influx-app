package archive;

import com.influxdb.client.InfluxDBClient;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;

import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * {@link HealthIndicator} for InfluxDB 2.
 *
 * @author Jakub Bednar (bednar@github)
 */
public class InfluxDB2HealthIndicator extends AbstractHealthIndicator {

    private final InfluxDBClient influxDBClient;

    public InfluxDB2HealthIndicator(final InfluxDBClient influxDBClient) {
        super("InfluxDBClient 2 health check failed");
        Assert.notNull(influxDBClient, "InfluxDBClient must not be null");

        this.influxDBClient = influxDBClient;
    }

    @Override
    protected void doHealthCheck(final Health.Builder builder) {
        boolean success = this.influxDBClient.ping();

        if (success) {
            builder.up();
        } else {
            builder.down();
        }
    }
}

