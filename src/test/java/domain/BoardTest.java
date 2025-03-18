package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BoardTest {

    @Test
    void _9_10_보드판을_생성할_수_있다() {
        // given
        Board board = new Board();

        // then
        assertThat(board.getBoard().size())
                .isEqualTo(90);
    }

    @ParameterizedTest
    @MethodSource("providePlaceAndPiece")
    void 장기_기물의_초기_위치를_저장한다(int row, int column, Piece piece) {
        // given
        Board board = new Board();

        // when & then
        assertThat(board.getPieceFrom(row, column)).isInstanceOf(piece.getClass());
    }

    private static Stream<Arguments> providePlaceAndPiece() {
        return Stream.of(
                Arguments.of(9, 5, new 궁(Side.초)),
                Arguments.of(0, 1, new 차(Side.초)),
                Arguments.of(0, 9, new 차(Side.초)),
                Arguments.of(8, 2, new 포(Side.초)),
                Arguments.of(8, 8, new 포(Side.초)),
                Arguments.of(7, 1, new 졸병(Side.초)),
                Arguments.of(7, 3, new 졸병(Side.초)),
                Arguments.of(7, 5, new 졸병(Side.초)),
                Arguments.of(7, 7, new 졸병(Side.초)),
                Arguments.of(7, 9, new 졸병(Side.초)),
                Arguments.of(0, 4, new 사(Side.초)),
                Arguments.of(0, 6, new 사(Side.초)),
                Arguments.of(0, 2, new 마(Side.초)),
                Arguments.of(0, 8, new 마(Side.초)),
                Arguments.of(0, 3, new 상(Side.초)),
                Arguments.of(0, 7, new 상(Side.초)),
                Arguments.of(2, 5, new 궁(Side.한)),
                Arguments.of(1, 1, new 차(Side.한)),
                Arguments.of(1, 9, new 차(Side.한)),
                Arguments.of(3, 2, new 포(Side.한)),
                Arguments.of(3, 8, new 포(Side.한)),
                Arguments.of(4, 1, new 졸병(Side.한)),
                Arguments.of(4, 3, new 졸병(Side.한)),
                Arguments.of(4, 5, new 졸병(Side.한)),
                Arguments.of(4, 7, new 졸병(Side.한)),
                Arguments.of(4, 9, new 졸병(Side.한)),
                Arguments.of(1, 4, new 사(Side.한)),
                Arguments.of(1, 6, new 사(Side.한)),
                Arguments.of(1, 2, new 마(Side.한)),
                Arguments.of(1, 8, new 마(Side.한)),
                Arguments.of(1, 3, new 상(Side.한)),
                Arguments.of(1, 7, new 상(Side.한))
        );
    }
}
