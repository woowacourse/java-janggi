package repository;

import db.JdbcConnection;
import domain.board.BoardLocation;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;

public class PieceStatusRepository {

    public void createTable(JdbcConnection connection) {
        String sql = "CREATE TABLE IF NOT EXISTS piece_status (" +
                "game_id INT NOT NULL, " +
                "piece_type VARCHAR(50) NOT NULL, " +
                "team VARCHAR(10) NOT NULL, " +
                "location_x INT NOT NULL, " +
                "location_y INT NOT NULL, " +
                "is_alive BOOLEAN NOT NULL, " +
                "PRIMARY KEY (game_id, piece_type, team, location_x, location_y))";

        try (Connection conn = connection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("테이블이 생성되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initializeBoard(JdbcConnection connection, int gameId, Map<BoardLocation, Piece> initialPieces) {
        String sql = "INSERT INTO piece_status (game_id, piece_type, team, location_x, location_y, is_alive) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 각 기물을 DB에 삽입
            for (Entry<BoardLocation, Piece> entry : initialPieces.entrySet()) {
                stmt.setInt(1, gameId);
                stmt.setString(2, entry.getValue().getType().name());
                stmt.setString(3, entry.getValue().getTeam().name());
                stmt.setInt(4, entry.getKey().x());
                stmt.setInt(5, entry.getKey().y());
                stmt.setBoolean(6, true);

                stmt.addBatch();
            }

            stmt.executeBatch();
            System.out.println("초기 보드판 상태가 DB에 저장되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
