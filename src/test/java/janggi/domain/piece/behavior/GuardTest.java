package janggi.domain.piece.behavior;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.move.Position;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GuardTest {

    @DisplayName("사가 움직일 수 있는 포지션들을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"5,5,8", "10,5,5", "10,9,3"})
    void test1(int row, int column, int expected) {
        // given
        Position position = Position.of(row, column);
        Guard guard = new Guard();
        Piece piece = new Piece(Side.HAN, guard);

        Map<Position, Piece> map = Map.of(position, piece);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = guard.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).hasSize(expected);
    }
}
