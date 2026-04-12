package janggi.db;

import janggi.domain.GameContext;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieceDao {
    private PieceDao() {
    }

    public static void deletePiecesTable(Connection connection, final int gameId) {
        final String sql = "DELETE FROM PIECE WHERE game_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    public static void insertPiece(Connection connection, GameContext gameContext, final int gameId) {
        final String sql = "INSERT INTO piece (game_id, position_row, position_column, piece_type, team_type) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : gameContext.getPositionPieceMap().entrySet()) {
                statement.setInt(1, gameId);
                statement.setInt(2, entry.getKey().getRow());
                statement.setInt(3, entry.getKey().getColumn());
                statement.setString(4, entry.getValue().pieceType().toString());
                statement.setString(5, entry.getValue().teamType().toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    public static Map<Position, Piece> selectPieceMap(Connection connection, final int gameId) {
        final String sql = "SELECT position_row, position_column, piece_type, team_type FROM piece WHERE game_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, gameId);
            return extractPieceMap(statement);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }

    private static Map<Position, Piece> extractPieceMap(PreparedStatement statement) {
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
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류", e);
        }
    }
}
