package janggiGame.arrangement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import janggiGame.board.Dot;
import janggiGame.piece.Advisor;
import janggiGame.piece.Cannon;
import janggiGame.piece.Chariot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.King;
import janggiGame.piece.Pawn;
import janggiGame.piece.Piece;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArrangementExceptHorseAndElephantTest {
    @DisplayName("마와 상을 제외한 나머지 기물을 정렬한다.")
    @Test
    void arrangeExceptHorseAndElephant_Test() {
        ArrangementExceptHorseAndElephant strategy = new ArrangementExceptHorseAndElephant();

        // when
        Map<Dot, Piece> pieces = strategy.arrange(Dynasty.HAN);

        // then
        assertThat(pieces).hasSize(12);
        assertInstanceOf(Chariot.class, pieces.get(Dot.of(0, 0)));
        assertInstanceOf(Chariot.class, pieces.get(Dot.of(8, 0)));

        assertInstanceOf(Advisor.class, pieces.get(Dot.of(3, 0)));
        assertInstanceOf(Advisor.class, pieces.get(Dot.of(5, 0)));

        assertInstanceOf(King.class, pieces.get(Dot.of(4, 1)));

        assertInstanceOf(Cannon.class, pieces.get(Dot.of(1, 2)));
        assertInstanceOf(Cannon.class, pieces.get(Dot.of(7, 2)));

        assertInstanceOf(Pawn.class, pieces.get(Dot.of(0, 3)));
        assertInstanceOf(Pawn.class, pieces.get(Dot.of(2, 3)));
        assertInstanceOf(Pawn.class, pieces.get(Dot.of(4, 3)));
        assertInstanceOf(Pawn.class, pieces.get(Dot.of(6, 3)));
        assertInstanceOf(Pawn.class, pieces.get(Dot.of(8, 3)));
    }
}