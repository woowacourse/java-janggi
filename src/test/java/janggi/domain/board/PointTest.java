package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("포인트 테스트")
class PointTest {

    @DisplayName("포인트는 세로가 1 ~ 10 이내여야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void createPointX(int x) {
        // when, then
        assertThatCode(() -> new Point(x, 1))
                .doesNotThrowAnyException();
    }

    @DisplayName("포인트는 가로가 1 ~ 9 이내여야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9})
    void createPointY(int y) {
        // when, then
        assertThatCode(() -> new Point(1, y))
                .doesNotThrowAnyException();
    }

    @DisplayName("포인트는 가로가 1 ~ 9, 세로 1 ~ 10이 아니면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "10, 10",
            "0, 0",
            "-1, 100"
    })
    void canNotCreatePoint(int x, int y) {
        // when, then
        assertThatThrownBy(() -> new Point(x, y))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("보드 내에서 이동해야합니다.");
    }
}