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

public class RuleWithNoTracesTest {

    @Test
    @DisplayName("이동 가능한 목적지 계산 테스트")
    public void execute() {
        Map<Position, Piece> positionPieceMap = Map.of(
            Position.valueOf(5, 3), new Elephant(TeamType.RED));
        Board board = new Board(positionPieceMap);
        BoardMediator boardMediator = new BoardMediatorImpl(board);
        List<Movement> movementOrder = List.of(
            new Movement(1, Direction.valueOf(0, 1)),
            new Movement(1, Direction.valueOf(-1, 1)),
            new Movement(1, Direction.valueOf(-1, 1)));
        Rule ruleWithNoTraces = new RuleWithNoTraces(movementOrder);
        List<Position> expected = List.of(Position.valueOf(3, 6));

        List<Position> actual = ruleWithNoTraces.execute(Position.valueOf(5, 3), boardMediator);

        assertThat(actual).hasSameElementsAs(expected);
    }

}
