package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.Camp;
import janggi.Point;
import janggi.board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
                .hasMessage("차는 수평 혹은 수직으로만 움직여야 합니다.");
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

    @DisplayName("차는 상하좌우로 움직일 때 기물에 막힌 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenBlocked() {
        // given
        Board board = new Board();
        board.placePiece(new Point(3, 5), new Soldier(Camp.CHU, board));
        Chariot chariot = new Chariot(Camp.CHU, board);
        Point fromPoint = new Point(3, 3);
        board.placePiece(fromPoint, chariot);
        Point toPoint = new Point(3, 7);

        // when & then
        assertThatCode(() -> chariot.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차는 기물을 넘어 이동할 수 없습니다.");
    }

    @DisplayName("특정 진영이 선택할 수 없는 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, HAN",
            "HAN, CHU",
    })
    void shouldThrowException_WhenSelectOtherCampPiece(Camp camp, Camp otherCamp) {
        // given
        Board board = new Board();
        Chariot chariot = new Chariot(otherCamp, board);

        // when & then
        assertThatCode(() -> chariot.validateSelect(camp))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다른 진영의 기물을 선택할 수 없습니다.");
    }
}
