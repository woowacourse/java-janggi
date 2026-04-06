package config;

import java.io.FileReader;
import java.sql.Connection;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.tools.RunScript;

public class DataSourceConfig {
    private static final String URL = "jdbc:h2:tcp://localhost/~/janggi";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "";
    private static final int CONNECTION_INITIAL_SIZE = 5;
    private static final int CONNECTION_MAX_TOTAL_SIZE = 5;

    private final DataSource dataSource;

    public DataSourceConfig() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(URL);
        basicDataSource.setUsername(USER_NAME);
        basicDataSource.setPassword(PASSWORD);
        basicDataSource.setInitialSize(CONNECTION_INITIAL_SIZE);
        basicDataSource.setMaxTotal(CONNECTION_MAX_TOTAL_SIZE);
        this.dataSource = basicDataSource;
        initializeSchema();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    private void initializeSchema() {
        try (Connection connection = dataSource.getConnection()) {
            RunScript.execute(connection, new FileReader("src/main/resources/init.sql"));
        } catch (Exception exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }
}
