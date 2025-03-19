package domain.pattern;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 마PathTest {

    @ParameterizedTest
    @MethodSource("provide마Path")
    void 마의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(Position afterPosition, List<Pattern> path) {
        // given
        Position beforePosition = new Position(6, 4);

        // when
        List<Pattern> 마path = 마Path.getPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(마path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide마Path() {
        return Stream.of(
                Arguments.of(new Position(5, 2), 마Path.LEFT_UP.getPatterns()),
                Arguments.of(new Position(5, 6), 마Path.RIGHT_UP.getPatterns()),
                Arguments.of(new Position(4, 5), 마Path.UP_RIGHT.getPatterns()),
                Arguments.of(new Position(4, 3), 마Path.UP_LEFT.getPatterns()),
                Arguments.of(new Position(7, 2), 마Path.LEFT_DOWN.getPatterns()),
                Arguments.of(new Position(8, 3), 마Path.DOWN_LEFT.getPatterns()),
                Arguments.of(new Position(8, 5), 마Path.DOWN_RIGHT.getPatterns()),
                Arguments.of(new Position(7, 6), 마Path.RIGHT_DOWN.getPatterns())
        );
    }

    @Test
    void 마의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        Position beforePosition = new Position(6, 4);
        Position afterPosition = new Position(4, 4);

        // when & then
        Assertions.assertThatThrownBy(() -> 마Path.getPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
