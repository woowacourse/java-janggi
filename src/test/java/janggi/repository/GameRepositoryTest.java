package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.db.ConnectionManager;
import janggi.db.DatabaseInitializer;
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
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRepositoryTest {

    private static final String URL = "jdbc:h2:mem:test-db;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private GameRepository gameRepository;
    private Board board;
    private Connection connection;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);

        ConnectionManager connectionManager = new ConnectionManager(dataSource);
        new DatabaseInitializer(connectionManager).initialize();
        connection = connectionManager.getConnection();

        gameRepository = new GameRepository(
                new GameStateDao(),
                new GamePieceDao()
        );
        board = createBoard();
    }

    @AfterEach
    void clear() throws SQLException {
        clearDatabase();
        connection.close();
    }

    @Test
    void 새_게임을_생성하면_게임방_번호를_반환한다() {
        // when
        long id = createGame(board);

        // then
        assertThat(id).isPositive();
    }

    @Test
    void 존재하지_않는_게임방을_조회하면_빈_값을_반환한다() {
        // when
        Optional<Game> foundGame = gameRepository.findById(connection, 1L);

        // then
        assertThat(foundGame).isEmpty();
    }

    @Test
    void 저장된_게임을_조회하면_현재_턴과_보드를_그대로_가지고_있다() {
        // given
        Game game = Game.newGame(board);
        long id = gameRepository.create(connection, game);

        // when
        Game foundGame = loadGame(id);

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(foundGame.currentTurn()).isEqualTo(Camp.CHO);
            assertSoftly.assertThat(foundGame.boardSnapshot()).isEqualTo(game.boardSnapshot());
        });
    }

    @Test
    void 전체_게임방_번호를_조회할_수_있다() {
        // when
        long firstGameId = createGame(createBoard());
        long secondGameId = createGame(createBoard());

        // then
        assertThat(gameRepository.findAllIds(connection)).containsExactly(firstGameId, secondGameId);
    }

    @Test
    void 게임을_저장하면_변경된_턴과_보드가_반영된다() {
        // given
        Game game = Game.newGame(board);
        long id = gameRepository.create(connection, game);
        Position source = new Position(3, 0);
        Position destination = new Position(4, 0);
        game.play(source, destination);

        // when
        gameRepository.update(connection, id, game);
        Game foundGame = loadGame(id);

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(foundGame.currentTurn()).isEqualTo(Camp.HAN);
            assertSoftly.assertThat(foundGame.boardSnapshot()).isEqualTo(game.boardSnapshot());
            assertSoftly.assertThat(foundGame.boardSnapshot()).doesNotContainKey(source);
            assertSoftly.assertThat(foundGame.boardSnapshot()).containsKey(destination);
        });
    }

    @Test
    void 게임을_삭제하면_조회할_수_없다() {
        // given
        long id = createGame(board);

        // when
        gameRepository.deleteById(connection, id);

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(gameRepository.findById(connection, id)).isEmpty();
            assertSoftly.assertThat(gameRepository.findAllIds(connection)).isEmpty();
        });
    }

    private Board createBoard() {
        return new Board(new StandardBoardInitializer(Map.of(
                Camp.HAN, ElephantSetUp.LEFT_ELEPHANT,
                Camp.CHO, ElephantSetUp.RIGHT_ELEPHANT
        )));
    }

    private long createGame(Board board) {
        return gameRepository.create(connection, Game.newGame(board));
    }

    private Game loadGame(long gameId) {
        return gameRepository.findById(connection, gameId).orElseThrow();
    }

    private void clearDatabase() {
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute("DROP ALL OBJECTS");
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 테스트 데이터베이스를 초기화할 수 없습니다.", e);
        }
    }
}
