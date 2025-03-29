package repository.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import repository.Schema;

public class ConnectMysql implements ConnectDatabase{
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user"; //  MySQL 서버 아이디
    private static final String PASSWORD = "password"; // MySQL 서버 비밀번호

    @Override
    public Connection create() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
            Schema.setTable(connection);
            return connection;
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 오류");
        }
    }
}
