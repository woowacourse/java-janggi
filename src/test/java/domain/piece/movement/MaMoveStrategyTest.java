package domain.piece.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Move;
import domain.Moves;
import java.util.List;
import org.junit.jupiter.api.Test;

class MaMoveStrategyTest {

    @Test
    void 마가_가능한_움직임을_알_수_있다() {
        // given
        MaMoveStrategy strategy = new MaMoveStrategy();
        // when
        List<Moves> possibleMoves = strategy.findPossibleMoves(null, null, null);
        // then
        List<Moves> expected = List.of(
                Moves.create(Move.FRONT, Move.NO_LINE_FRONT_LEFT),
                Moves.create(Move.FRONT, Move.NO_LINE_FRONT_RIGHT),
                Moves.create(Move.BACK, Move.NO_LINE_BACK_LEFT),
                Moves.create(Move.BACK, Move.NO_LINE_BACK_RIGHT),
                Moves.create(Move.RIGHT, Move.NO_LINE_FRONT_RIGHT),
                Moves.create(Move.RIGHT, Move.NO_LINE_BACK_RIGHT),
                Moves.create(Move.LEFT, Move.NO_LINE_FRONT_LEFT),
                Moves.create(Move.LEFT, Move.NO_LINE_BACK_LEFT)
        );
        assertThat(possibleMoves).isEqualTo(expected);
    }
}