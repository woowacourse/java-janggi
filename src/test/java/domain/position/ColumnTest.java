package domain.position;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ColumnTest {
    @ParameterizedTest
    @MethodSource("addValues")
    void 덧셈이_정상적으로_수행되어야_한다(int source, int target, int result) {
        Assertions.assertThat(new Column(source).add(target)).isEqualTo(new Column(result));
    }

    private static Stream<Arguments> addValues() {
        return Stream.of(
                Arguments.of(1, 1, 2),
                Arguments.of(7, -5, 2),
                Arguments.of(3, 4, 7),
                Arguments.of(4, 5, 9)
        );
    }

    @ParameterizedTest
    @MethodSource("getUpperValues")
    void 큰_값이_정상적으로_반환되어야_한다(int source, int target, int result) {
        Assertions.assertThat(new Column(source).getUpper(new Column(target))).isEqualTo(new Column(result));
    }

    private static Stream<Arguments> getUpperValues() {
        return Stream.of(
                Arguments.of(1, 2, 2),
                Arguments.of(7, -5, 7),
                Arguments.of(3, 4, 4),
                Arguments.of(4, 7, 7)
        );
    }

    @ParameterizedTest
    @MethodSource("getLowerValues")
    void 작은_값이_정상적으로_반환되어야_한다(int source, int target, int result) {
        Assertions.assertThat(new Column(source).getLower(new Column(target))).isEqualTo(new Column(result));
    }

    private static Stream<Arguments> getLowerValues() {
        return Stream.of(
                Arguments.of(1, 2, 1),
                Arguments.of(7, -5, -5),
                Arguments.of(3, 4, 3),
                Arguments.of(4, 7, 4)
        );
    }

}