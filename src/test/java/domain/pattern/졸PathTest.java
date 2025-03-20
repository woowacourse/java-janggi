package domain.pattern;

import domain.Position;
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
    void 졸의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(Position afterPosition, List<Pattern> path) {
        // given
        int beforeRow = 7;
        int beforeColumn = 5;
        Position beforePosition = new Position(beforeRow, beforeColumn);
        Path pathOf졸 = new 졸Path();

        // when
        List<Pattern> 졸path = pathOf졸.getPath(beforePosition, afterPosition);

        // when & then
        Assertions.assertThat(졸path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide졸Path() {
        Path pathOf졸 = new 졸Path();
        return Stream.of(
                Arguments.of(new Position(6, 5), pathOf졸.getPatterns(Direction.UP)),
                Arguments.of(new Position(7, 4), pathOf졸.getPatterns(Direction.LEFT)),
                Arguments.of(new Position(7, 6), pathOf졸.getPatterns(Direction.RIGHT))
        );
    }

    @Test
    void 졸의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // given
        int beforeRow = 7;
        int beforeColumn = 5;
        Position beforePosition = new Position(beforeRow, beforeColumn);

        int afterRow = 8;
        int afterColumn = 5;
        Position afterPosition = new Position(afterRow, afterColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> new 졸Path().getPath(beforePosition, afterPosition))
                .isInstanceOf(IllegalStateException.class);
    }
}
