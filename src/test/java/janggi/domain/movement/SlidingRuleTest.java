package janggi.domain.movement;

import static janggi.domain.Position.MAXIMUM_ROW;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SlidingRuleTest {

    @Test
    @DisplayName("이동 가능한 자취 경로 계산 테스트")
    public void execute() {
        Map<Position, Piece> positionPieceMap = Map.of(
            Position.valueOf(5, 3), new Chariot(TeamType.RED),
            Position.valueOf(8, 3), new Elephant(TeamType.BLUE));
        Board board = new Board(positionPieceMap);
        BoardMediator boardMediator = new BoardMediatorImpl(board);
        Direction direction = Direction.SOUTH;
        Rule slidingRule = new SlidingRule(
            List.of(new SlidingMovement(MAXIMUM_ROW, direction)));
        List<Position> expected = List.of(Position.valueOf(6, 3), Position.valueOf(7, 3),
            Position.valueOf(8, 3));

        List<Position> actual = slidingRule.execute(Position.valueOf(5, 3), boardMediator);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
