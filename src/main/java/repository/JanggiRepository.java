package repository;

import domain.*;
import domain.piece.Piece;
import domain.position.Position;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class JanggiRepository {
    private static final String URL = "jdbc:h2:tcp://localhost/~/janggi";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public void saveGame(Map<Position, Piece> boardStatus, Camp camp) {
        initTable();
        saveBoard(boardStatus);
        saveTurn(camp);
    }

    public Map<Position, Piece> readBoard() {
        String sql = "select * from pieces";
        Map<Position, Piece> board = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()){

            while (rs.next()) {
                String camp = rs.getString("camp");
                String pieceType = rs.getString("piece_type");
                int x = rs.getInt("x_position");
                int y = rs.getInt("y_position");

                Position position = new Position(x, y);
                Piece piece = PieceGenerator.createPiece(Camp.valueOf(camp), PieceType.valueOf(pieceType));

                board.put(position, piece);
            }

            return board;
        } catch (SQLException e) {
            System.err.println("SQL read 오류: " + e.getMessage());
        }

        throw new NoSuchElementException("저장된 Board가 없습니다.");
    }

    public Camp readTurn() {
        String sql = "select * from games";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                String camp = rs.getString("current_turn");
                return Camp.valueOf(camp);
            }
        } catch (SQLException e) {
            System.err.println("turn 읽기 오류: " + e.getMessage());
        }

        throw new NoSuchElementException("저장된 turn이 없습니다.");
    }

    private void initTable() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS pieces");
            stmt.execute("DROP TABLE IF EXISTS games");

            String sql = """
                CREATE TABLE IF NOT EXISTS games (
                    current_turn VARCHAR(10) NOT NULL
                )
            """;

            String piecesSql = """
                CREATE TABLE IF NOT EXISTS pieces (
                    camp VARCHAR(10) NOT NULL,
                    piece_type VARCHAR(20) NOT NULL,
                    x_position INT NOT NULL,
                    y_position INT NOT NULL
                )
            """;

            stmt.execute(sql);
            stmt.execute(piecesSql);
        } catch (SQLException e) {
            System.err.println("테이블 초기화 중 오류 발생: " + e.getMessage());
        }
    }

    private void saveTurn(Camp camp) {
        String gamesSql = "insert into games(current_turn) values (?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(gamesSql)) {

            pstmt.setString(1, camp.name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Turn 저장 오류: " + e.getMessage());
        }
    }

    private void saveBoard(Map<Position, Piece> boardStatus) {
        for (Map.Entry<Position, Piece> entry : boardStatus.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            int x = position.getX();
            int y = position.getY();
            String pieceType = piece.getPieceType().name();
            String pieceCamp = piece.getCamp().name();

            savePiece(pieceCamp, pieceType, x, y);
        }
    }

    private void savePiece(String camp, String type, int x, int y) {
        String sql = "INSERT INTO pieces (camp, piece_type, x_position, y_position) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, camp);
            pstmt.setString(2, type);
            pstmt.setInt(3, x);
            pstmt.setInt(4, y);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Pieces 저장 오류: " + e.getMessage());
        }
    }
}
