package domain.pattern;

import domain.Position;
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
    void 차의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(Position afterPosition, List<Pattern> path) {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        Position beforePosition = new Position(beforeRow, beforeColumn);

        // when
        List<Pattern> 차path = 차Path.getPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(차path).containsAll(path);
    }

    static Stream<Arguments> provide차Path() {
        return Stream.of(
                Arguments.of(new Position(5, 1),
                        List.of(차Path.UP.getPattern(), 차Path.UP.getPattern(), 차Path.UP.getPattern(),
                                차Path.UP.getPattern(), 차Path.UP.getPattern())),
                Arguments.of(new Position(0, 9),
                        List.of(차Path.RIGHT.getPattern(), 차Path.RIGHT.getPattern(), 차Path.RIGHT.getPattern(),
                                차Path.RIGHT.getPattern(), 차Path.RIGHT.getPattern(), 차Path.RIGHT.getPattern(),
                                차Path.RIGHT.getPattern(), 차Path.RIGHT.getPattern()))
        );
    }

    @Test
    void 차의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 0;
        int beforeColumn = 1;
        Position beforePosition = new Position(beforeRow, beforeColumn);

        int afterRow = 9;
        int afterColumn = 2;
        Position afterPosition = new Position(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> 차Path.getPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
