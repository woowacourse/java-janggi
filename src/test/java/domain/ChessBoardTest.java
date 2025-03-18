package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ChessBoardTest {

    @Test
    void _9_10_보드판을_생성할_수_있다() {
        // given
        ChessBoard chessBoard = new ChessBoard();

        // then
        assertThat(chessBoard.getBoard().size())
                .isEqualTo(90);
    }

    @ParameterizedTest
    @MethodSource("providePlaceAndPiece")
    void 장기_기물의_초기_위치를_저장한다(int row, int column, Piece piece) {
        // given
        ChessBoard chessBoard = new ChessBoard();

        // when
        chessBoard.initBoard();

        // then
        assertThat(chessBoard.getPieceFrom(row, column)).isInstanceOf(piece.getClass());
    }

    private static Stream<Arguments> providePlaceAndPiece() {
        return Stream.of(
                Arguments.of(9, 5, new 궁()),
                Arguments.of(0, 1, new 차()),
                Arguments.of(0, 9, new 차()),
                Arguments.of(8, 2, new 포()),
                Arguments.of(8, 8, new 포()),
                Arguments.of(7, 1, new 졸병()),
                Arguments.of(7, 3, new 졸병()),
                Arguments.of(7, 5, new 졸병()),
                Arguments.of(7, 7, new 졸병()),
                Arguments.of(7, 9, new 졸병()),
                Arguments.of(0, 4, new 사()),
                Arguments.of(0, 6, new 사())
        );
    }
}
