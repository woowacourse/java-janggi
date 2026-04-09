package janggi.repository.piece;

import janggi.config.DatabaseManager;
import janggi.domain.position.Position;
import janggi.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPieceRepository implements PieceRepository {

    @Override
    public void saveAll(Connection connection, Long gameId, List<PieceEntity> pieces) {
        String sql = "INSERT INTO piece (janggi_game_id, row_pos, col_pos, team, type) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (PieceEntity piece : pieces) {
                statement.setLong(1, gameId);
                statement.setInt(2, piece.row());
                statement.setInt(3, piece.column());
                statement.setString(4, piece.dynasty());
                statement.setString(5, piece.type());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("저장 실패", e);
        }
    }

    @Override
    public void update(Connection connection, Long gameId, Position from, Position to) {
        String deleteSql = """
                DELETE FROM piece 
                WHERE janggi_game_id = ? AND row_pos = ? AND col_pos = ?
                """;
        String updateSql = """
                UPDATE piece
                SET row_pos = ?, col_pos = ?
                WHERE janggi_game_id = ? AND row_pos = ? AND col_pos = ?
                """;

        try (
                PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                PreparedStatement updateStatement = connection.prepareStatement(updateSql)
        ) {
            deleteStatement.setLong(1, gameId);
            deleteStatement.setInt(2, to.row().row());
            deleteStatement.setInt(3, to.column().column());
            deleteStatement.executeUpdate();

            updateStatement.setInt(1, to.row().row());
            updateStatement.setInt(2, to.column().column());
            updateStatement.setLong(3, gameId);
            updateStatement.setInt(4, from.row().row());
            updateStatement.setInt(5, from.column().column());
            int affectedRows = updateStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("이동할 기물이 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("기물 이동 저장 실패", e);
        }
    }

    @Override
    public List<PieceEntity> findAllByGameId(Long gameId) {
        String sql = "SELECT row_pos, col_pos, team, type FROM piece WHERE janggi_game_id = ?";

        List<PieceEntity> pieces = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int row = resultSet.getInt("row_pos");
                    int column = resultSet.getInt("col_pos");
                    String dynasty = resultSet.getString("team");
                    String pieceType = resultSet.getString("type");

                    pieces.add(PieceEntity.toEntity(row, column, dynasty, pieceType));
                }
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("기물 조회 실패", e);
        }
    }

}
