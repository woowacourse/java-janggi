package persistence.datasource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;

public class DataSourceInitializer {

    private final DataSource dataSource;

    public DataSourceInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initialize() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = Files.readString(Path.of("src/main/resources/schema.sql"));
            List<String> statements = List.of(sql.split(";"));

            try (Statement statement = connection.createStatement()) {
                for (String each : statements) {
                    if (each.isBlank()) {
                        continue;
                    }
                    statement.execute(each);
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }
}
