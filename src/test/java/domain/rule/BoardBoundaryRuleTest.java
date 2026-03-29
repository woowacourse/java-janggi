package domain.rule;

import domain.board.Board;
import domain.board.Side;
import domain.coordinate.Position;
import domain.piece.Pawn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BoardBoundaryRuleTest {

    @Test
    @DisplayName("장기판 범위를 벗어나면 이동할 수 없다.")
    void boundaryTest() {
        // given
        Board board = new Board(Map.of());
        BoardBoundaryRule rule = new BoardBoundaryRule();

        Position start = new Position(9, 4);
        Position dest = new Position(10, 4);

        // when
        boolean result = rule.isValid(board, start, dest, new Pawn(Side.HAN));

        // then
        assertThat(result).isFalse();
    }
}
