package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public final class BoardDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀 번호

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteBoardEntity() {
        final var query = "DELETE FROM Board";

        try (final var connection = BoardDao.getConnection()) {
            if (connection == null) {
                throw new SQLException("데이터 베이스 연결에 실패했습니다.");
            }
            try (final var statement = connection.createStatement()) {
                statement.executeUpdate(query);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addPieceEntitiesToBoardEntity(final List<PieceEntity> pieceEntities) {
        pieceEntities.forEach(BoardDao::addPieceEntityToBoardEntity);
    }

    private static void addPieceEntityToBoardEntity(final PieceEntity pieceEntity) {
        final var query = "INSERT INTO Board VALUES(?, ?, ?, ?)";

        try (final var connection = BoardDao.getConnection()) {
            if (connection == null) {
                throw new SQLException("데이터 베이스 연결에 실패했습니다.");
            }
            try (final var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, pieceEntity.row());
                preparedStatement.setInt(2, pieceEntity.column());
                preparedStatement.setString(3, pieceEntity.type());
                preparedStatement.setString(4, pieceEntity.dynasty());

                preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
