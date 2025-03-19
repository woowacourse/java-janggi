package domain.pattern;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 포PathTest {

    @ParameterizedTest
    @MethodSource("provide포Path")
    void 포의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(Position afterPosition, List<Pattern> path) {
        // given
        int beforeRow = 10;
        int beforeColumn = 1;
        Position beforePosition = new Position(beforeRow, beforeColumn);

        // when
        List<Pattern> 포path = 포Path.getPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(포path).containsAll(path);
    }

    static Stream<Arguments> provide포Path() {
        return Stream.of(
                Arguments.of(new Position(5, 1),
                        List.of(포Path.UP.getPattern(), 포Path.UP.getPattern(), 포Path.UP.getPattern(),
                                포Path.UP.getPattern(), 포Path.UP.getPattern())),
                Arguments.of(new Position(10, 9),
                        List.of(포Path.RIGHT.getPattern(), 포Path.RIGHT.getPattern(), 포Path.RIGHT.getPattern(),
                                포Path.RIGHT.getPattern(), 포Path.RIGHT.getPattern(), 포Path.RIGHT.getPattern(),
                                포Path.RIGHT.getPattern(), 포Path.RIGHT.getPattern()))
        );
    }

    @Test
    void 포의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 10;
        int beforeColumn = 1;
        Position beforePosition = new Position(beforeRow, beforeColumn);

        int afterRow = 9;
        int afterColumn = 2;
        Position afterPosition = new Position(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> 포Path.getPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
