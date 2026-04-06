package repository;

import javax.sql.DataSource;
import org.sqlite.SQLiteDataSource;

public final class MemoryDBConnectionUtil {

    private static final String JDBC_SQLITE_URL = "jdbc:sqlite::memory:?cache=shared";

    private MemoryDBConnectionUtil() {
    }

    public static DataSource getDataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(JDBC_SQLITE_URL);

        return dataSource;
    }
}
