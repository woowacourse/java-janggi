package domain.piece.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Move;
import domain.Moves;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;
import org.junit.jupiter.api.Test;

class PawnMoveStrategyTest {

    @Test
    void 졸의_가능한_움직임을_알_수_있다() {
        // given
        PawnMoveStrategy pawnMoveStrategy = new PawnMoveStrategy();
        // when
        List<Moves> possibleMoves = pawnMoveStrategy.findPossibleMoves(
                new Position(1, 1), new Position(2, 1), Team.CHO);
        // then
        List<Moves> expected = List.of(
                Moves.create(Move.FRONT),
                Moves.create(Move.RIGHT),
                Moves.create(Move.LEFT),
                Moves.create(Move.FRONT_LEFT),
                Moves.create(Move.FRONT_RIGHT)
        );
        assertThat(possibleMoves).isEqualTo(expected);
    }
}