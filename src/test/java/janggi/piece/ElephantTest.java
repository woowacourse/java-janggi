package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.Camp;
import janggi.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ElephantTest {

    @DisplayName("상은 직선으로 한 칸, 대각선으로 두 칸 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,5,7",
            "HAN,7,5",
            "HAN,3,5",
            "HAN,7,7",
            "HAN,5,5",
            "HAN,8,6",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int toX, int toY) {
        // given
        Elephant elephant = new Elephant(camp);
        Point fromPoint = new Point(5, 5);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> elephant.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상은 직선으로 한 칸, 대각선으로 두 칸 움직여야 합니다.");
    }

    @DisplayName("상은 직선으로 한 칸, 대각선으로 두 칸 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,7,8",
            "HAN,8,7",
            "HAN,7,2",
            "HAN,2,7",
            "HAN,8,3",
            "HAN,3,8",
            "HAN,3,2",
            "HAN,2,3,",
    })
    void validateMoveTest(Camp camp, int toX, int toY) {
        // given
        Elephant elephant = new Elephant(camp);
        Point fromPoint = new Point(5, 5);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> elephant.validateMove(fromPoint, toPoint))
                .doesNotThrowAnyException();
    }
}