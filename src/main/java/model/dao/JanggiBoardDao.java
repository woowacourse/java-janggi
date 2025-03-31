package model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import model.dto.PieceDto;

public final class JanggiBoardDao {
    private final Connection connection;

    public JanggiBoardDao() {
        this.connection = getConnection();
    }

    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            Connection janggiBoardDBConnection = DriverManager.getConnection(
                    "jdbc:mysql://" + properties.getProperty("SERVER") + "/"
                            + properties.getProperty("DATABASE")
                            + properties.getProperty("OPTION"),
                    properties.getProperty("USERNAME"),
                    properties.getProperty("PASSWORD"));
            janggiBoardDBConnection.setAutoCommit(false);
            return janggiBoardDBConnection;
        } catch (final SQLException sqlException) {
            System.err.println("[ERROR] DB 연결 오류");
            sqlException.printStackTrace();
            return null;
        } catch (IOException ioException) {
            System.out.println("[ERROR] DB 정보 파일 읽기 오류");
            ioException.printStackTrace();
            return null;
        }
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void updateJanggiGame(int gameId, List<PieceDto> pieces, boolean turn) {
        final var deleteGameQuery = "DELETE FROM JanggiBoard WHERE game_id = ?";
        final var insertGameQuery = "INSERT INTO JanggiBoard (game_id, x_pos, y_pos, team_name, piece_type, turn)VALUES(?, ?, ?, ?, ?, ?)";
        try (final var deleteStatement = connection.prepareStatement(deleteGameQuery)) {
            deleteStatement.setInt(1, gameId);
            deleteStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("[ERROR] DB 삭제 오류");
            sqlException.printStackTrace();
        }

        try (final var preparedStatement = connection.prepareStatement(insertGameQuery)) {
            for (final var piece : pieces) {
                preparedStatement.setInt(1, gameId);
                preparedStatement.setInt(2, piece.x());
                preparedStatement.setInt(3, piece.y());
                preparedStatement.setString(4, piece.team());
                preparedStatement.setString(5, piece.pieceName());
                preparedStatement.setBoolean(6, turn);

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException sqlException) {
            System.out.println("[ERROR] DB 추가 오류");
            sqlException.printStackTrace();
        }

    }

    public void deleteJanggiGame(int gameId) {
        final var deleteGameQuery = "DELETE FROM JanggiBoard WHERE game_id = ?";
        try (final var deleteStatement = connection.prepareStatement(deleteGameQuery)) {
            deleteStatement.setInt(1, gameId);
            deleteStatement.executeUpdate();
            connection.commit();
        } catch (SQLException sqlException) {
            System.out.println("[ERROR] DB 삭제 오류");
            sqlException.printStackTrace();
        }
    }

    public boolean existJanggiGame(int gameId) {
        final var existGameQuery = "SELECT 1 FROM JanggiBoard WHERE game_id = ? LIMIT 1";

        try (final var existStatement = connection.prepareStatement(existGameQuery)) {
            existStatement.setInt(1, gameId);
            return existStatement.executeQuery().next();
        } catch (SQLException sqlException) {
            System.out.println("[ERROR] DB 데이터 탐색 오류");
            sqlException.printStackTrace();
        }
        return false;
    }

    public List<PieceDto> findByGameId(int gameId) {
        final var query = "SELECT x_pos, y_pos, team_name, piece_type FROM JanggiBoard WHERE game_id = ?";
        final List<PieceDto> results = new ArrayList<>();

        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int xPos = resultSet.getInt("x_pos");
                    int yPos = resultSet.getInt("y_pos");
                    String team = resultSet.getString("team_name");
                    String pieceType = resultSet.getString("piece_type");

                    PieceDto dto = new PieceDto(xPos, yPos, team, pieceType);
                    results.add(dto);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("[ERROR] DB 데이터 탐색 오류");
            sqlException.printStackTrace();
        }
        return results;
    }

    public boolean getTurnByGameId(int gameId) {
        final var query = "SELECT turn FROM JanggiBoard WHERE game_id = ?";
        boolean turn = false;
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                turn = resultSet.getBoolean("turn");
            }
        } catch (SQLException sqlException) {
            System.out.println("[ERROR] DB 데이터 탐색 오류");
            sqlException.printStackTrace();
        }
        return turn;
    }

}
