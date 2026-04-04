package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OffLineRuleTest {

    @Test
    @DisplayName("이동 가능한 목적지 계산 테스트")
    public void execute() {
        Map<Position, Piece> positionPieceMap = Map.of(
            Position.valueOf(5, 3), new Elephant(TeamType.RED));
        Board board = new Board(positionPieceMap);
        BoardMediator boardMediator = new BoardMediatorImpl(board);
        List<OffLineMovement> movementOrder = List.of(
            new OffLineMovement(1, Direction.EAST),
            new OffLineMovement(1, Direction.NORTH_EAST),
            new OffLineMovement(1, Direction.NORTH_EAST));
        Rule ruleWithNoTraces = new OffLineRule(movementOrder);
        List<Position> expected = List.of(Position.valueOf(3, 6));

        List<Position> actual = ruleWithNoTraces.execute(Position.valueOf(5, 3), boardMediator);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

}
