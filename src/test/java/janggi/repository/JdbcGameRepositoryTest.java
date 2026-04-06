package janggi.repository;

import janggi.entity.GameEntity;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcGameRepositoryTest {

    private JdbcGameRepository gameRepository;
    private Connection conn;

    @BeforeAll
    static void initTable() throws SQLException {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Game (game_id INT PRIMARY KEY, state VARCHAR(50), turn VARCHAR(50), start_date DATE)"
             );
             PreparedStatement pstmt2 = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Piece (game_id INT, type VARCHAR(50), `row` INT, col INT)"
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
        gameRepository = new JdbcGameRepository();
        conn = DBConnectionManager.getConnection();
        conn.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }

    @Test
    void 새로운_GameEntity를_전달하면_DB에_성공적으로_INSERT_되어야_한다() throws SQLException {
        // give
        int testGameId = 100;
        GameEntity newGame = new GameEntity(
                testGameId,
                "PROGRESS",
                "HAN",
                new java.sql.Date(System.currentTimeMillis())
        );

        // when
        gameRepository.save(conn, newGame);

        // then
        String sql = "SELECT * FROM Game WHERE game_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, testGameId);
            ResultSet rs = pstmt.executeQuery();

            assertThat(rs.next()).isTrue();
            assertThat(rs.getString("turn")).isEqualTo("HAN");
            assertThat(rs.getString("state")).isEqualTo("PROGRESS");
        }
    }

    @Test
    void 기존_방의_턴을_다른_턴으로_업데이트하면_DB에_정상_반영되어야_한다() throws SQLException {
        // give
        int testGameId = 200;
        GameEntity game = new GameEntity(
                testGameId,
                "PROGRESS",
                "HAN",
                new java.sql.Date(System.currentTimeMillis())
        );
        gameRepository.save(conn, game);

        // when
        String nextTurn = "CHO";
        gameRepository.updateTurn(conn, testGameId, nextTurn);

        // then
        String sql = "SELECT turn FROM Game WHERE game_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, testGameId);
            ResultSet rs = pstmt.executeQuery();

            assertThat(rs.next()).isTrue();
            assertThat(rs.getString("turn")).isEqualTo("CHO");
        }
    }

    @Test
    void 존재하는_방_번호로_조회하면_해당_게임_정보가_담긴_Optional을_반환한다() throws SQLException {
        // give
        int gameId = 300;
        GameEntity game = new GameEntity(gameId, "PROGRESS", "CHO", new java.sql.Date(System.currentTimeMillis()));
        gameRepository.save(conn, game);

        // when
        Optional<GameEntity> result = gameRepository.findById(conn, gameId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getGameId()).isEqualTo(gameId);
        assertThat(result.get().getTurn()).isEqualTo("CHO");
    }

    @Test
    void 존재하지_않는_방_번호로_조회하면_빈_Optional을_반환한다() throws SQLException {
        // when
        Optional<GameEntity> result = gameRepository.findById(conn, 999);

        // then
        assertThat(result).isEmpty();
    }
}
