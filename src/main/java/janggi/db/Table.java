package janggi.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Table {
    private final Connection connection;

    public Table(final Connection connection) {
        this.connection = connection;
    }

    public boolean isTableExist(final String tableName) throws SQLException {
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
        try (final var conn = connection.getConnection();
             final var preparedStatement = conn.prepareStatement(createPieceTableQuery)) {
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
        try (final var conn = connection.getConnection();
             final var preparedStatement = conn.prepareStatement(createTeamTableQuery)) {
            preparedStatement.executeUpdate();
            System.out.println("팀 테이블 생성 성공!");
        } catch (Exception e) {
            System.out.println("테이블 생성 실패 이유 : " + e);
        }
    }

    public void dropTable(final String tableName) {
        String dropTableQuery = "DROP TABLE ";
        try (final var conn = connection.getConnection();
             final var preparedStatement = conn.prepareStatement(dropTableQuery + tableName)) {
            preparedStatement.executeUpdate();
            System.out.println(tableName + "삭제 성공!");
        } catch (Exception e) {
            System.out.println("테이블 삭제 실패 이유 : " + e);
        }
    }
}
