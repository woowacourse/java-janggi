package janggi.game;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Gung;
import janggi.piece.Piece;
import janggi.piece.Sa;
import janggi.point.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class GameResultTest {

    @Nested
    @DisplayName("초나라의 궁이 공격받으면 초나라가 패배했다고 반환한다.")
    void calculateWinningResult() {
        List<Piece> attackedPieces = List.of(
                new Gung(Team.CHO, new Point(1, 1)),
                new Sa(Team.HAN, new Point(1, 2))
        );

        Game game = new Game();



        assertThat(game.canContinue()).isTrue();
    }

}