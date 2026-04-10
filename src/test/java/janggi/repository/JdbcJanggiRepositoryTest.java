package janggi.repository;

import janggi.domain.GameStatus;
import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.dto.GameInformationDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
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
                    "turn VARCHAR(50))");

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
    void 게임을_저장하면_ID가_발급되고_기물_정보가_DB에_기록된다() {
        // give
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(Row.of(0), Column.of(0)), new Piece(Team.CHO, PieceType.CHA));
        JanggiGame game = new JanggiGame(new Board(pieces));

        // when
        JanggiGame savedGame = janggiRepository.save(conn, game);

        // then
        assertThat(savedGame.getGameId()).isGreaterThan(0);
        Optional<JanggiGame> found = janggiRepository.findById(conn, savedGame.getGameId());
        assertThat(found).isPresent();
        assertThat(found.get().getBoard().getBoard()).hasSize(1);
    }

    @Test
    void 게임_상태와_턴을_업데이트하면_DB의_기물_정보가_최신_상태로_갱신된다() {
        // give
        Map<Position, Piece> pieces = new HashMap<>();
        Position oldPos = Position.of(Row.of(0), Column.of(0));
        pieces.put(oldPos, new Piece(Team.CHO, PieceType.CHA));
        JanggiGame savedGame = janggiRepository.save(conn, new JanggiGame(new Board(pieces)));

        Map<Position, Piece> newPieces = new HashMap<>();
        Position newPos = Position.of(Row.of(0), Column.of(1));
        newPieces.put(newPos, new Piece(Team.CHO, PieceType.CHA));

        JanggiGame updatedGame = new JanggiGame(savedGame.getGameId(), new Board(newPieces), Team.HAN, GameStatus.END);

        // when
        janggiRepository.update(conn, updatedGame);

        // then
        JanggiGame result = janggiRepository.findById(conn, savedGame.getGameId()).get();
        assertThat(result.getCurrentTeam()).isEqualTo(Team.HAN);
        assertThat(result.getGameStatus()).isEqualTo(GameStatus.END);
        assertThat(result.getBoard().getBoard()).containsKey(newPos);
        assertThat(result.getBoard().getBoard()).doesNotContainKey(oldPos);
    }

    @Test
    void findAll_조회_시_DTO_매퍼를_통해_한글로_변환된_정보를_가져온다() {
        // give
        Map<Position, Piece> pieces = new HashMap<>();
        janggiRepository.save(conn, new JanggiGame(new Board(pieces)));

        // when
        List<GameInformationDto> games = janggiRepository.findAll(conn);

        // then
        assertThat(games).isNotEmpty();
        GameInformationDto dto = games.get(0);
        assertThat(dto.status()).isEqualTo("진행 중");
        assertThat(dto.turn()).isEqualTo("초");
    }

    @Test
    void 존재하지_않는_ID로_조회하면_빈_Optional을_반환한다() {
        // when
        Optional<JanggiGame> result = janggiRepository.findById(conn, 9999);

        // then
        assertThat(result).isEmpty();
    }
}
