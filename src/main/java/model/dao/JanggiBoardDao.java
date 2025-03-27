package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import model.dto.PieceDto;

public final class JanggiBoardDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user"; //  MySQL 서버 아이디
    private static final String PASSWORD = "password"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        // 드라이버 연결
        try {
            System.out.println("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION);
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void updateJanggiGame(int gameId, List<PieceDto> pieces) {
        final var deleteGameQuery = "DELETE FROM JanggiBoard WHERE game_id = ?";
        final var insertGameQuery = "INSERT INTO JanggiBoard (game_id, x_pos, y_pos, team_name, piece_type)VALUES(?, ?, ?, ?, ?)";

        try (final var connection = getConnection()) {
            try (final var deleteStatement = connection.prepareStatement(deleteGameQuery)) {
                deleteStatement.setInt(1, gameId);
                deleteStatement.executeUpdate();
            }

            try (final var preparedStatement = connection.prepareStatement(insertGameQuery)) {
                for (final var piece : pieces) {
                    preparedStatement.setInt(1, gameId);
                    preparedStatement.setInt(2, piece.x());
                    preparedStatement.setInt(3, piece.y());
                    preparedStatement.setString(4, piece.team());
                    preparedStatement.setString(5, piece.pieceName());

                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteJanggiGame(int gameId) {
        final var deleteGameQuery = "DELETE FROM JanggiBoard WHERE game_id = ?";
        try (final var connection = getConnection()) {
            try (final var deleteStatement = connection.prepareStatement(deleteGameQuery)) {
                deleteStatement.setInt(1, gameId);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existJanggiGame(int gameId) {
        final var existGameQuery = "SELECT 1 FROM JanggiBoard WHERE game_id = ? LIMIT 1";
        try (final var connection = getConnection()) {
            try (final var existStatement = connection.prepareStatement(existGameQuery)) {
                existStatement.setInt(1, gameId);
                return existStatement.executeQuery().next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}