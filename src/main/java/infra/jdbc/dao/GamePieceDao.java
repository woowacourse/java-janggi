package infra.jdbc.dao;

import domain.pieces.PieceType;
import domain.pieces.Side;
import infra.jdbc.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GamePieceDao {

    public void insertAll(Connection connection, long gameId, List<PieceEntity> pieces) throws SQLException {
        String sql = "INSERT INTO game_piece (game_id, row_index, column_index, side, piece_type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (PieceEntity piece : pieces) {
                statement.setLong(1, gameId);
                statement.setInt(2, piece.row());
                statement.setInt(3, piece.column());
                statement.setString(4, piece.side().name());
                statement.setString(5, piece.pieceType().name());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public List<PieceEntity> findAllByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "SELECT * FROM game_piece WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<PieceEntity> pieces = new ArrayList<>();
                while (resultSet.next()) {
                    pieces.add(new PieceEntity(
                            resultSet.getInt("row_index"),
                            resultSet.getInt("column_index"),
                            Side.valueOf(resultSet.getString("side")),
                            PieceType.valueOf(resultSet.getString("piece_type"))
                    ));
                }
                return pieces;
            }
        }
    }

    public void deleteAllByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "DELETE FROM game_piece WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }
}
