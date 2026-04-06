package config;

import java.io.FileReader;
import java.sql.Connection;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.tools.RunScript;

public class DataSourceConfig {
    private static final String CLASS_NAME = "org.h2.Driver";
    private static final String URL = "jdbc:h2:~/janggi;AUTO_SERVER=TRUE;IFEXISTS=FALSE";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "";
    private static final String INIT_FILE_PATH = "src/main/resources/init.sql";

    private static final int CONNECTION_INITIAL_SIZE = 5;
    private static final int CONNECTION_MAX_TOTAL_SIZE = 5;

    private final DataSource dataSource;

    static {
        try {
            Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

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
            RunScript.execute(connection, new FileReader(INIT_FILE_PATH));
        } catch (Exception exception) {
            throw new IllegalStateException("[ERROR] init.sql 파일을 실행하는 도중 문제가 발생했습니다.", exception);
        }
    }
}
