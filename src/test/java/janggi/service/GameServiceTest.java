package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.db.ConnectionManager;
import janggi.db.DatabaseInitializer;
import janggi.db.TransactionManager;
import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.domain.board.initializer.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.repository.GamePieceDaoImpl;
import janggi.repository.GameRepository;
import janggi.repository.GameStateDaoImpl;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    private static final String URL = "jdbc:h2:mem:test-db;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String TEST_DATABASE_CLEAR_FAILED = "[ERROR] 테스트 데이터베이스를 초기화할 수 없습니다.";

    private GameService gameService;
    private Board board;
    private ConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        connectionManager = new ConnectionManager(URL, USER, PASSWORD);
        new DatabaseInitializer(connectionManager).initialize();

        gameService = new GameService(
                new TransactionManager(connectionManager),
                new GameRepository(new GameStateDaoImpl(), new GamePieceDaoImpl())
        );
        board = createBoard();
    }

    @AfterEach
    void tearDown() {
        clearDatabase();
    }

    @Test
    void 새_게임을_생성하고_다시_조회할_수_있다() {
        // when
        LoadedGame createdGame = gameService.create(board);
        long id = createdGame.id();
        Game game = createdGame.game();
        LoadedGame loadedGame = gameService.findById(id).orElseThrow();
        Game foundGame = loadedGame.game();

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(id).isPositive();
            assertSoftly.assertThat(gameService.findAllIds()).containsExactly(id);
            assertSoftly.assertThat(foundGame.currentTurn()).isEqualTo(Camp.CHO);
            assertSoftly.assertThat(foundGame.boardSnapshot()).isEqualTo(game.boardSnapshot());
        });
    }

    @Test
    void 게임을_두_개_생성하면_전체_게임방_번호를_조회할_수_있다() {
        // when
        LoadedGame firstGame = gameService.create(createBoard());
        LoadedGame secondGame = gameService.create(createBoard());

        // then
        assertThat(gameService.findAllIds()).containsExactly(firstGame.id(), secondGame.id());
    }

    @Test
    void 게임을_저장하면_변경된_턴과_보드_상태가_반영된다() {
        // given
        LoadedGame createdGame = gameService.create(board);
        long id = createdGame.id();
        Game game = createdGame.game();
        Position source = new Position(3, 0);
        Position destination = new Position(4, 0);
        game.play(source, destination);

        // when
        gameService.update(id, game);
        LoadedGame loadedGame = gameService.findById(id).orElseThrow();
        Game foundGame = loadedGame.game();

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(foundGame.currentTurn()).isEqualTo(Camp.HAN);
            assertSoftly.assertThat(foundGame.boardSnapshot()).isEqualTo(game.boardSnapshot());
            assertSoftly.assertThat(foundGame.boardSnapshot()).doesNotContainKey(source);
            assertSoftly.assertThat(foundGame.boardSnapshot()).containsKey(destination);
        });
    }

    private Board createBoard() {
        return new Board(new StandardBoardInitializer(Map.of(
                Camp.HAN, ElephantSetUp.LEFT_ELEPHANT,
                Camp.CHO, ElephantSetUp.RIGHT_ELEPHANT
        )));
    }

    private void clearDatabase() {
        try (
                Connection connection = connectionManager.createConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.execute("DROP ALL OBJECTS");
        } catch (SQLException e) {
            throw new IllegalStateException(TEST_DATABASE_CLEAR_FAILED, e);
        }
    }
}
