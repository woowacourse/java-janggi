package janggi.repository.dao;

import janggi.domain.board.Position;
import janggi.repository.data.PieceData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    public void saveAll(Connection connection, List<PieceData> pieceData) throws SQLException {
        String sql = "INSERT INTO pieces(game_id, x, y, name, team) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (PieceData pieceDatum : pieceData) {
                statement.setLong(1, pieceDatum.gameId());
                statement.setInt(2, pieceDatum.x());
                statement.setInt(3, pieceDatum.y());
                statement.setString(4, pieceDatum.name());
                statement.setString(5, pieceDatum.team());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public List<PieceData> findByGameId(Connection connection, long savedGameId) throws SQLException {
        String sql = "SELECT game_id, x, y, name, team FROM pieces WHERE game_id = ?";
        List<PieceData> pieceData = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, savedGameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    pieceData.add(new PieceData(
                            resultSet.getLong("game_id"),
                            resultSet.getInt("x"),
                            resultSet.getInt("y"),
                            resultSet.getString("name"),
                            resultSet.getString("team")
                    ));
                }
            }
        }
        return pieceData;
    }

    public void deleteOn(Connection connection, long savedGameId, Position piecePosition) throws SQLException {
        String sql = "DELETE FROM pieces WHERE game_id = ? AND x = ? AND y = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, savedGameId);
            statement.setInt(2, piecePosition.x());
            statement.setInt(3, piecePosition.y());
            statement.executeUpdate();
        }
    }

    public void save(Connection connection, PieceData pieceData) throws SQLException {
        String sql = "INSERT INTO pieces(game_id, x, y, name, team) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, pieceData.gameId());
            statement.setInt(2, pieceData.x());
            statement.setInt(3, pieceData.y());
            statement.setString(4, pieceData.name());
            statement.setString(5, pieceData.team());
            statement.executeUpdate();
        }
    }
}
