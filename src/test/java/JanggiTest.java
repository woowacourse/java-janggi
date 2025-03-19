import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class JanggiTest {
    public static Stream<Arguments> provideXY() {
        return Stream.of(
                Arguments.of(-1, 7),
                Arguments.of(2,10),
                Arguments.of(9, -1)
        );
    }

    @DisplayName("x와 y 좌표를 가지고 있는 점을 생성한다.")
    @Test
    void createPosition() {
        // given
        int x = 1;
        int y = 2;

        // when // then
        assertThatCode(() -> new Dot(x, y))
                .doesNotThrowAnyException();

    }

    @DisplayName("x 좌표가 0부터 8까지의 범위를 가진다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8})
    void xHasBoundary(int x) {
        // given
        int y = 2;

        // when // then
        assertThatCode(() -> new Dot(x, y))
                .doesNotThrowAnyException();
    }

    @DisplayName("y 좌표가 0부터 9까지의 범위를 가진다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    void yHasBoundary(int y) {
        // given
        int x = 2;

        // when // then
        assertThatCode(() -> new Dot(x, y))
                .doesNotThrowAnyException();
    }

    @DisplayName("범위를 벗어난 점을 생성할 경우 예외 처리한다.")
    @ParameterizedTest
    @MethodSource("provideXY")
    void validateDotRange(int x, int y) {

        // when // then
        assertThatCode(() -> new Dot(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }



}
