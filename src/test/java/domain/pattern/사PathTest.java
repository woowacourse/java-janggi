package domain.pattern;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 사PathTest {

    @ParameterizedTest
    @MethodSource("provide사Path")
    void 사의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(int afterRow, int afterColumn, List<Pattern> path) {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;

        // when
        List<Pattern> 사path = 사Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn);

        // when & then
        Assertions.assertThat(사path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide사Path() {
        return Stream.of(
                Arguments.of(8, 5, 사Path.UP.getPatterns()),
                Arguments.of(9, 4, 사Path.LEFT.getPatterns()),
                Arguments.of(9, 6, 사Path.RIGHT.getPatterns()),
                Arguments.of(10, 5, 사Path.DOWN.getPatterns()));
    }

    @Test
    void 사의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 9;
        int beforeColumn = 5;
        int afterRow = 8;
        int afterColumn = 4;

        // when & then
        Assertions.assertThatThrownBy(() -> 사Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn))
                .isInstanceOf(IllegalStateException.class);
    }
}
