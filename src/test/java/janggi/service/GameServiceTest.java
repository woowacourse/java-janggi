package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.db.ConnectionManager;
import janggi.db.DatabaseInitializer;
import janggi.db.TransactionManager;
import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.domain.board.initializer.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.repository.GamePieceDao;
import janggi.repository.GameRepository;
import janggi.repository.GameStateDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    private static final String URL = "jdbc:h2:mem:service-test-db;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String INVALID_GAME_ROOM = "[ERROR] 존재하지 않는 게임방 번호입니다.";

    private GameService gameService;
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

        gameService = new GameService(
                new TransactionManager(connectionManager),
                new GameRepository(
                        new GameStateDao(),
                        new GamePieceDao()
                )
        );
        board = createBoard();
    }

    @AfterEach
    void clear() throws SQLException {
        clearDatabase();
        connection.close();
    }

    @Test
    void 새_게임을_생성하면_게임방_번호를_반환하고_상태를_조회할_수_있다() {
        // when
        long gameId = gameService.createNewGame(board);
        GameStatus gameStatus = gameService.getGameStatus(gameId);

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(gameId).isPositive();
            assertSoftly.assertThat(gameStatus.currentTurn()).isEqualTo(Camp.CHO);
            assertSoftly.assertThat(gameStatus.boardSnapshot())
                    .isEqualTo(Game.newGame(board).boardSnapshot());
            assertSoftly.assertThat(gameStatus.gameEnded()).isFalse();
        });
    }

    @Test
    void 저장된_게임방이_존재하는지_검증할_수_있다() {
        // given
        long gameId = gameService.createNewGame(board);

        // when
        gameService.validateGameExists(gameId);
    }

    @Test
    void 존재하지_않는_게임방을_조회하면_예외가_발생한다() {
        assertThatThrownBy(() -> gameService.validateGameExists(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_GAME_ROOM);
    }

    @Test
    void 전체_게임방_번호를_조회할_수_있다() {
        // when
        long firstGameId = gameService.createNewGame(createBoard());
        long secondGameId = gameService.createNewGame(createBoard());

        // then
        assertThat(gameService.getAllIds()).containsExactly(firstGameId, secondGameId);
    }

    @Test
    void 게임이_끝나지_않으면_턴을_진행_후_저장한다() {
        // given
        long gameId = gameService.createNewGame(board);
        Position source = new Position(3, 0);
        Position destination = new Position(4, 0);

        // when
        GameStatus playedStatus = gameService.playEachTurn(gameId, source, destination);
        GameStatus foundGameStatus = gameService.getGameStatus(gameId);

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(playedStatus.gameEnded()).isFalse();
            assertSoftly.assertThat(playedStatus.currentTurn()).isEqualTo(Camp.HAN);
            assertSoftly.assertThat(playedStatus.boardSnapshot()).doesNotContainKey(source);
            assertSoftly.assertThat(playedStatus.boardSnapshot()).containsKey(destination);
            assertSoftly.assertThat(foundGameStatus.currentTurn()).isEqualTo(Camp.HAN);
            assertSoftly.assertThat(foundGameStatus.boardSnapshot()).isEqualTo(playedStatus.boardSnapshot());
        });
    }

    @Test
    void 장군을_잡으면_게임을_삭제한다() {
        // given
        long gameId = gameService.createNewGame(createEndingBoard());
        Position source = new Position(0, 0);
        Position destination = new Position(0, 4);

        // when
        GameStatus playedStatus = gameService.playEachTurn(gameId, source, destination);

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(playedStatus.gameEnded()).isTrue();
            assertSoftly.assertThat(playedStatus.currentTurn()).isEqualTo(Camp.CHO);
            assertSoftly.assertThat(gameService.getAllIds()).isEmpty();
            assertSoftly.assertThatThrownBy(() -> gameService.getGameStatus(gameId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(INVALID_GAME_ROOM);
        });
    }

    private Board createBoard() {
        return new Board(new StandardBoardInitializer(Map.of(
                Camp.HAN, ElephantSetUp.LEFT_ELEPHANT,
                Camp.CHO, ElephantSetUp.RIGHT_ELEPHANT
        )));
    }

    private Board createEndingBoard() {
        return new Board(() -> Map.of(
                new Position(0, 0), new Piece(PieceType.CHARIOT, Camp.CHO),
                new Position(0, 4), new Piece(PieceType.GENERAL, Camp.HAN)
        ));
    }

    private void clearDatabase() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP ALL OBJECTS");
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 테스트 데이터베이스를 초기화할 수 없습니다.", e);
        }
    }
}
