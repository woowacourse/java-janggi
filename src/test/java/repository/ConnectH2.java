package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.connection.ConnectDatabase;

public class ConnectH2 implements ConnectDatabase {

    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";


    @Override
    public Connection create() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 테스트 DB 연결 오류");
        }
    }

    @Override
    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 테스트 DB 연결 닫기 오류");
        }
    }

}
