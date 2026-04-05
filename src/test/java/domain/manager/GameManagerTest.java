package domain.manager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.exception.JanggiException;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameManagerTest {

    private Player choPlayer;
    private Player hanPlayer;
    private GameManager gameManager;

    @BeforeEach
    void setUp() {
        choPlayer = new Player(new Name("cho"), Team.CHO);
        hanPlayer = new Player(new Name("han"), Team.HAN);

        gameManager = new GameManager(
                choPlayer,
                hanPlayer,
                Formation.SANG_MA_SANG_MA,
                Formation.SANG_MA_SANG_MA
        );
    }

    @Test
    void 현재_턴의_기물_위치를_검증하면_예외가_발생하지_않는다() {
        Position source = new Position(9, 0);
        assertDoesNotThrow(() -> gameManager.validateSource(source));
    }

    @Test
    void 다른_팀의_기물_위치를_검증하면_예외가_발생한다() {
        Position source = new Position(0, 0);
        assertThrows(JanggiException.class, () -> gameManager.validateSource(source));
    }

    @Test
    void 기물을_이동하면_보드가_움직이고_턴이_교체된다() {
        Position source = new Position(6, 0);
        Position destination = new Position(5, 0);

        gameManager.move(source, destination);

        assertEquals(hanPlayer, gameManager.getCurrentPlayer());
    }

    @Test
    void 턴을_교체하면_현재_플레이어가_바뀐다() {
        gameManager.switchTurn();
        assertEquals(hanPlayer, gameManager.getCurrentPlayer());

        gameManager.switchTurn();
        assertEquals(choPlayer, gameManager.getCurrentPlayer());
    }

    @Test
    void 게임_종료를_호출하면_진행_상태가_거짓이_된다() {
        gameManager.endGame();

        assertFalse(gameManager.isGameRunning());
    }

    @Test
    void fromLoadedState_불러온_보드로_게임매니저를_생성한다() {
        Board originalBoard = new GameManager(
                choPlayer, hanPlayer,
                Formation.SANG_MA_SANG_MA,
                Formation.SANG_MA_SANG_MA
        ).getBoard();

        GameManager loadedGameManager = GameManager.fromLoadedState(
                choPlayer,
                hanPlayer,
                originalBoard,
                Team.CHO
        );

        assertEquals(choPlayer, loadedGameManager.getCurrentPlayer());
        assertEquals(originalBoard, loadedGameManager.getBoard());
    }

    @Test
    void fromLoadedState_현재_차례가_HAN일때_정확히_설정된다() {
        Board originalBoard = new GameManager(
                choPlayer, hanPlayer,
                Formation.SANG_MA_SANG_MA,
                Formation.SANG_MA_SANG_MA
        ).getBoard();

        GameManager loadedGameManager = GameManager.fromLoadedState(
                choPlayer,
                hanPlayer,
                originalBoard,
                Team.HAN
        );

        assertEquals(hanPlayer, loadedGameManager.getCurrentPlayer());
    }

    @Test
    void fromLoadedState_생성_후_즉시_게임을_진행할_수_있다() {
        Board originalBoard = new GameManager(
                choPlayer, hanPlayer,
                Formation.SANG_MA_SANG_MA,
                Formation.SANG_MA_SANG_MA
        ).getBoard();

        GameManager loadedGameManager = GameManager.fromLoadedState(
                choPlayer,
                hanPlayer,
                originalBoard,
                Team.HAN
        );

        // HAN 차례이므로 HAN의 기물 위치(1,4) 검증은 성공해야 함
        Position hanSourcePosition = new Position(1, 4);  // HAN JANG 위치

        assertDoesNotThrow(
                () -> loadedGameManager.validateSource(hanSourcePosition));
    }
}
