package janggi.db;

import janggi.domain.GameContext;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;
import janggi.domain.team.TurnManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GameDao {

    private static final int FIX_GAME_ID = 1;
    private final DatabaseConnector databaseConnector;

    public GameDao() {
        databaseConnector = new DatabaseConnector();
    }

    public GameDao(String url) {
        databaseConnector = new DatabaseConnector(url);
    }

    public void saveGame(GameContext gameContext) {
        try (Connection connection = databaseConnector.getConnection()) {
            deletePiecesTable(connection);
            deleteGameTable(connection);
            insertCurrentTurn(connection, gameContext);
            insertPiece(connection, gameContext);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류");
        }
    }

    public GameContext loadPreviousGame() {
        try (Connection connection = databaseConnector.getConnection()) {
            TurnManager turnManager = new TurnManager(selectCurrentTurn(connection));
            Board board = new Board(selectPieceMap(connection));
            return new GameContext(turnManager, board);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류");
        }
    }

    public void deleteGameRecord() {
        try (Connection connection = databaseConnector.getConnection()) {
            deletePiecesTable(connection);
            deleteGameTable(connection);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류");
        }
    }

    public boolean hasGameData() {
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM game WHERE id = ?")) {
            statement.setInt(1, FIX_GAME_ID);
            return isGamePresent(statement);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류");
        }
    }

    private void deletePiecesTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM PIECE WHERE game_id = ?")) {
            statement.setInt(1, FIX_GAME_ID);
            statement.executeUpdate();
        }
    }

    private void deleteGameTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM GAME WHERE id = ?")) {
            statement.setInt(1, FIX_GAME_ID);
            statement.executeUpdate();
        }
    }

    private void insertCurrentTurn(Connection connection, GameContext gameContext) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO game (id, current_turn) VALUES (?, ?);")) {
            statement.setInt(1, FIX_GAME_ID);
            statement.setString(2, gameContext.currentTeamType().toString());
            statement.executeUpdate();
        }
    }

    private void insertPiece(Connection connection, GameContext gameContext) throws SQLException {
        String sql = "INSERT INTO piece (game_id, position_row, position_column, piece_type, team_type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : gameContext.getPositionPieceMap().entrySet()) {
                statement.setInt(1, FIX_GAME_ID);
                statement.setInt(2, entry.getKey().getRow());
                statement.setInt(3, entry.getKey().getColumn());
                statement.setString(4, entry.getValue().pieceType().toString());
                statement.setString(5, entry.getValue().teamType().toString());
                statement.executeUpdate();
            }
        }
    }

    private String selectCurrentTurn(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT current_turn FROM game WHERE id = ?")) {
            statement.setInt(1, FIX_GAME_ID);
            return extractCurrentTurn(statement);
        }
    }

    private String extractCurrentTurn(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getString("current_turn");
        }
    }

    private Map<Position, Piece> selectPieceMap(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT position_row, position_column, piece_type, team_type FROM piece WHERE game_id = ?;")) {
            statement.setInt(1, FIX_GAME_ID);
            return extractPieceMap(statement);
        }
    }

    private Map<Position, Piece> extractPieceMap(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            Map<Position, Piece> positionPieceMap = new HashMap<>();
            while (resultSet.next()) {
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                TeamType teamType = TeamType.valueOf(resultSet.getString("team_type"));
                positionPieceMap.put(
                        Position.valueOf(resultSet.getInt("position_row"), resultSet.getInt("position_column")),
                        pieceType.toPiece(teamType));
            }
            return positionPieceMap;
        }
    }

    private boolean isGamePresent(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("COUNT(*)") > 0;
            }
            return false;
        }
    }
}
