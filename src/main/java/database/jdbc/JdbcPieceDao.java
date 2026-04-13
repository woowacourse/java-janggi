package database.jdbc;

import database.dao.PieceDao;
import database.dto.PieceDto;
import domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPieceDao implements PieceDao {
    private final DatabaseConnector connector;

    public JdbcPieceDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    @Override
    public void saveAll(int gameId, List<PieceDto> pieces) {
        String sql = "INSERT INTO piece (game_id, type, team, row_idx, col_idx) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (PieceDto piece : pieces) {
                statement.setInt(1, gameId);
                statement.setString(2, piece.type());
                statement.setString(3, piece.team());
                statement.setInt(4, piece.rowIndex());
                statement.setInt(5, piece.colIndex());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("기물 저장에 실패했습니다.", e);
        }
    }

    @Override
    public List<PieceDto> findAll(int gameId) {
        String sql = "SELECT type, team, row_idx, col_idx FROM piece WHERE game_id = ?";
        List<PieceDto> pieces = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, gameId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    pieces.add(new PieceDto(
                            rs.getString("type"),
                            rs.getString("team"),
                            rs.getInt("row_idx"),
                            rs.getInt("col_idx")
                    ));
                }
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("기물 조회에 실패했습니다. gameId: " + gameId, e);
        }
    }

    @Override
    public void updatePosition(Connection connection, int gameId, Position src, Position dest) {
        String sql = "UPDATE piece SET row_idx = ?, col_idx = ? WHERE game_id = ? AND row_idx = ? AND col_idx = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dest.getRow());
            statement.setInt(2, dest.getColumn());
            statement.setInt(3, gameId);
            statement.setInt(4, src.getRow());
            statement.setInt(5, src.getColumn());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 위치 업데이트에 실패했습니다.", e);
        }
    }

    @Override
    public void delete(Connection connection, int gameId, Position position) {
        String sql = "DELETE FROM piece WHERE game_id = ? AND row_idx = ? AND col_idx = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, gameId);
            statement.setInt(2, position.getRow());
            statement.setInt(3, position.getColumn());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 삭제에 실패했습니다.", e);
        }
    }
}
