package domain.pattern;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 졸PathTest {

    @ParameterizedTest
    @MethodSource("provide졸Path")
    void 졸의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(int afterRow, int afterColumn, List<Pattern> path) {
        // given
        int beforeRow = 7;
        int beforeColumn = 5;

        // when
        List<Pattern> 졸path = 졸Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn);

        // when & then
        Assertions.assertThat(졸path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide졸Path() {
        return Stream.of(
                Arguments.of(6, 5, 졸Path.UP.getPatterns()),
                Arguments.of(7, 4, 졸Path.LEFT.getPatterns()),
                Arguments.of(7, 6, 졸Path.RIGHT.getPatterns())
        );
    }

    @Test
    void 졸의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 7;
        int beforeColumn = 5;
        int afterRow = 8;
        int afterColumn = 5;

        // when & then
        Assertions.assertThatThrownBy(() -> 졸Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn))
                .isInstanceOf(IllegalStateException.class);
    }
}
