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
             PreparedStatement dropStmt = conn.prepareStatement(
                     "DROP TABLE IF EXISTS Piece, Game"
             );
             PreparedStatement pstmt1 = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Game (game_id INT PRIMARY KEY, state VARCHAR(50), turn VARCHAR(50), start_date DATE)"
             );
             // 💡 1. 테이블 생성 쿼리에 team VARCHAR(50)을 추가했습니다.
             PreparedStatement pstmt2 = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Piece (game_id INT, type VARCHAR(50), team VARCHAR(50), `row` INT, col INT, FOREIGN KEY (game_id) REFERENCES Game(game_id) ON DELETE CASCADE)"
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
                new PieceEntity(testGameId, "CHA", "CHO", 0, 0),
                new PieceEntity(testGameId, "MA", "HAN", 0, 1)
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
        List<PieceEntity> initialPiece = List.of(new PieceEntity(testGameId, "CHA", "CHO", 0, 0));
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
        // give: 💡 팀 정보 추가
        List<PieceEntity> targetPiece = List.of(new PieceEntity(testGameId, "JOL", "HAN", 5, 5));
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

    @Test
    void 특정_방의_모든_기물을_조회하면_저장된_모든_기물_리스트를_반환한다() throws SQLException {
        // give
        List<PieceEntity> pieces = List.of(
                new PieceEntity(testGameId, "CHA", "CHO", 0, 0),
                new PieceEntity(testGameId, "MA", "HAN", 0, 1)
        );
        pieceRepository.saveAll(conn, pieces);

        // when
        List<PieceEntity> result = pieceRepository.findAllByGameId(conn, testGameId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("type").contains("CHA", "MA");
        assertThat(result).extracting("team").contains("CHO", "HAN");
    }

    @Test
    void 기물이_없는_방_번호로_조회하면_빈_리스트를_반환한다() throws SQLException {
        // when
        List<PieceEntity> result = pieceRepository.findAllByGameId(conn, testGameId);

        // then
        assertThat(result).isEmpty();
    }
}
