package janggi.db.dao;

import janggi.db.DbConnector;
import janggi.db.entity.PieceEntity;
import janggi.domain.common.Team;
import janggi.domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    public void saveAll(List<PieceEntity> pieces) {
        String sql = "INSERT INTO pieces (game_id, x, y, piece_type, team) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            collectPieceBatch(pieces, preparedStatement);
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 저장 중 오류가 발생했습니다", e);
        }
    }

    public void deleteAllByGameId(Long gameId) {
        String sql = "DELETE FROM pieces WHERE game_id = ?";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 삭제 중 오류가 발생했습니다", e);
        }
    }

    public List<PieceEntity> findAllByGameId(Long gameId) {
        String sql = "SELECT * FROM pieces WHERE game_id = ?";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);
            return getPieceEntities(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 조회 중 오류가 발생했습니다", e);
        }
    }

    private List<PieceEntity> getPieceEntities(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return collectPieces(resultSet);
        }
    }

    private List<PieceEntity> collectPieces(ResultSet resultSet) throws SQLException {
        List<PieceEntity> pieces = new ArrayList<>();
        while (resultSet.next()) {
            pieces.add(mapToEntity(resultSet));
        }
        return pieces;
    }

    private void collectPieceBatch(List<PieceEntity> pieces, PreparedStatement preparedStatement) throws SQLException {
        for (PieceEntity piece : pieces) {
            preparedStatement.setLong(1, piece.getGameId());
            preparedStatement.setInt(2, piece.getX());
            preparedStatement.setInt(3, piece.getY());
            preparedStatement.setString(4, piece.getPieceType().name());
            preparedStatement.setString(5, piece.getTeam().name());
            preparedStatement.addBatch();
        }
    }

    private PieceEntity mapToEntity(ResultSet resultSet) throws SQLException {
        return new PieceEntity(
                resultSet.getLong("game_id"),
                resultSet.getInt("x"),
                resultSet.getInt("y"),
                PieceType.valueOf(resultSet.getString("piece_type")),
                Team.valueOf(resultSet.getString("team"))
        );
    }
}
