import org.flywaydb.core.Flyway;

public final class JanggiMigration {
    public static final String URL = "jdbc:mysql://localhost:13306/janggi";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    private final Flyway flyway;

    public JanggiMigration(String url, String userName, String password) {
        this.flyway = Flyway.configure()
                .dataSource(url, userName, password)
                .baselineOnMigrate(true)
                .load();
    }

    public void migrate() {
        flyway.migrate();
    }
}
