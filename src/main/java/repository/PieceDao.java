package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PieceDao {

    public void saveAll(Connection connection, long gameId, List<PieceRow> pieces) throws SQLException {
        String sql = "INSERT INTO piece (game_id, team, piece_type, x, y) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (PieceRow piece : pieces) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setString(2, piece.team());
            preparedStatement.setString(3, piece.pieceType());
            preparedStatement.setInt(4, piece.x());
            preparedStatement.setInt(5, piece.y());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }

    public List<PieceRow> findByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "SELECT team, piece_type, x, y FROM piece WHERE game_id = ?";
        List<PieceRow> pieces = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, gameId);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                pieces.add(new PieceRow(
                        resultSet.getInt("x"),
                        resultSet.getInt("y"),
                        resultSet.getString("piece_type"),
                        resultSet.getString("team")
                ));
            }
        }
        return pieces;
    }

    public Optional<Long> findIdByPosition(Connection connection, long gameId, int x, int y) throws SQLException {
        String sql = "SELECT id FROM piece WHERE game_id = ? AND x = ? AND y = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, gameId);
        preparedStatement.setInt(2, x);
        preparedStatement.setInt(3, y);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("id"));
            }
        }
        return Optional.empty();
    }

    public void updatePosition(Connection connection, long pieceId, int x, int y) throws SQLException {
        String sql = "UPDATE piece SET x = ?, y = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, x);
        preparedStatement.setInt(2, y);
        preparedStatement.setLong(3, pieceId);
        preparedStatement.executeUpdate();
    }

    public void deleteById(Connection connection, long pieceId) throws SQLException {
        String sql = "DELETE FROM piece WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, pieceId);
        preparedStatement.executeUpdate();
    }
}
