package database.jdbc;

import database.dao.PieceDao;
import domain.game.Team;
import domain.piece.Piece;
import domain.piece.PieceDefinition;
import domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JdbcPieceDao implements PieceDao {

    private final DatabaseConnector connector;

    public JdbcPieceDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    @Override
    public void saveAll(int gameId, Map<Position, Piece> pieces) {
        String sql = "INSERT INTO piece (game_id, type, team, row_idx, col_idx) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                statement.setInt(1, gameId);
                statement.setString(2, piece.getType().name());
                statement.setString(3, piece.getTeam().name());
                statement.setInt(4, position.getRow());
                statement.setInt(5, position.getColumn());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("기물 저장에 실패했습니다.", e);
        }
    }

    @Override
    public Map<Position, Piece> findAll(int gameId) {
        String sql = "SELECT type, team, row_idx, col_idx FROM piece WHERE game_id = ?";
        Map<Position, Piece> pieces = new HashMap<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, gameId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Position position = new Position(rs.getInt("row_idx"), rs.getInt("col_idx"));
                    PieceDefinition type = PieceDefinition.valueOf(rs.getString("type"));
                    Team team = Team.valueOf(rs.getString("team"));
                    pieces.put(position, type.createPiece(team));
                }
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("기물 조회에 실패했습니다. gameId: " + gameId, e);
        }
    }

    @Override
    public void updatePosition(int gameId, Position src, Position dest) {
        String sql = "UPDATE piece SET row_idx = ?, col_idx = ? WHERE game_id = ? AND row_idx = ? AND col_idx = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

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
    public void delete(int gameId, Position position) {
        String sql = "DELETE FROM piece WHERE game_id = ? AND row_idx = ? AND col_idx = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, gameId);
            statement.setInt(2, position.getRow());
            statement.setInt(3, position.getColumn());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 삭제에 실패했습니다.", e);
        }
    }
}
