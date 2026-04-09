package database;

import static org.assertj.core.api.Assertions.assertThat;

import database.entity.GameEntity;
import database.jdbc.DatabaseConnector;
import database.jdbc.DatabaseInitializer;
import database.jdbc.JdbcGameDao;
import database.jdbc.JdbcPieceDao;
import domain.game.Team;
import domain.piece.Piece;
import domain.piece.PieceDefinition;
import domain.position.Position;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class GameRepositoryTest {

    @Container
    static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("jangi-test")
            .withUsername("TESTUSER")
            .withPassword("1234");
    private DatabaseConnector connector;
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() throws SQLException {
        connector = new DatabaseConnector(mysql.getJdbcUrl(), mysql.getUsername(), mysql.getPassword());
        DatabaseInitializer.initialize(connector);
        gameRepository = new GameRepository(new JdbcGameDao(connector), new JdbcPieceDao(connector));
        cleanUp();
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanUp();
    }

    private void cleanUp() throws SQLException {
        try (Connection conn = connector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM piece");
            stmt.execute("DELETE FROM game");
        }
    }

    @Test
    void 진행중인_게임이_없으면_빈_Optional이_반환된다() {
        Optional<GameEntity> result = gameRepository.findLatestGame();

        assertThat(result).isEmpty();
    }

    @Test
    void 새_게임을_시작하면_게임_ID가_반환된다() {
        int gameId = gameRepository.startNewGame(Team.HAN, createPieces());

        assertThat(gameId).isPositive();
    }

    @Test
    void 게임_시작_후_최근_게임을_조회할_수_있다() {
        gameRepository.startNewGame(Team.HAN, createPieces());

        Optional<GameEntity> result = gameRepository.findLatestGame();

        assertThat(result).isPresent();
        assertThat(result.get().currentTurn()).isEqualTo(Team.HAN);
    }

    @Test
    void 기물을_저장하고_불러올_수_있다() {
        Map<Position, Piece> pieces = createPieces();
        int gameId = gameRepository.startNewGame(Team.HAN, pieces);

        Map<Position, Piece> loaded = gameRepository.loadPieces(gameId);

        assertThat(loaded).hasSize(pieces.size());
        pieces.forEach((pos, piece) -> {
            assertThat(loaded).containsKey(pos);
            assertThat(loaded.get(pos).getType()).isEqualTo(piece.getType());
            assertThat(loaded.get(pos).getTeam()).isEqualTo(piece.getTeam());
        });
    }

    @Test
    void 이동_후_기물_위치가_업데이트된다() {
        Position src = new Position(1, 1);
        Position dest = new Position(1, 3);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(src, PieceDefinition.CHA.createPiece(Team.HAN));
        int gameId = gameRepository.startNewGame(Team.HAN, pieces);

        gameRepository.saveMove(gameId, src, dest, false, Team.CHO);

        Map<Position, Piece> loaded = gameRepository.loadPieces(gameId);
        assertThat(loaded).containsKey(dest);
        assertThat(loaded).doesNotContainKey(src);
    }

    @Test
    void 이동_후_턴이_변경된다() {
        Position src = new Position(1, 1);
        Position dest = new Position(1, 3);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(src, PieceDefinition.CHA.createPiece(Team.HAN));
        int gameId = gameRepository.startNewGame(Team.HAN, pieces);

        gameRepository.saveMove(gameId, src, dest, false, Team.CHO);

        Optional<GameEntity> result = gameRepository.findLatestGame();
        assertThat(result.get().currentTurn()).isEqualTo(Team.CHO);
    }

    @Test
    void 이동시_목적지_기물이_삭제된다() {
        Position src = new Position(1, 1);
        Position dest = new Position(1, 3);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(src, PieceDefinition.CHA.createPiece(Team.HAN));
        pieces.put(dest, PieceDefinition.BYEONG.createPiece(Team.CHO));
        int gameId = gameRepository.startNewGame(Team.HAN, pieces);

        gameRepository.saveMove(gameId, src, dest, true, Team.CHO);

        Map<Position, Piece> loaded = gameRepository.loadPieces(gameId);
        assertThat(loaded).hasSize(1);
        assertThat(loaded.get(dest).getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 게임_삭제_후_조회되지_않는다() {
        int gameId = gameRepository.startNewGame(Team.HAN, createPieces());

        gameRepository.deleteGame(gameId);

        assertThat(gameRepository.findLatestGame()).isEmpty();
    }

    @Test
    void 게임_삭제시_기물도_함께_삭제된다() {
        int gameId = gameRepository.startNewGame(Team.HAN, createPieces());

        gameRepository.deleteGame(gameId);

        assertThat(gameRepository.loadPieces(gameId)).isEmpty();
    }

    private Map<Position, Piece> createPieces() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(1, 1), PieceDefinition.CHA.createPiece(Team.HAN));
        pieces.put(new Position(1, 9), PieceDefinition.CHA.createPiece(Team.HAN));
        pieces.put(new Position(10, 1), PieceDefinition.CHA.createPiece(Team.CHO));
        return pieces;
    }
}
