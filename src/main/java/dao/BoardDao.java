package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public static List<PieceEntity> readPieceEntitiesFromBoardEntity() {
        final var query = "SELECT row_value, column_value, type, dynasty FROM Board";
        final List<PieceEntity> pieceEntities = new ArrayList<>();

        try (final var connection = BoardDao.getConnection()) {
            if (connection == null) {
                throw new SQLException("데이터 베이스 연결에 실패했습니다.");
            }
            try (final var statement = connection.createStatement();
                 final var resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    final int row = resultSet.getInt("row_value");
                    final int column = resultSet.getInt("column_value");
                    final String type = resultSet.getString("type");
                    final String dynasty = resultSet.getString("dynasty");

                    pieceEntities.add(new PieceEntity(row, column, type, dynasty));
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return pieceEntities;
    }

    public static int readTurnEntity() {
        final var query = "SELECT turn FROM Turn";

        try (final var connection = BoardDao.getConnection()) {
            if (connection == null) {
                throw new SQLException("데이터 베이스 연결에 실패했습니다.");
            }
            try (final var statement = connection.createStatement();
                 final var resultSet = statement.executeQuery(query)) {

                if (resultSet.next()) {
                    return resultSet.getInt("turn");
                }
                throw new SQLException("Turn 테이블에서 데이터를 찾을 수 없습니다.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetTurnEntity() {
        final var query = "UPDATE Turn SET turn = 0";

        try (final var connection = BoardDao.getConnection()) {
            if (connection == null) {
                throw new SQLException("데이터 베이스 연결에 실패했습니다.");
            }
            try (final var statement = connection.createStatement()) {
                int rowsAffected = statement.executeUpdate(query);
                if (rowsAffected == 0) {
                    throw new SQLException("Turn 테이블에서 값을 리셋할 수 없습니다.");
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void incrementTurn() {
        final var query = "UPDATE Turn SET turn = turn + 1";

        try (final var connection = BoardDao.getConnection()) {
            if (connection == null) {
                throw new SQLException("데이터 베이스 연결에 실패했습니다.");
            }
            try (final var statement = connection.createStatement()) {
                int rowsAffected = statement.executeUpdate(query);
                if (rowsAffected == 0) {
                    throw new SQLException("Turn 테이블에서 값을 증가시킬 수 없습니다.");
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
