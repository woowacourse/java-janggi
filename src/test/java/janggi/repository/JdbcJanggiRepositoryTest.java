package janggi.repository;

import janggi.GameStatus;
import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class JdbcJanggiRepositoryTest {

    @Container
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0.33")
            .withDatabaseName("janggi_test")
            .withUsername("testuser")
            .withPassword("1234");

    private JdbcJanggiRepository janggiRepository;
    private Connection conn;

    @BeforeEach
    void setUp() throws SQLException {
        janggiRepository = new JdbcJanggiRepository();
        conn = DriverManager.getConnection(MYSQL.getJdbcUrl(), MYSQL.getUsername(), MYSQL.getPassword());

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Game (" +
                    "game_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "state VARCHAR(50), " +
                    "turn VARCHAR(50), " +
                    "start_date DATE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS Piece (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "game_id INT, " +
                    "type VARCHAR(50), " +
                    "team VARCHAR(50), " +
                    "`row` INT, " +
                    "col INT)");
        }
        conn.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.rollback();
            conn.close();
        }
    }

    @Test
    void 새로운_게임을_저장하면_발급된_ID를_포함한_도메인_객체를_반환한다() throws SQLException {
        // given
        Map<Position, Piece> initialPieces = new HashMap<>();
        initialPieces.put(Position.of(Row.of(1), Column.of(1)), new Piece(Team.CHO, PieceType.CHA));

        JanggiGame newGame = new JanggiGame(new Board(initialPieces));

        // when
        JanggiGame savedGame = janggiRepository.save(conn, newGame);

        // then
        assertThat(savedGame.getGameId()).isNotNull();
        assertThat(savedGame.getGameStatus()).isEqualTo(GameStatus.PROGRESS);
    }

    @Test
    void 진행_중인_게임을_조회하면_보드와_턴_정보를_완벽하게_복원한다() throws SQLException {
        // give
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(Row.of(1), Column.of(1)), new Piece(Team.CHO, PieceType.CHA));
        janggiRepository.save(conn, new JanggiGame(new Board(pieces)));

        // when
        Optional<JanggiGame> result = janggiRepository.findInProgressGame(conn);

        // then
        assertThat(result).isPresent();
        JanggiGame game = result.get();
        assertThat(game.getCurrentTeam()).isEqualTo(Team.CHO);
        assertThat(game.getGameStatus()).isEqualTo(GameStatus.PROGRESS);
        assertThat(game.getBoard().getBoard()).hasSize(1);
    }

    @Test
    void 게임_상태를_업데이트하면_기존_기물을_삭제하고_새로운_상태를_저장한다() throws SQLException {
        // give
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(Row.of(1), Column.of(1)), new Piece(Team.CHO, PieceType.CHA));
        JanggiGame savedGame = janggiRepository.save(conn, new JanggiGame(new Board(pieces)));

        Map<Position, Piece> movedPieces = new HashMap<>();
        movedPieces.put(Position.of(Row.of(1), Column.of(2)), new Piece(Team.CHO, PieceType.CHA));

        JanggiGame updatedGame = new JanggiGame(savedGame.getGameId(), new Board(movedPieces), Team.HAN, GameStatus.END);

        // when
        janggiRepository.update(conn, updatedGame);

        // then
        Optional<JanggiGame> result = janggiRepository.findInProgressGame(conn);
        assertThat(result).isEmpty();

        try (PreparedStatement pstmt = conn.prepareStatement("SELECT state FROM Game WHERE game_id = ?")) {
            pstmt.setInt(1, savedGame.getGameId());
            try (ResultSet rs = pstmt.executeQuery()) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getString("state")).isEqualTo("END");
            }
        }
    }
}
