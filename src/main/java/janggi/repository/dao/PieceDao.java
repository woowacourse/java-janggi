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

    public void move(Connection connection, long savedGameId, Position from, Position to) throws SQLException {
        String sql = "UPDATE pieces SET x = ?, y = ? WHERE game_id = ? AND x = ? AND y = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, to.x());
            statement.setInt(2, to.y());
            statement.setLong(3, savedGameId);
            statement.setInt(4, from.x());
            statement.setInt(5, from.y());

            int updatedCount = statement.executeUpdate();
            if (updatedCount != 1) {
                throw new SQLException("이동 대상 기물 수정 결과가 예상과 다릅니다.");
            }
        }
    }
}

