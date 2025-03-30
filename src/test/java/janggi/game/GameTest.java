package janggi.game;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.movement.target.AttackedPiece;
import janggi.piece.Gung;
import janggi.piece.Sa;
import janggi.point.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    @DisplayName("초나라의 궁이 공격받으면 초나라가 패배했다고 반환한다.")
    void calculateWinningResult() {
        List<AttackedPiece> attackedPieces = List.of(
                new AttackedPiece(new Gung(Team.CHO, new Point(1, 1))),
                new AttackedPiece(new Sa(Team.HAN, new Point(1, 2)))
        );

        Game game = new Game(Board.init(), attackedPieces, Team.CHO, null);

        assertThat(game.canContinue()).isFalse();
    }
}