package domain.manager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.exception.JanggiException;
import domain.board.Board;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JanggiGameManagerTest {

    private Player choPlayer;
    private Player hanPlayer;
    private JanggiGameManager janggiGameManager;

    @BeforeEach
    void setUp() {
        choPlayer = new Player(new Name("cho"), Team.CHO);
        hanPlayer = new Player(new Name("han"), Team.HAN);

        janggiGameManager = new JanggiGameManager(
                choPlayer,
                hanPlayer,
                Formation.SANG_MA_SANG_MA,
                Formation.SANG_MA_SANG_MA
        );
    }

    @Test
    void 현재_턴의_기물_위치를_검증하면_예외가_발생하지_않는다() {
        Position source = new Position(9, 0);
        assertDoesNotThrow(() -> janggiGameManager.validateSource(source));
    }

    @Test
    void 다른_팀의_기물_위치를_검증하면_예외가_발생한다() {
        Position source = new Position(0, 0);
        assertThrows(JanggiException.class, () -> janggiGameManager.validateSource(source));
    }

    @Test
    void 기물을_이동하면_보드가_움직이고_턴이_교체된다() {
        Position source = new Position(6, 0);
        Position destination = new Position(5, 0);

        janggiGameManager.move(source, destination);

        assertEquals(hanPlayer, janggiGameManager.getCurrentPlayer());
    }

    @Test
    void 턴을_교체하면_현재_플레이어가_바뀐다() {
        janggiGameManager.switchTurn();
        assertEquals(hanPlayer, janggiGameManager.getCurrentPlayer());

        janggiGameManager.switchTurn();
        assertEquals(choPlayer, janggiGameManager.getCurrentPlayer());
    }

    @Test
    void 게임_종료를_호출하면_진행_상태가_거짓이_된다() {
        janggiGameManager.endGame();

        assertFalse(janggiGameManager.isGameRunning());
    }

    @Test
    void fromLoadedState_불러온_보드로_게임매니저를_생성한다() {
        Board originalBoard = new JanggiGameManager(
                choPlayer, hanPlayer,
                Formation.SANG_MA_SANG_MA,
                Formation.SANG_MA_SANG_MA
        ).getBoard();

        JanggiGameManager loadedJanggiGameManager = JanggiGameManager.fromLoadedState(
                choPlayer,
                hanPlayer,
                originalBoard,
                Team.CHO
        );

        assertEquals(choPlayer, loadedJanggiGameManager.getCurrentPlayer());
        assertEquals(originalBoard, loadedJanggiGameManager.getBoard());
    }

    @Test
    void fromLoadedState_현재_차례가_HAN일때_정확히_설정된다() {
        Board originalBoard = new JanggiGameManager(
                choPlayer, hanPlayer,
                Formation.SANG_MA_SANG_MA,
                Formation.SANG_MA_SANG_MA
        ).getBoard();

        JanggiGameManager loadedJanggiGameManager = JanggiGameManager.fromLoadedState(
                choPlayer,
                hanPlayer,
                originalBoard,
                Team.HAN
        );

        assertEquals(hanPlayer, loadedJanggiGameManager.getCurrentPlayer());
    }

    @Test
    void fromLoadedState_생성_후_즉시_게임을_진행할_수_있다() {
        Board originalBoard = new JanggiGameManager(
                choPlayer, hanPlayer,
                Formation.SANG_MA_SANG_MA,
                Formation.SANG_MA_SANG_MA
        ).getBoard();

        JanggiGameManager loadedJanggiGameManager = JanggiGameManager.fromLoadedState(
                choPlayer,
                hanPlayer,
                originalBoard,
                Team.HAN
        );
        Position hanSourcePosition = new Position(1, 4);

        assertDoesNotThrow(
                () -> loadedJanggiGameManager.validateSource(hanSourcePosition));
    }
}
