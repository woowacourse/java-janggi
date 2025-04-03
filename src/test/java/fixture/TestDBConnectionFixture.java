package fixture;

import db.connection.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnectionFixture implements DBConnection {
    private static final TestDBConnectionFixture INSTANCE = new TestDBConnectionFixture();

    private static final String SERVER = "localhost:13307"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user"; //  MySQL 서버 아이디
    private static final String PASSWORD = "password"; // MySQL 서버 비밀번호

    private TestDBConnectionFixture() {
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 테스트 DB 연결에 실패하였습니다.");
        }
    }

    public static TestDBConnectionFixture getInstance() {
        return INSTANCE;
    }
}
