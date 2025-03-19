package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.Camp;
import janggi.Point;
import janggi.board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HorseTest {

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,5,7",
            "HAN,7,5",
            "HAN,3,5",
            "HAN,7,7",
            "HAN,5,5",
            "HAN,6,8",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        Horse horse = new Horse(camp, board);
        Point fromPoint = new Point(5, 5);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> horse.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
    }

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,6,7",
            "HAN,7,6",
            "HAN,7,4",
            "HAN,6,3",
            "HAN,4,7",
            "HAN,3,6",
            "HAN,3,4",
            "HAN,4,3,",
    })
    void validateMoveTest(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        Horse horse = new Horse(camp, board);
        Point fromPoint = new Point(5, 5);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> horse.validateMove(fromPoint, toPoint))
                .doesNotThrowAnyException();
    }

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직일 때 기물에 막힌 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,6,7",
            "HAN,7,4",
            "HAN,6,3",
            "HAN,3,4",
    })
    void shouldThrowException_WhenBlocked(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        Horse horse = new Horse(camp, board);
        Point fromPoint = new Point(5, 5);
        Point toPoint = new Point(toX, toY);
        board.placePiece(new Point(5, 6), new Soldier(Camp.CHU, board));
        board.placePiece(new Point(6, 5), new Soldier(Camp.CHU, board));
        board.placePiece(new Point(4, 5), new Soldier(Camp.CHU, board));
        board.placePiece(new Point(5, 4), new Soldier(Camp.CHU, board));

        // when & then
        assertThatCode(() -> horse.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("마는 기물을 넘어서 이동할 수 없습니다.");
    }
}
