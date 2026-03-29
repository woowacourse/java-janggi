package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StepMoveRuleTest {

    @Test
    @DisplayName("이동 가능한 목적지 계산 테스트")
    void execute() {
        // given
        Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 3), new Elephant(TeamType.RED)
        );
        Board board = new Board(positionPieceMap);
        List<Movement> movementOrder = List.of(
                new Movement(1, Direction.RIGHT),
                new Movement(2, Direction.UP_RIGHT)
        );
        MoveRule moveRuleWithNoTraces = new StepMoveRule(movementOrder);
        Position from = Position.valueOf(5, 3);

        // when
        List<Position> actual = moveRuleWithNoTraces.execute(from, board);

        // then
        List<Position> expected = List.of(Position.valueOf(3, 6));
        assertThat(actual).hasSameElementsAs(expected);
    }
}
