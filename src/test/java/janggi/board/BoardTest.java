package janggi.board;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.Camp;
import janggi.Point;
import janggi.piece.Elephant;
import janggi.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardTest {

    @DisplayName("특정 좌표에 기물을 둘 수 있다.")
    @Test
    void placePieceTest() {
        // given
        Board board = new Board();
        Point point = new Point(1, 1);
        Piece piece = new Elephant(Camp.HAN);

        // when & then
        assertThatCode(() -> board.placePiece(point, piece))
                .doesNotThrowAnyException();
    }

    @DisplayName("영역 밖으로 기물을 둘 때 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "9,11",
            "9,0",
            "10,10",
            "0,10"
    })
    void shouldThrowException_WhenInvalidPoint(int x, int y) {
        // given
        Board board = new Board();
        Point point = new Point(x, y);
        Piece piece = new Elephant(Camp.HAN);

        // when & then
        assertThatCode(() -> board.placePiece(point, piece))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물의 위치는 9 x 10 영역을 벗어날 수 없습니다.");
    }
}
