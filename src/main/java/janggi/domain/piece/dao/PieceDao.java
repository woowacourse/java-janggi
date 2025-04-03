package janggi.domain.piece.dao;

import janggi.domain.database.DatabaseConnection;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.PieceMovementStrategyMap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class PieceDao {

    public Map<Position, Piece> findAllToMap() {
        String query = "SELECT * FROM piece";
        try (PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Map<Position, Piece> result = new HashMap<>();
                while (resultSet.next()) {
                    Side side = Side.valueOf(resultSet.getString("side"));
                    PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                    Position position = new Position(resultSet.getInt("position_x"), resultSet.getInt("position_y"));

                    Piece piece = new Piece(pieceType, PieceMovementStrategyMap.get(pieceType), side, position);
                    result.put(position, piece);
                }
                return result;
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAll() {
        String query = "DELETE FROM piece";
        try (PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAll(Map<Position, Piece> pieces) {
        String query = "INSERT INTO piece VALUES(?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(query)) {
            for (Piece piece : pieces.values()) {
                preparedStatement.setString(1, piece.getSide().name());
                preparedStatement.setString(2, piece.getPieceType().name());
                preparedStatement.setInt(3, piece.getPosition().x());
                preparedStatement.setInt(4, piece.getPosition().y());

                if (preparedStatement.executeUpdate() != 1) {
                    throw new RuntimeException("저장에 실패했습니다.");
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
