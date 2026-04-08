package db.jdbc;

import org.flywaydb.core.Flyway;

public class FlywayDatabaseMigrator implements DatabaseMigrator {

    private static final String LOCATION = "classpath:db/migration";

    private final ConnectionManager connectionManager;

    public FlywayDatabaseMigrator(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void migrate() {
        Flyway flyway = createFlyway();
        flyway.repair();
        flyway.migrate();
    }

    private Flyway createFlyway() {
        return Flyway.configure()
            .dataSource(connectionManager.getDataSource())
            .locations(LOCATION)
            .baselineOnMigrate(true)
            .baselineVersion("2")
            .load();
    }
}