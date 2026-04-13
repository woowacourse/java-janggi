package repository.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;

public class DataSourceManager {

    private static final String URL = "jdbc:mysql://localhost:3309/janggi_db?serverTimezone=UTC&useSSL=false&rewriteBatchedStatements=true";
    private static final String USER = "janggi";
    private static final String PASSWORD = "janggi1234";

    private DataSourceManager() {
    }

    public static DataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
}
