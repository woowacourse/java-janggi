package domain.position;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ColumnTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 10})
    void 범위를_벗어난_값으로_생성_시_예외가_발생해야_한다(int value) {
        Assertions.assertThatThrownBy(() -> new Column(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 9})
    void 범위_내부의_값으로_생성_시_예외가_발생하지_않는다(int value) {
        Assertions.assertThatNoException().isThrownBy(() -> new Column(value));
    }

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
                Arguments.of(3, 4, 3),
                Arguments.of(4, 7, 4)
        );
    }

}