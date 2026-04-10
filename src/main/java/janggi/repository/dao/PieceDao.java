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

    public void saveAll(Connection connection, List<PieceData> piecesData) throws SQLException {
        String sql = "INSERT INTO pieces(game_id, row_index, column_index, name, team) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (PieceData pieceData : piecesData) {
                statement.setLong(1, pieceData.gameId());
                statement.setInt(2, pieceData.row());
                statement.setInt(3, pieceData.column());
                statement.setString(4, pieceData.name());
                statement.setString(5, pieceData.team());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public List<PieceData> findByGameId(Connection connection, long savedGameId) throws SQLException {
        String sql = "SELECT game_id, row_index, column_index, name, team FROM pieces WHERE game_id = ?";
        List<PieceData> piecesData = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, savedGameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    piecesData.add(new PieceData(
                            resultSet.getLong("game_id"),
                            resultSet.getInt("row_index"),
                            resultSet.getInt("column_index"),
                            resultSet.getString("name"),
                            resultSet.getString("team")
                    ));
                }
            }
        }
        return piecesData;
    }

    public void deleteOn(Connection connection, long savedGameId, Position piecePosition) throws SQLException {
        String sql = "DELETE FROM pieces WHERE game_id = ? AND row_index = ? AND column_index = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, savedGameId);
            statement.setInt(2, piecePosition.y());
            statement.setInt(3, piecePosition.x());
            statement.executeUpdate();
        }
    }

    public void move(Connection connection, long savedGameId, Position from, Position to) throws SQLException {
        String sql = """
                UPDATE pieces
                SET row_index = ?, column_index = ?
                WHERE game_id = ? AND row_index = ? AND column_index = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, to.y());
            statement.setInt(2, to.x());
            statement.setLong(3, savedGameId);
            statement.setInt(4, from.y());
            statement.setInt(5, from.x());

            int updatedCount = statement.executeUpdate();
            if (updatedCount != 1) {
                throw new SQLException("이동 대상 기물 수정 결과가 예상과 다릅니다.");
            }
        }
    }
}
