package janggi.repository;

import janggi.entity.PieceEntity;
import janggi.util.DBConnectionManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcPieceRepositoryTest {

    private JdbcPieceRepository pieceRepository;
    private Connection conn;
    private final int testGameId = 1;

    @BeforeAll
    static void initTable() throws SQLException {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Game (game_id INT PRIMARY KEY, state VARCHAR(50), turn VARCHAR(50), start_date DATE)"
             );
             PreparedStatement pstmt2 = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Piece (game_id INT, type VARCHAR(50), `row` INT, col INT, FOREIGN KEY (game_id) REFERENCES Game(game_id) ON DELETE CASCADE)"
             )) {
            pstmt1.execute();
            pstmt2.execute();
        }
    }

    @AfterAll
    static void dropTable() throws SQLException {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DROP TABLE IF EXISTS Piece, Game")) {
            pstmt.execute();
        }
    }

    @BeforeEach
    void setUp() throws SQLException {
        pieceRepository = new JdbcPieceRepository();
        conn = DBConnectionManager.getConnection();
        conn.setAutoCommit(false);

        String insertGameSql = "INSERT INTO Game (game_id, state, turn, start_date) VALUES (?, 'PROGRESS', 'HAN', NOW())";
        try (PreparedStatement pstmt = conn.prepareStatement(insertGameSql)) {
            pstmt.setInt(1, testGameId);
            pstmt.executeUpdate();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }

    @Test
    void 여러_개의_기물을_넘기면_DB에_한_번에_저장되어야_한다() throws SQLException {
        // give
        List<PieceEntity> pieces = List.of(
                new PieceEntity(testGameId, "CHA", 0, 0),
                new PieceEntity(testGameId, "MA", 0, 1)
        );

        // when
        pieceRepository.saveAll(conn, pieces);

        // then
        String sql = "SELECT COUNT(*) FROM Piece WHERE game_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, testGameId);
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            int count = rs.getInt(1);
            assertThat(count).isEqualTo(2);
        }
    }

    @Test
    void 기물의_기존_좌표와_새_좌표를_주면_성공적으로_위치가_업데이트되어야_한다() throws SQLException {
        // give
        List<PieceEntity> initialPiece = List.of(new PieceEntity(testGameId, "CHA", 0, 0));
        pieceRepository.saveAll(conn, initialPiece);

        // when
        pieceRepository.updatePosition(conn, testGameId, 0, 0, 2, 2);

        // then
        String sql = "SELECT type FROM Piece WHERE game_id = ? AND `row` = ? AND col = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, testGameId);
            pstmt.setInt(2, 2);
            pstmt.setInt(3, 2);
            ResultSet rs = pstmt.executeQuery();

            assertThat(rs.next()).isTrue();
            assertThat(rs.getString("type")).isEqualTo("CHA");
        }
    }

    @Test
    void 좌표를_주면_해당_위치의_기물이_삭제_되어야_한다() throws SQLException {
        // give
        List<PieceEntity> targetPiece = List.of(new PieceEntity(testGameId, "JOL", 5, 5));
        pieceRepository.saveAll(conn, targetPiece);

        // when
        pieceRepository.deleteByPosition(conn, testGameId, 5, 5);

        // then
        String sql = "SELECT * FROM Piece WHERE game_id = ? AND `row` = ? AND col = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, testGameId);
            pstmt.setInt(2, 5);
            pstmt.setInt(3, 5);
            ResultSet rs = pstmt.executeQuery();

            assertThat(rs.next()).isFalse();
        }
    }
}
