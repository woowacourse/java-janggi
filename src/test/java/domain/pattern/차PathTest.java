package domain.pattern;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 차PathTest {

    @ParameterizedTest
    @MethodSource("provide차Path")
    void 차의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(int afterRow, int afterColumn, List<Pattern> path) {
        // given
        int beforeRow = 10;
        int beforeColumn = 1;

        // when
        List<Pattern> 차path = 차Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn);

        // when & then
        Assertions.assertThat(차path).containsAll(path);
    }

    static Stream<Arguments> provide차Path() {
        return Stream.of(
                Arguments.of(5, 1, List.of(차Path.UP.getPattern(), 차Path.UP.getPattern(), 차Path.UP.getPattern(),
                        차Path.UP.getPattern(), 차Path.UP.getPattern())),
                Arguments.of(10, 9,
                        List.of(차Path.RIGHT.getPattern(), 차Path.RIGHT.getPattern(), 차Path.RIGHT.getPattern(),
                                차Path.RIGHT.getPattern(), 차Path.RIGHT.getPattern(), 차Path.RIGHT.getPattern(),
                                차Path.RIGHT.getPattern(), 차Path.RIGHT.getPattern()))
        );
    }

    @Test
    void 차의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 10;
        int beforeColumn = 1;
        int afterRow = 9;
        int afterColumn = 2;

        // when & then
        Assertions.assertThatThrownBy(() -> 차Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn))
                .isInstanceOf(IllegalStateException.class);
    }
}
