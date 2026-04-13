package janggi.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;

public final class DataSourceConfig {

    private DataSourceConfig() {
    }

    public static DataSource createDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/janggi?serverTimezone=Asia/Seoul&characterEncoding=UTF-8");
        dataSource.setUser("root");
        dataSource.setPassword("12345678");
        return dataSource;
    }
}
