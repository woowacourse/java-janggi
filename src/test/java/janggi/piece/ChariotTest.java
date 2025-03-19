package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.Camp;
import janggi.Point;
import janggi.board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {

    @DisplayName("차는 상하좌우로 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,2,2",
            "HAN,4,2",
            "HAN,2,4",
            "HAN,4,4",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        Chariot chariot = new Chariot(camp, board);
        Point fromPoint = new Point(3, 3);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> chariot.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차는 상하좌우로 움직여야 합니다.");
    }

    @DisplayName("차는 상하좌우 무제한으로 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,3,0",
            "HAN,3,5",
            "HAN,0,3",
            "HAN,5,3",
    })
    void validateMoveTest(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        Chariot chariot = new Chariot(camp, board);
        Point fromPoint = new Point(3, 3);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> chariot.validateMove(fromPoint, toPoint))
                .doesNotThrowAnyException();
    }
}
