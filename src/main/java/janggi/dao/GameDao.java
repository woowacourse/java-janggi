package janggi.dao;

import janggi.domain.game.Team;
import janggi.dto.GameDto;
import janggi.dto.PieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class GameDao {

    private final MysqlConnection mysqlConnection;

    public GameDao(final MysqlConnection mysqlConnection) {
        this.mysqlConnection = mysqlConnection;
    }

    public List<GameDto> getAllGames() {
        final String query = "SELECT id, turn, created_at FROM game ORDER BY created_at";
        final List<GameDto> games = new ArrayList<>();

        try (final Connection connection = mysqlConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                games.add(toGameDto(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return games;
    }

    public GameDto getGameById(final int gameId) {
        final String query = "SELECT id, turn, created_at FROM game WHERE id = ?";

        try (final Connection connection = mysqlConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return toGameDto(resultSet);
            }
            throw new SQLException("게임을 조회할 수 없습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveGame(final Team turn, final List<PieceDto> pieceDtos) {
        final String insertGameQuery = "INSERT INTO game (turn) VALUES(?)";
        final String insertPieceQuery = "INSERT INTO piece (game_id, pieceType, team, col_num, row_num) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = mysqlConnection.getConnection();
            connection.setAutoCommit(false);

            try (final PreparedStatement insertGameStatement = connection.prepareStatement(insertGameQuery,
                    Statement.RETURN_GENERATED_KEYS);
                 final PreparedStatement insertPieceStatement = connection.prepareStatement(insertPieceQuery)) {

                insertGameStatement.setString(1, turn.name());
                insertGameStatement.executeUpdate();

                final ResultSet keys = insertGameStatement.getGeneratedKeys();
                int gameId;
                if (keys.next()) {
                    gameId = keys.getInt(1);
                } else {
                    throw new SQLException("게임 생성 후 키가 반환되지 않았습니다.");
                }

                for (PieceDto pieceDto : pieceDtos) {
                    insertPieceStatement.setInt(1, gameId);
                    insertPieceStatement.setString(2, pieceDto.pieceType());
                    insertPieceStatement.setString(3, pieceDto.team());
                    insertPieceStatement.setInt(4, pieceDto.colNum());
                    insertPieceStatement.setInt(5, pieceDto.rowNum());
                    insertPieceStatement.addBatch();
                }
                insertPieceStatement.executeBatch();

                connection.commit();
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* 기존 게임 업데이트하기 */

    private final GameDto toGameDto(final ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String turn = resultSet.getString("turn");
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        return new GameDto(id, turn, createdAt.toLocalDateTime());
    }
}
