package janggi.dto;

import java.util.Properties;

public record H2DBPropertiesDto(
    String driverClassName,
    String url,
    String id,
    String password
) {

    public static H2DBPropertiesDto of(final Properties properties) {
        return new H2DBPropertiesDto(
            properties.getProperty("h2-db.driver-class-name"),
            properties.getProperty("h2-db.datasource.url"),
            properties.getProperty("h2-db.datasource.id"),
            properties.getProperty("h2-db.datasource.password"));
    }
}
