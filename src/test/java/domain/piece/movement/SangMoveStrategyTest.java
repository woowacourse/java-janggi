package domain.piece.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Move;
import domain.Moves;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;
import org.junit.jupiter.api.Test;

class SangMoveStrategyTest {

    @Test
    void 상의_가능한_움직임들을_알_수_있다() {
        // given
        SangMoveStrategy strategy = new SangMoveStrategy();
        // when
        List<Moves> possibleMoves = strategy.findPossibleMoves(new Position(1, 1), new Position(1, 1), Team.CHO);
        // then
        List<Moves> expected = List.of(
                Moves.create(Move.FRONT, Move.NO_LINE_FRONT_LEFT, Move.NO_LINE_FRONT_LEFT),
                Moves.create(Move.FRONT, Move.NO_LINE_FRONT_RIGHT, Move.NO_LINE_FRONT_RIGHT),
                Moves.create(Move.BACK, Move.NO_LINE_BACK_LEFT, Move.NO_LINE_BACK_LEFT),
                Moves.create(Move.BACK, Move.NO_LINE_BACK_RIGHT, Move.NO_LINE_BACK_RIGHT),
                Moves.create(Move.RIGHT, Move.NO_LINE_FRONT_RIGHT, Move.NO_LINE_FRONT_RIGHT),
                Moves.create(Move.RIGHT, Move.NO_LINE_BACK_RIGHT, Move.NO_LINE_BACK_RIGHT),
                Moves.create(Move.LEFT, Move.NO_LINE_FRONT_LEFT, Move.NO_LINE_FRONT_LEFT),
                Moves.create(Move.LEFT, Move.NO_LINE_BACK_LEFT, Move.NO_LINE_BACK_LEFT)
        );
        assertThat(possibleMoves).isEqualTo(expected);
    }
}