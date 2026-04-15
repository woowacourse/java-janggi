package janggi.db.connection;

import janggi.exception.DataAccessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection implements DbConnection {

    private static final String URL = "jdbc:mysql://localhost:13306/janggi?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DataAccessException("게임 데이터를 불러오는 중 통신 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", e);
        }
    }
}
