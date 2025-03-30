package janggi.db;

import janggi.piece.Piece;
import janggi.piece.PieceFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class BoardStatus {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pazz4321";

    private static final String INVALID_DB_CONNECTION = "[DB 연결 오류] ";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.out.println(INVALID_DB_CONNECTION + e.getMessage());
            return null;
        }
    }

    public void createBoardStatus() {
        String sql = """
                CREATE TABLE IF NOT EXISTS board_status (
                      piece_id INT AUTO_INCREMENT,
                      piece_name VARCHAR(20),
                      team_name VARCHAR(10),
                      piece_status VARCHAR(10),
                      position_x INT,
                      position_y INT,
                      PRIMARY KEY (piece_id, position_x, position_y)
                  );
                """;

        try (final var conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isBoardStatusEmpty() {
        String sql = "SELECT COUNT(*) FROM board_status";

        try (final var conn = getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void saveBoardStatus(List<Piece> allPieces) {
        String sql = "INSERT INTO board_status (piece_name, team_name, piece_status, position_x, position_y) VALUES (?, ?, ?, ?, ?)";

        try (final var conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Piece piece : allPieces) {
                pstmt.setString(1, piece.getName());
                pstmt.setString(2, piece.getTeamName());
                pstmt.setString(3, piece.getStatus().name());
                pstmt.setInt(4, piece.getPosition().x());
                pstmt.setInt(5, piece.getPosition().y());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Piece> loadBoardStatus() {
        String sql = "SELECT piece_name, team_name, piece_status, position_x, position_y FROM board_status";

        List<Piece> pieces = new ArrayList<>();
        try (final var conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pieceName = rs.getString("piece_name");
                String teamName = rs.getString("team_name");
                String pieceStatus = rs.getString("piece_status");
                int positionX = rs.getInt("position_x");
                int positionY = rs.getInt("position_y");

                pieces.add(PieceFactory.createPiece(pieceName, teamName, pieceStatus, positionX, positionY));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pieces;
    }

    public void clearBoardStatus() {
        String sql = "TRUNCATE TABLE board_status";

        try (final var conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
