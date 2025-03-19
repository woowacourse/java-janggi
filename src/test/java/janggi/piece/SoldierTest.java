package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.Camp;
import janggi.Point;
import janggi.board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    @DisplayName("군인이 뒤로 움직일 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, 2",
            "CHU, 0"
    })
    void shouldThrowException_WhenMoveBackward(Camp camp, int toY) {
        // given
        Board board = new Board();
        Soldier soldier = new Soldier(camp, board);
        Point fromPoint = new Point(0, 1);
        Point toPoint = new Point(0, toY);

        // when & then
        assertThatCode(() -> soldier.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("뒤로 갈 수 없습니다.");
    }

    @DisplayName("군인은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, 0, 1",
            "HAN, 2, 1",
            "HAN, 1, 0",
            "CHU, 0, 1",
            "CHU, 2, 1",
            "CHU, 1, 2",
    })
    void validMoveTest(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        Soldier soldier = new Soldier(camp, board);
        Point fromPoint = new Point(1, 1);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> soldier.validateMove(fromPoint, toPoint))
                .doesNotThrowAnyException();
    }

    @DisplayName("군인은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, 3, 1",
            "CHU, 0, 3"
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        Soldier soldier = new Soldier(camp, board);
        Point fromPoint = new Point(1, 1);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> soldier.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
    }

    @DisplayName("같은 위치로 이동할 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, 1, 1",
            "CHU, 1, 1"
    })
    void shouldThrowException_WhenStop(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        Soldier soldier = new Soldier(camp, board);
        Point fromPoint = new Point(1, 1);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> soldier.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("같은 위치로 이동할 수 없습니다.");
    }

    @DisplayName("같은 진영의 기물을 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCamp() {
        // given
        Board board = new Board();
        Soldier soldier = new Soldier(Camp.HAN, board);

        // when & then
        assertThatCode(() -> soldier.validateCatch(new Soldier(Camp.HAN, board)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 진영의 기물을 잡을 수 없습니다.");
    }
}
