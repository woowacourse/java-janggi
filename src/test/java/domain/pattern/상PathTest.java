package domain.pattern;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 상PathTest {

    @ParameterizedTest
    @MethodSource("provide상Path")
    void 상의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(Position afterPosition, List<Pattern> path) {
        // given
        int beforeRow = 6;
        int beforeColumn = 5;
        Position beforePosition = new Position(beforeRow, beforeColumn);

        // when
        List<Pattern> 상path = 상Path.getPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(상path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide상Path() {
        return Stream.of(
                Arguments.of(new Position(4, 8), 상Path.RIGHT_UP.getPatterns()),
                Arguments.of(new Position(8, 8), 상Path.RIGHT_DOWN.getPatterns()),
                Arguments.of(new Position(9, 7), 상Path.DOWN_RIGHT.getPatterns()),
                Arguments.of(new Position(9, 3), 상Path.DOWN_LEFT.getPatterns()),
                Arguments.of(new Position(8, 2), 상Path.LEFT_DOWN.getPatterns()),
                Arguments.of(new Position(4, 2), 상Path.LEFT_UP.getPatterns()),
                Arguments.of(new Position(3, 3), 상Path.UP_LEFT.getPatterns()),
                Arguments.of(new Position(3, 7), 상Path.UP_RIGHT.getPatterns())
        );
    }

    @Test
    void 상의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 6;
        int beforeColumn = 5;
        Position beforePosition = new Position(beforeRow, beforeColumn);

        int afterRow = 4;
        int afterColumn = 6;
        Position afterPosition = new Position(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> 상Path.getPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
