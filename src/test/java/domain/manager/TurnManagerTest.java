package domain.manager;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.exception.JanggiException;
import domain.piece.Jol;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import org.junit.jupiter.api.Test;

class TurnManagerTest {

    @Test
    void 현재_플레이어_팀과_같은_팀이면_예외가_발생하지_않는다() {
        Player choPlayer = new Player(new Name("cho"), Team.CHO);
        Player hanPlayer = new Player(new Name("han"), Team.HAN);
        TurnManager turnManager = new TurnManager(choPlayer, hanPlayer);

        assertDoesNotThrow(() -> turnManager.validateTurn(Team.CHO));
    }

    @Test
    void 현재_플레이어_팀과_다른_팀이면_예외가_발생한다() {
        Player choPlayer = new Player(new Name("cho"), Team.CHO);
        Player hanPlayer = new Player(new Name("han"), Team.HAN);
        TurnManager turnManager = new TurnManager(choPlayer, hanPlayer);

        assertThrows(JanggiException.class, () -> turnManager.validateTurn(Team.HAN));
    }

    @Test
    void 턴을_변경하면_현재_플레이어가_바뀐다() {
        Player choPlayer = new Player(new Name("cho"), Team.CHO);
        Player hanPlayer = new Player(new Name("han"), Team.HAN);
        TurnManager turnManager = new TurnManager(choPlayer, hanPlayer);

        turnManager.switchTurn();

        assertEquals(hanPlayer, turnManager.getCurrentPlayer());
    }

    @Test
    void 기물을_포획하면_현재_플레이어의_포획목록에_추가된다() {
        Player choPlayer = new Player(new Name("cho"), Team.CHO);
        Player hanPlayer = new Player(new Name("han"), Team.HAN);
        TurnManager turnManager = new TurnManager(choPlayer, hanPlayer);
        Jol capturedPiece = new Jol(Team.HAN);

        turnManager.capturePiece(capturedPiece);

        assertEquals(1, choPlayer.getCaughtPiece().size());
        assertEquals(capturedPiece, choPlayer.getCaughtPiece().getFirst());
    }

    @Test
    void 게임종료를_호출하면_진행상태가_false가_된다() {
        Player choPlayer = new Player(new Name("cho"), Team.CHO);
        Player hanPlayer = new Player(new Name("han"), Team.HAN);
        TurnManager turnManager = new TurnManager(choPlayer, hanPlayer);

        turnManager.endGame();

        assertFalse(turnManager.isGameRunning());
    }
}
