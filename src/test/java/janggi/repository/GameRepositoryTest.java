package janggi.repository;

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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRepositoryTest {

    private static final String URL = "jdbc:h2:mem:test-db;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String TEST_DATABASE_CLEAR_FAILED = "[ERROR] 테스트 데이터베이스를 초기화할 수 없습니다.";

    private GameRepository gameRepository;
    private Board board;
    private ConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);

        connectionManager = new ConnectionManager(dataSource);
        new DatabaseInitializer(connectionManager).initialize();

        gameRepository = new GameRepository(
                new TransactionManager(connectionManager),
                new GameStateDao(),
                new GamePieceDao()
        );
        board = createBoard();
    }

    @AfterEach
    void clear() {
        clearDatabase();
    }

    @Test
    void 새_게임을_생성하고_다시_조회할_수_있다() {
        // when
        LoadedGame createdGame = gameRepository.create(board);
        long id = createdGame.id();
        Game game = createdGame.game();
        LoadedGame loadedGame = gameRepository.findById(id).get();
        Game foundGame = loadedGame.game();

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(gameRepository.findAllIds()).containsExactly(id);
            assertSoftly.assertThat(foundGame.currentTurn()).isEqualTo(Camp.CHO);
            assertSoftly.assertThat(foundGame.boardSnapshot()).isEqualTo(game.boardSnapshot());
        });
    }

    @Test
    void 게임을_두_개_생성하면_전체_게임방_번호를_조회할_수_있다() {
        // when
        LoadedGame firstGame = gameRepository.create(createBoard());
        LoadedGame secondGame = gameRepository.create(createBoard());

        // then
        assertThat(gameRepository.findAllIds()).containsExactly(firstGame.id(), secondGame.id());
    }

    @Test
    void 게임을_저장하면_변경된_턴과_보드_상태가_반영된다() {
        // given
        LoadedGame createdGame = gameRepository.create(board);
        long id = createdGame.id();
        Game game = createdGame.game();
        Position source = new Position(3, 0);
        Position destination = new Position(4, 0);
        game.play(source, destination);

        // when
        gameRepository.update(id, game);
        LoadedGame loadedGame = gameRepository.findById(id).orElseThrow();
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
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.execute("DROP ALL OBJECTS");
        } catch (SQLException e) {
            throw new IllegalStateException(TEST_DATABASE_CLEAR_FAILED, e);
        }
    }
}
