package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Point;
import janggi.camp.Camp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GeneralTest {

    @DisplayName("장군은 궁성을 벗어나는 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({"2, 0", "2, 1", "2, 2", "3, 3", "4, 3", "5, 3"})
    void shouldThrowException_WhenPointOutOfPalace(int toX, int toY) {
        // given
        General general = new General(Camp.CHU);
        Point from = new Point(4, 0);
        Point to = new Point(toX, toY);

        // when & then
        assertThatCode(() -> general.validateMove(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장군은 궁성 밖을 나갈 수 없습니다.");
    }

    @DisplayName("장군은 상하좌우, 대각선으로 한 칸만 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({"3, 2", "4, 2", "5, 2"})
    void shouldThrowException_WhenInvalidMove(int toX, int toY) {
        // given
        General general = new General(Camp.CHU);
        Point from = new Point(4, 0);
        Point to = new Point(toX, toY);

        // when & then
        assertThatCode(() -> general.validateMove(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장군은 상하좌우, 대각선으로 한 칸만 움직일 수 있습니다.");
    }

    @DisplayName("장군은 상하좌우, 대각선으로 한 칸만 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({"3, 0", "3, 1", "4, 1", "5, 0", "5, 1"})
    void validateMoveTest(int toX, int toY) {
        // given
        General general = new General(Camp.CHU);
        Point from = new Point(4, 0);
        Point to = new Point(toX, toY);

        // when & then
        assertThatCode(() -> general.validateMove(from, to))
                .doesNotThrowAnyException();
    }
}
