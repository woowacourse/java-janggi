package domain.manager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.exception.JanggiException;
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
}