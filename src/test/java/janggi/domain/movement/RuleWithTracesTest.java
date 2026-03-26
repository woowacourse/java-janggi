package janggi.domain.movement;

import static janggi.domain.Position.MAXIMUM_ROW;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.piece.Elephant;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RuleWithTracesTest {

    Board board;
    BoardMediator boardMediator;

    @Test
    @DisplayName("이동 가능한 자취 경로 계산 테스트")
    public void execute() {
        board = new Board(Map.of(Position.valueOf(8, 3), new Elephant(TeamType.BLUE)));
        boardMediator = new BoardMediatorImpl(board);
        Direction direction = Direction.valueOf(1, 0);
        Rule ruleWithTraces = new RuleWithTraces();
        List<Position> expected = List.of(Position.valueOf(6, 3), Position.valueOf(7, 3), Position.valueOf(8, 3));

        List<Position> actual = ruleWithTraces.execute(List.of(new Movement(MAXIMUM_ROW, direction, boardMediator)));

        assertThat(actual).hasSameElementsAs(expected);
    }
}
