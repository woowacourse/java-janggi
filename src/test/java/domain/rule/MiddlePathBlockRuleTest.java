package domain.rule;

import domain.board.Board;
import domain.board.Side;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class MiddlePathBlockRuleTest {

    List<List<Direction>> PATHS = List.of(
            List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT)
    );

    @Test
    @DisplayName("중간 경로가 비어있으면 이동 가능하다.")
    void pass_Test() {
        // given

        MiddlePathBlockRule rule = new MiddlePathBlockRule(PATHS);

        Position start = new Position(4, 4);
        Board board = new Board(Map.of());
        Position dest = new Position(1, 2);
        Piece piece = mock(Piece.class);

        // when
        boolean result = rule.isValid(board, start, dest, piece);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("1차 중간 경로가 막히면 이동 할 수 없다.")
    void firstBlock_Test() {
        // given

        MiddlePathBlockRule rule = new MiddlePathBlockRule(PATHS);

        Position start = new Position(4, 4);

        Map<Position, Piece> map = new HashMap<>();
        map.put(new Position(3, 4), new Pawn(Side.HAN));
        Board board = new Board(map);
        Position dest = new Position(1, 2);
        Piece piece = mock(Piece.class);

        // when
        boolean result = rule.isValid(board, start, dest, piece);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("2차 중간 경로가 막히면 이동 할 수 없다.")
    void secondBlock_Test() {
        // given
        MiddlePathBlockRule rule = new MiddlePathBlockRule(PATHS);

        Position start = new Position(4, 4);
        Map<Position, Piece> map = new HashMap<>();
        map.put(new Position(2, 3), new Pawn(Side.HAN));
        Board board = new Board(map);
        Position dest = new Position(1, 2);
        Piece piece = mock(Piece.class);

        // when
        boolean result = rule.isValid(board, start, dest, piece);

        // then
        assertThat(result).isFalse();
    }
}
