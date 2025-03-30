package janggi.db;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connection {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public java.sql.Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean isTableExist(String tableName) throws SQLException {
        final var connection = new Connection();
        DatabaseMetaData databaseMetaData = connection.getConnection().getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, null);
        return resultSet.next();
    }

    public void createPieceTable() {
        String createPieceTableQuery = "CREATE TABLE piece ("
                + "id INT not null auto_increment,"
                + "pieceType varchar(10) not null, " // 기물 종류
                + "positionX INT not null," // x 위치
                + "positionY INT not null," // y 위치
                + "team varchar(10) not null, PRIMARY KEY (id))"; // 팀
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(createPieceTableQuery)) {
            preparedStatement.executeUpdate();
            System.out.println("기물 테이블 생성 성공!");
        } catch (Exception e) {
            System.out.println("테이블 생성 실패 이유 : " + e);
        }
    }

    public void createTurnTable() {
        String createTeamTableQuery = "CREATE TABLE turn ("
                + "id INT not null auto_increment,"
                + "team varchar(10) not null, PRIMARY KEY (id))"; // 기물 종류
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(createTeamTableQuery)) {
            preparedStatement.executeUpdate();
            System.out.println("팀 테이블 생성 성공!");
        } catch (Exception e) {
            System.out.println("테이블 생성 실패 이유 : " + e);
        }
    }

    public void dropTable(String tableName) {
        String dropTableQuery = "DROP TABLE ";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(dropTableQuery + tableName)) {
            preparedStatement.executeUpdate();
            System.out.println(tableName + "삭제 성공!");
        } catch (Exception e) {
            System.out.println("테이블 삭제 실패 이유 : " + e);
        }
    }
}
