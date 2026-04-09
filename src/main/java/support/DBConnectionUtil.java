package support;

import javax.sql.DataSource;
import org.sqlite.SQLiteDataSource;

public final class DBConnectionUtil {

    private static final String JDBC_SQLITE_URL = "jdbc:sqlite:src/main/resources/db/janggi.db";

    private DBConnectionUtil() {
    }

    public static DataSource getDataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(JDBC_SQLITE_URL);

        return dataSource;
    }
}
