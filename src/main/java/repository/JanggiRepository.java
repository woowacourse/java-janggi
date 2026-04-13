package repository;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceGenerator;
import domain.piece.PieceType;
import domain.position.Position;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class JanggiRepository {
    private static final String URL = "jdbc:h2:tcp://localhost/~/janggi";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public JanggiRepository() {
        initTable();
    }

    public void saveGame(int gameId, Map<Position, Piece> boardStatus, Camp camp) {
        updateTurn(gameId, camp);
        saveBoard(gameId, boardStatus);
    }

    public int createNewGame(Camp firstTurn) {
        String gamesSql = "INSERT INTO games(current_turn) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(gamesSql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, firstTurn.name());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // 자동 생성된 game_id 반환
                }
            }
        } catch (SQLException e) {
            System.err.println("새 게임 생성 오류: " + e.getMessage());
        }
        throw new IllegalStateException("새로운 game_id 생성에 실패했습니다.");
    }

    public int getLatestGameId() {
        String sql = "SELECT MAX(game_id) AS latest_id FROM games";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int latestId = rs.getInt("latest_id");
                if (rs.wasNull()) {
                    throw new NoSuchElementException("저장된 게임이 없습니다.");
                }
                return latestId;
            }
        } catch (SQLException e) {
            System.err.println("game_id 읽기 오류: " + e.getMessage());
        }
        throw new NoSuchElementException("저장된 게임이 없습니다.");
    }

    public Map<Position, Piece> readBoard(int gameId) {
        String sql = "SELECT * FROM pieces WHERE game_id = ?";
        Map<Position, Piece> board = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.err.println("SQL read 오류: " + e.getMessage());
        }

        throw new NoSuchElementException("저장된 Board가 없습니다.");
    }

    public Camp readTurn(int gameId) {
        String sql = "SELECT * FROM games WHERE game_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String camp = rs.getString("current_turn");
                    return Camp.valueOf(camp);
                }
            }
        } catch (SQLException e) {
            System.err.println("turn 읽기 오류: " + e.getMessage());
        }

        throw new NoSuchElementException("저장된 turn이 없습니다.");
    }

    private void initTable() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String sql = """
            CREATE TABLE IF NOT EXISTS games (
                game_id INT AUTO_INCREMENT PRIMARY KEY,
                current_turn VARCHAR(10) NOT NULL
            )
            """;

            String piecesSql = """
            CREATE TABLE IF NOT EXISTS pieces (
                game_id INT NOT NULL,
                camp VARCHAR(10) NOT NULL,
                piece_type VARCHAR(20) NOT NULL,
                x_position INT NOT NULL,
                y_position INT NOT NULL,
                FOREIGN KEY (game_id) REFERENCES games(game_id) ON DELETE CASCADE
            )
            """;

            stmt.execute(sql);
            stmt.execute(piecesSql);
        } catch (SQLException e) {
            System.err.println("테이블 초기화 중 오류 발생: " + e.getMessage());
        }
    }

    private void updateTurn(int gameId, Camp camp) {
        String sql = "UPDATE games SET current_turn = ? WHERE game_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, camp.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Turn 업데이트 오류: " + e.getMessage());
        }
    }

    private void saveBoard(int gameId, Map<Position, Piece> boardStatus) {
        deleteAllPieces(gameId);

        for (Map.Entry<Position, Piece> entry : boardStatus.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            int x = position.getX();
            int y = position.getY();
            String pieceType = piece.getPieceType().name();
            String pieceCamp = piece.getCamp().name();

            savePiece(gameId, pieceCamp, pieceType, x, y);
        }
    }

    private void savePiece(int gameId, String camp, String type, int x, int y) {
        String sql = "INSERT INTO pieces (game_id, camp, piece_type, x_position, y_position) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            pstmt.setString(2, camp);
            pstmt.setString(3, type);
            pstmt.setInt(4, x);
            pstmt.setInt(5, y);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Pieces 저장 오류: " + e.getMessage());
        }
    }

    private void deleteAllPieces(int gameId) {
        String sql = "DELETE FROM pieces WHERE game_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Pieces 삭제 오류: " + e.getMessage());
        }
    }
}
