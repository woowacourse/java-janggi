package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Optional;

import janggi.domain.board.coordinate.Point;
import janggi.domain.game.GameResult;
import janggi.domain.game.GameSession;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;
import janggi.repository.FakeGameRepository;
import janggi.repository.FakeTransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    private FakeGameRepository fakeGameRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        fakeGameRepository = new FakeGameRepository();
        gameService = new GameService(new FakeTransactionManager(), fakeGameRepository);
    }

    @Test
    @DisplayName("createGame(): 진행 중인 게임 ID를 조회할 수 있다")
    void createGame() {
        GameSession session = gameService.createGame(
                (side) -> Map.of(Point.of(0, 0), new Soldier(Side.CHO)),
                (side) -> Map.of(Point.of(9, 0), new Soldier(Side.HAN))
        );

        Optional<Integer> activeId = gameService.findActiveGameId();

        assertThat(activeId).isPresent();
        assertThat(activeId.get()).isEqualTo(session.gameId());
    }

    @Test
    @DisplayName("move(): 이동 후 repository에 변경된 상태가 저장된다")
    void move() {
        GameSession session = gameService.createGame(
                (side) -> Map.of(Point.of(0, 0), new Soldier(Side.CHO)),
                (side) -> Map.of(Point.of(9, 0), new Soldier(Side.HAN))
        );

        gameService.move(session, Point.of(0, 0), Point.of(1, 0));

        assertThat(fakeGameRepository.load(session.gameId()).getBoard())
                .doesNotContainKey(Point.of(0, 0));
    }

    @Test
    @DisplayName("move(): 상대 진영의 궁을 잡으면 게임 결과를 반환하고 종료 처리된다")
    void move_gameOver() {
        GameSession session = gameService.createGame(
                (side) -> Map.of(Point.of(0, 0), new Soldier(Side.CHO)),
                (side) -> Map.of(Point.of(1, 0), new General(Side.HAN))
        );

        GameResult result = gameService.move(session, Point.of(0, 0), Point.of(1, 0));

        assertThat(result.isGameOver()).isTrue();
        assertThat(result.getWinner()).isEqualTo(Side.CHO);
        assertThat(gameService.findActiveGameId()).isEmpty();
    }

    @Test
    @DisplayName("finish(): 게임이 종료되면 종료 처리된다.")
    void finish() {
        GameSession session = gameService.createGame(
                (side) -> Map.of(Point.of(0, 0), new Soldier(Side.CHO)),
                (side) -> Map.of(Point.of(9, 0), new Soldier(Side.HAN))
        );

        gameService.finish(session.gameId(), Side.NONE);

        assertThat(gameService.findActiveGameId()).isEmpty();
    }
}
