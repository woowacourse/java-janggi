package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Side;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GeneralTest {

    @DisplayName("궁이 움직일 수 있는 포지션들을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"5,5,8", "1,5,5", "10,1,3"})
    void test1(int row, int column, int expected) {
        // given
        Position position = Position.of(row, column);
        General general = new General();
        Piece piece = new Piece(Side.HAN, general);

        Map<Position, Piece> map = Map.of(position, piece);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = general.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual.size()).isEqualTo(expected);
    }
}
