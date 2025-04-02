package janggi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MysqlConnection {

    private static final String SERVER = "localhost:3306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul";
    private static final String USERNAME = "root"; //  MySQL 유저 아이디
    private static final String PASSWORD = "11111111"; // MySQL 유저 비밀번호

    private final String url;

    public MysqlConnection() {
        this.url = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;
    }

    public MysqlConnection(final String customDatabase) {
        this.url = "jdbc:mysql://" + SERVER + "/" + customDatabase + OPTION;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
