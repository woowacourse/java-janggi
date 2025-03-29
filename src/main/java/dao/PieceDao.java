package dao;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.piece.Piece;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;

public class PieceDao {
    private final Connection connection = JdbcConnection.getInstance();

    public void createPieceTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS piece (" +
                "piece_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "piece_type VARCHAR(50) NOT NULL, " +
                "team VARCHAR(10) NOT NULL, " +
                "location_x INT NOT NULL, " +
                "location_y INT NOT NULL, " +
                "is_alive BOOLEAN NOT NULL, " +
                "PRIMARY KEY (game_id, piece_type, team, location_x, location_y))";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("테이블이 생성되었습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeBoardIfNotExists(Board board) {
        Map<BoardLocation, Piece> pieces = board.getPieces();
        String query = "INSERT INTO piece_status ("
                + "piece_type,"
                + "team,"
                + "location_x,"
                + "location_y,"
                + "is_alive) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Entry<BoardLocation, Piece> entry : pieces.entrySet()) {
                stmt.setString(1, entry.getValue().getType().name());
                stmt.setString(2, entry.getValue().getTeam().name());
                stmt.setInt(3, entry.getKey().x());
                stmt.setInt(4, entry.getKey().y());
                stmt.setBoolean(5, true);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
