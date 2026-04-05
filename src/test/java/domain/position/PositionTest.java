package domain.position;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PositionTest {
    @ParameterizedTest
    @MethodSource("goValues")
    void 기존_위치에서_이동된_위치를_구할_수_있다(Position start, int moveRow, int moveColumn, Position result) {
        Assertions.assertThat(start.go(moveRow, moveColumn)).isEqualTo(result);
    }

    private static Stream<Arguments> goValues() {
        return Stream.of(
                Arguments.of(Position.of(1, 2), 2, 3, Position.of(3, 5)),
                Arguments.of(Position.of(3, 6), -2, 1, Position.of(1, 7))
        );
    }

    @ParameterizedTest
    @MethodSource("isSameRowValues")
    void 같은_Row인지_확인할_수_있다(Position source, Position destination, boolean result) {
        Assertions.assertThat(source.isSameRow(destination)).isEqualTo(result);
    }

    private static Stream<Arguments> isSameRowValues() {
        return Stream.of(
                Arguments.of(Position.of(1, 2), Position.of(1, 5), true),
                Arguments.of(Position.of(1, 2), Position.of(2, 5), false)
        );
    }

    @ParameterizedTest
    @MethodSource("isSameColumnValues")
    void 같은_Column인지_확인할_수_있다(Position source, Position destination, boolean result) {
        Assertions.assertThat(source.isSameColumn(destination)).isEqualTo(result);
    }

    private static Stream<Arguments> isSameColumnValues() {
        return Stream.of(
                Arguments.of(Position.of(1, 2), Position.of(1, 2), true),
                Arguments.of(Position.of(1, 2), Position.of(2, 5), false)
        );
    }

    @ParameterizedTest
    @MethodSource("getVerticalPathExcludeDestination")
    void 같은_Column의_Row_위치를_구할_수_있다(Position source, Position destination, List<Position> result) {
        Assertions.assertThat(source.getVerticalPathExcludeDestination(destination)).isEqualTo(result);
    }

    private static Stream<Arguments> getVerticalPathExcludeDestination() {
        return Stream.of(
                Arguments.of(Position.of(1, 2), Position.of(5, 2),
                        List.of(Position.of(2, 2), Position.of(3, 2), Position.of(4, 2))),
                Arguments.of(Position.of(9, 2), Position.of(6, 2),
                        List.of(Position.of(7, 2), Position.of(8, 2)))
        );
    }
}
