package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.db.DatabaseInitializer;
import janggi.db.TestConnectionManager;
import janggi.db.TransactionManager;
import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.domain.board.initializer.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.repository.GamePieceRepository;
import janggi.repository.GameStateRepository;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    private GameService gameService;
    private Board board;
    private TestConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        connectionManager = new TestConnectionManager();
        new DatabaseInitializer(connectionManager).initialize();

        gameService = new GameService(
                new TransactionManager(connectionManager),
                new GameStateRepository(),
                new GamePieceRepository()
        );
        board = createBoard();
    }

    @AfterEach
    void tearDown() {
        connectionManager.clear();
    }

    @Test
    void 새_게임을_생성하고_다시_조회할_수_있다() {
        // when
        Game createdGame = gameService.create(board);
        Game loadedGame = gameService.findById(createdGame.id()).orElseThrow();

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(createdGame.id()).isPositive();
            assertSoftly.assertThat(gameService.findAllIds()).containsExactly(createdGame.id());
            assertSoftly.assertThat(loadedGame.currentTurn()).isEqualTo(Camp.CHO);
            assertSoftly.assertThat(loadedGame.boardSnapshot()).isEqualTo(createdGame.boardSnapshot());
        });
    }

    @Test
    void 게임을_두_개_생성하면_전체_게임방_번호를_조회할_수_있다() {
        // when
        Game firstGame = gameService.create(createBoard());
        Game secondGame = gameService.create(createBoard());

        // then
        assertThat(gameService.findAllIds()).containsExactly(firstGame.id(), secondGame.id());
    }

    @Test
    void 게임을_저장하면_변경된_턴과_보드_상태가_반영된다() {
        // given
        Game createdGame = gameService.create(board);
        Position source = new Position(3, 0);
        Position destination = new Position(4, 0);
        createdGame.play(source, destination);

        // when
        gameService.save(createdGame);
        Game loadedGame = gameService.findById(createdGame.id()).orElseThrow();

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(loadedGame.currentTurn()).isEqualTo(Camp.HAN);
            assertSoftly.assertThat(loadedGame.boardSnapshot()).isEqualTo(createdGame.boardSnapshot());
            assertSoftly.assertThat(loadedGame.boardSnapshot()).doesNotContainKey(source);
            assertSoftly.assertThat(loadedGame.boardSnapshot()).containsKey(destination);
        });
    }

    private Board createBoard() {
        return new Board(new StandardBoardInitializer(Map.of(
                Camp.HAN, ElephantSetUp.LEFT_ELEPHANT,
                Camp.CHO, ElephantSetUp.RIGHT_ELEPHANT
        )));
    }
}
