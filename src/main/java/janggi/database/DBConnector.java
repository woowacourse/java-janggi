package janggi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    private static final String SERVER = "localhost:3306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "100tk324dl"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        //SQL 문을 데이터베이스로 보내기 위한
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] : DB 연결에 실패하였습니다.");
        }
    }

    //현재는 CREATE를 하는 것이니까, 그러면 stme.excuteUpdate()해도 0을 반환하겠군.
    //왜냐하면, excuteUpdate()가 int형을 반환하는데 그것은 SQL 실행 결과로 영향을 받은 행(row)의 개수니까

    //try 블록이 끝나면 자동으로 stmt.close()가 호출됨
    public void createTable() {
        try (final Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(createTeamTable());
            stmt.executeUpdate(createPiecesTable());
            stmt.executeUpdate(createTurnTable());
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] 테이블 생성 중 에러 발생");
        }
    }

    private String createTeamTable() {
        return "CREATE TABLE IF NOT EXISTS team (id INT AUTO_INCREMENT PRIMARY KEY, name CHAR(1) NOT NULL)";
    }

    private String createPiecesTable() {
        return "CREATE TABLE IF NOT EXISTS pieces ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "team_id INT NOT NULL, "
                + "piece_type CHAR(1) NOT NULL, "
                + "x INT NOT NULL, "
                + "y INT NOT NULL, "
                + "FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE"
                + ")";
    }

    private String createTurnTable() {
        return "CREATE TABLE IF NOT EXISTS turn ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "current_turn CHAR(1) NOT NULL)";
    }

}
